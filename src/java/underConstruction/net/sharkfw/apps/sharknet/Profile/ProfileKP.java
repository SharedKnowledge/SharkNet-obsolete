package Profile;

import Profile.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.peer.KEPConnection;
import net.sharkfw.peer.KnowledgePort;
import net.sharkfw.peer.SharkEngine;
import net.sharkfw.system.L;
import net.sharkfw.system.SharkException;
import net.sharkfw.system.SharkSecurityException;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by _Wayne- on 05.05.2015.
 */
public class ProfileKP extends KnowledgePort implements ProfileKPApp, ProfileKPConfig {
    private final PeerSemanticTag owner;
    private ProfileFactory pf;
    private boolean acceptWithoutVerification = false;
    private boolean sendProfiles2UnknownPeer = false;
    private boolean sendMyProfileAutomatically = false;
    private boolean ask4ProfilesAutomatically = false;
    private boolean acceptProfileWithDifferentOwner = false;
    private boolean ask4ProfilesAutomaticallyOnWiFiDirectConnection = false;
    private boolean sendProfileAutomaticallyOnWiFiDirectConnection = false;

    public ProfileKP(SharkEngine se, SharkKB kb, ProfileFactory pf, PeerSemanticTag owner) throws SharkKBException {
        super(se);
        this.kb = kb;
        this.pf = new ProfileFactoryImpl(kb);
        this.pf = pf;
        this.owner = owner;
    }

