package sharknet;

import Profile.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.peer.KEPConnection;
import net.sharkfw.peer.KnowledgePort;
import net.sharkfw.peer.SharkEngine;
import net.sharkfw.system.SharkSecurityException;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by _Wayne- on 05.05.2015.
 */
public class ProfileKP extends KnowledgePort implements ProfileKPApp, ProfileKPConfig {
    public static final String PROFILE_TOPIC_SI = "http://www.sharksystem.net/apps/sharknet/vocabulary/peerProfile.html";
    private final PeerSemanticTag owner;
    private ProfileFactory pf;
    private boolean acceptWithoutVerification = false;
    private boolean sendProfiles2UnknownPeer = false;
    private boolean sendProfileAutomatically = false;
    private boolean ask4ProfilesAutomatically = false;
    private boolean acceptProfileWithDifferentOwner = false;
    private boolean ask4ProfilesAutomaticallyOnWiFiDirectConnection = false;
    private boolean sendProfileAutomaticallyOnWiFiDirectConnection = false;

    public ProfileKP(SharkEngine se, SharkKB kb, ProfileFactory pf, PeerSemanticTag owner){
        super(se);
        this.pf = new ProfileFactoryImpl(kb);
        this.pf = pf;
        this.owner = owner;
    }

    @Override
    protected void doExpose(SharkCS interest, KEPConnection kepConnection) {
        //Woher weiss ich, dass eine WiFi-Direct Verbindung besteht?
        //Wie prüfe ich, ob verifiziert?
        int i = 42;
    }

    @Override
    protected void doInsert(Knowledge knowledge, KEPConnection kepConnection) {
        int i = 42;
    }

    private SemanticTag getProfileTopic(){
        SemanticTag profileTopic = InMemoSharkKB.createInMemoSemanticTag("SharkNet Peer Profile", PROFILE_TOPIC_SI);

        return profileTopic;
    }

    @Override
    public void ask4Profiles(PeerSTSet peers) throws SharkKBException, SharkSecurityException, IOException {
        STSet profileTopics = InMemoSharkKB.createInMemoSTSet();
        profileTopics.merge(this.getProfileTopic());
        //remote peer dimension null oder peer?
        Interest profileInterest = InMemoSharkKB.createInMemoInterest(profileTopics, null, null, null, null, null, SharkCS.DIRECTION_IN);

        for (Enumeration<PeerSemanticTag> e = peers.peerTags(); e.hasMoreElements();){
            PeerSemanticTag peer = e.nextElement();
            //Wie wird die Verbindung aufgebaut? this.se.startTCP(???)
            this.sendInterest(profileInterest, peer);
            //this.se.publishKP(this, peer);
        }

    }

    @Override
    public void ask4Profiles() {
        //woher weiss ich, welche peers in der umgebung sind?
    }

    @Override
    public void sendMyProfile() {

    }

    @Override
    public void sendProfiles(PeerSTSet profiles, PeerSTSet recipients) throws SharkSecurityException, IOException, SharkKBException {
        SemanticTag profileTopic = this.getProfileTopic();

        for (Enumeration<PeerSemanticTag> e = recipients.peerTags(); e.hasMoreElements();){
            PeerSemanticTag recipient = e.nextElement();

            ContextCoordinates cc = InMemoSharkKB.createInMemoContextCoordinates(
                    profileTopic,
                    this.owner,
                    this.owner,
                    recipient,
                    null,
                    null,
                    SharkCS.DIRECTION_OUT
            );
            ContextPoint cp = InMemoSharkKB.createInMemoContextPoint(cc);

            for (Enumeration<PeerSemanticTag> profilesE = profiles.peerTags(); profilesE.hasMoreElements();) {
                PeerSemanticTag peer = profilesE.nextElement();
                Profile profile = pf.getProfile(peer, peer);
                cp.addInformation(Serializer.serialize(profile));
            }

            Knowledge k = InMemoSharkKB.createInMemoKnowledge();
            k.addContextPoint(cp);

            this.sendKnowledge(k,recipient);
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
    public void setSendProfileAutomatically(boolean sendProfileAutomatically) {
        this.sendProfileAutomatically = sendProfileAutomatically;
    }

    @Override
    public boolean getSendProfileAutomatically() {
        return this.sendProfileAutomatically;
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