    /**
     * Checks whether a context space contains a context coordinate with a profile topic
     * @param interest
     * @return true if there is a profile topic, false otherwise
     * @throws SharkKBException
     */
    private boolean check4ProfileTopic(SharkCS interest) throws SharkKBException {
        STSet topics = interest.getTopics();
        for (Enumeration<SemanticTag> e = topics.tags(); e.hasMoreElements();){
            if ((e.nextElement()).identical(ProfileFactoryImpl.getProfileSemanticTag())){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfSenderIsKnown(PeerSemanticTag sender) throws SharkKBException {
        PeerSTSet peers = this.kb.getPeerSTSet();
        for (Enumeration<PeerSemanticTag> e = peers.peerTags(); e.hasMoreElements();){
            PeerSemanticTag peer = e.nextElement();
            if (!SharkCSAlgebra.identical(peer, sender)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doExpose(SharkCS interest, KEPConnection kepConnection) {
        if (!acceptWithoutVerification && !kepConnection.receivedMessageSigned()) { return; }

        try {
            if (!sendProfiles2UnknownPeer && !(checkIfSenderIsKnown(kepConnection.getSender()))) { return;}
        } catch (SharkKBException e) {
            L.e(e.getMessage(), this);
        }
        //WiFi Direct Connection (bei Allinteresse)
        if (SharkCSAlgebra.isAny(interest)){
            if (ask4ProfilesAutomaticallyOnWiFiDirectConnection){
                ContextCoordinates profileCC = getProfileCC(SharkCS.DIRECTION_IN);
                try {
                    kepConnection.expose(profileCC);
                } catch (SharkException e) {
                    L.w(e.getMessage(), this);
                }
            }
            if (sendProfileAutomaticallyOnWiFiDirectConnection){
                PeerSTSet recipients = InMemoSharkKB.createInMemoPeerSTSet();
                try {
                    recipients.merge(kepConnection.getSender());
                    sendMyProfile(recipients);
                } catch (SharkKBException e) {
                    L.e(e.getMessage(), this);
                }
            }
        //Interest in profile exchange
        } else try {
            if (check4ProfileTopic(interest)){
                PeerSTSet sender = InMemoSharkKB.createInMemoPeerSTSet();
                sender.merge(kepConnection.getSender());
                sendAllProfiles(sender);

                if (ask4ProfilesAutomatically){
                    ask4Profiles(sender);
                }
            }
        } catch (SharkKBException e) {
            L.e(e.getMessage(), this);
        }
    }

    @Override
    protected void doInsert(Knowledge knowledge, KEPConnection kepConnection) {
        if (!acceptWithoutVerification && !kepConnection.receivedMessageSigned()) { return; }

        for (Enumeration<ContextPoint> cpE = knowledge.contextPoints(); cpE.hasMoreElements();) {
            ContextPoint cp = cpE.nextElement();
            ContextCoordinates cc = cp.getContextCoordinates();
            //select contextpoints with profiles
            if (cc.getTopic().identical(ProfileFactoryImpl.getProfileSemanticTag())
                    && (cc.getOriginator().identical(cc.getPeer())) || acceptProfileWithDifferentOwner){
                try {
                    ContextPoint profileCP = this.kb.getContextPoint(cc);

                    if (profileCP == null){ //new profile
                        SharkCSAlgebra.merge(this.kb, cc, cp, true);
                    } else { //profile already exists
                        int existingProfileVersion = Integer.parseInt(profileCP.getContextCoordinates().getTopic().getProperty(Profile.SHARK_PROFILE_VERSION_PROPERTY));
                        int newProfileVersion = Integer.parseInt(cc.getTopic().getProperty(Profile.SHARK_PROFILE_VERSION_PROPERTY));

                        if (newProfileVersion > existingProfileVersion){
                            pf.removeProfile(cc.getOriginator(), cc.getPeer());
                            SharkCSAlgebra.merge(this.kb, cc, cp, true);
                        }
                    }
                } catch (SharkKBException e) {
                    L.e(e.getMessage(), this);
                }

                if (sendMyProfileAutomatically){
                    try {
                        PeerSTSet recipients = InMemoSharkKB.createInMemoPeerSTSet();
                        recipients.merge(kepConnection.getSender());
                        sendMyProfile(recipients);
                    } catch (SharkKBException e) {
                        L.e(e.getMessage(), this);
                    }
                }
            }
        }
    }

    private ContextCoordinates getProfileCC(int direction){
        SemanticTag profileTopic = ProfileFactoryImpl.getProfileSemanticTag();
        return InMemoSharkKB.createInMemoContextCoordinates(profileTopic, null, this.owner, null, null, null, direction);
    }

    @Override
    public void ask4Profiles(PeerSTSet peers) {
        try {
            ContextCoordinates profileCC = getProfileCC(SharkCS.DIRECTION_IN);

            for (Enumeration<PeerSemanticTag> e = peers.peerTags(); e.hasMoreElements();){
                PeerSemanticTag peer = e.nextElement();
                if (!SharkCSAlgebra.identical(peer, this.owner)){
                    this.sendInterest(profileCC, peer);
                }
            }
        }
        catch (SharkKBException e) {
            L.e(e.getMessage(), this);
        }
        catch (SharkSecurityException e) {
            L.e(e.getMessage(), this);
        }
        catch (IOException e) {
            L.e(e.getMessage(), this);
        }
    }

    @Override
    public void sendMyProfile(PeerSTSet recipients) {
        Iterator<Profile> profiles = null;
        try {
            profiles = pf.getProfiles(this.owner, this.owner).iterator();
        } catch (SharkKBException e) {
            L.e(e.getMessage(), this);
        }
        sendProfiles(profiles, recipients);
    }

    @Override
    public void sendAllProfiles(PeerSTSet recipients) {
        Iterator<Profile> profiles = null;
        try {
            profiles = pf.getAllProfiles().iterator();
        } catch (SharkKBException e) {
            L.e(e.getMessage(), this);
        }
        sendProfiles(profiles, recipients);
    }

    @Override
    //recipients als iterator
    public void sendProfiles(Iterator<Profile> profiles, PeerSTSet recipients) {
        Iterator<Profile> profilliste = profiles;
        for (Enumeration<PeerSemanticTag> e = recipients.peerTags(); e.hasMoreElements(); ) {
            PeerSemanticTag recipient = e.nextElement();
            try {
                Knowledge k = pf.getKnowledge4Profiles(profilliste);
                this.sendKnowledge(k, recipient);
            }
            catch (SharkKBException e1) {
                L.e(e1.getMessage(), this);
            }
            catch (SharkSecurityException e1) {
                L.e(e1.getMessage(), this);
            }
            catch (IOException e1) {
                L.e(e1.getMessage(), this);
            }
        }
    }

    @Override
    public void setAcceptWithoutVerification(boolean acceptWithoutVerification) {
        this.acceptWithoutVerification = acceptWithoutVerification;
    }

    @Override
    public boolean getAcceptWithoutVerification() {
        return this.acceptWithoutVerification;
    }

    @Override
    public void setSendProfiles2UnknownPeer(boolean sendProfiles2UnknownPeer) {
        this.sendProfiles2UnknownPeer = sendProfiles2UnknownPeer;
    }

    @Override
    public boolean getSendProfiles2UnknownPeer() {
        return this.sendProfiles2UnknownPeer;
    }

    @Override
    public void setSendMyProfileAutomatically(boolean sendProfileAutomatically) {
        this.sendMyProfileAutomatically = sendProfileAutomatically;
    }

    @Override
    public boolean getSendMyProfileAutomatically() {
        return this.sendMyProfileAutomatically;
    }

    @Override
    public void setAsk4ProfilesAutomatically(boolean ask4ProfilesAutomatically) {
        this.ask4ProfilesAutomatically = ask4ProfilesAutomatically;
    }

    @Override
    public boolean getAsk4ProfilesAutomatically() {
        return this.ask4ProfilesAutomatically;
    }

    @Override
    public void setAcceptProfileWithDifferentOwner(boolean acceptProfileWithDifferentOwner) {
        this.acceptProfileWithDifferentOwner = acceptProfileWithDifferentOwner;
    }

    @Override
    public boolean getAcceptProfileWithDifferentOwner() {
        return this.acceptProfileWithDifferentOwner;
    }

    @Override
    public void setAsk4ProfilesAutomaticallyOnWiFiDirectConnection(boolean ask4ProfilesAutomaticallyOnWiFiDirectConnection) {
        this.ask4ProfilesAutomaticallyOnWiFiDirectConnection = ask4ProfilesAutomaticallyOnWiFiDirectConnection;
    }

    @Override
    public boolean getAsk4ProfilesAutomaticallyOnWiFiDirectConnection() {
        return this.ask4ProfilesAutomaticallyOnWiFiDirectConnection;
    }

    @Override
    public void setSendProfileAutomaticallyOnWiFiDirectConnection(boolean sendProfileAutomaticallyOnWiFiDirectConnection) {
        this.sendProfileAutomaticallyOnWiFiDirectConnection = sendProfileAutomaticallyOnWiFiDirectConnection;
    }

    @Override
    public boolean getSendProfileAutomaticallyOnWiFiDirectConnection() {
        return this.sendProfileAutomaticallyOnWiFiDirectConnection;
    }
}
