package Profile;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mr.T on 06.05.2015.
 */
public class ProfileFactoryImpl implements ProfileFactory {
    private SharkKB kb = null;
    private static SemanticTag profileSemanticTag = null;

    public ProfileFactoryImpl(SharkKB kb) throws SharkKBException {
        this.kb = kb;
    }
    public static SemanticTag getProfileSemanticTag() {
        if (ProfileFactoryImpl.profileSemanticTag == null) {
            ProfileFactoryImpl.profileSemanticTag = InMemoSharkKB.createInMemoSemanticTag("Profile", "http://www.sharksystem.net/Profile.html");
        }
        return ProfileFactoryImpl.profileSemanticTag;
    }

    @Override
    public List<Profile> getAllProfiles() throws SharkKBException {
        Enumeration<ContextPoint> contextPointEnumerations = kb.getAllContextPoints();
        //extract contextPoints mit specified topic von CSAlgebra
        List<Profile> profileList = new ArrayList<Profile>();
        while (contextPointEnumerations.hasMoreElements() == true) {
            profileList.add(new ProfileImpl(kb, contextPointEnumerations.nextElement()));
            //System.out.println(L.cp2String(contextPointEnumerations.nextElement()));
        }
        return profileList;
    }
    //To Do
    //To dO getAllProfilesWhereOwnerAndCreatorAreEqual
    @Override
    public Profile getProfile(PeerSemanticTag creatorAndTarget) throws SharkKBException {
        List<Profile> profiles = new ArrayList<Profile>();
        profiles.addAll(this.getProfiles(creatorAndTarget, creatorAndTarget));
        if (!profiles.isEmpty()) {
            return profiles.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public Profile getProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException {
        List<Profile> profiles = new ArrayList<Profile>();
        profiles.addAll(this.getProfiles(creator, target));
        if (!profiles.isEmpty()) {
            return profiles.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public List<Profile> getProfiles(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException {
        List<Profile> profileList = new ArrayList<Profile>();
        List<Profile> tempProfileList = new ArrayList<Profile>();
        if (creator != null && target != null) {
            ContextCoordinates cc = InMemoSharkKB.createInMemoContextCoordinates(ProfileFactoryImpl.getProfileSemanticTag(), creator, target, null, null, null, SharkCS.DIRECTION_INOUT);
            if (kb.getContextPoint(cc) != null) {
                profileList.add(new ProfileImpl(kb, kb.getContextPoint(cc)));
            }
        }
        else if (creator == null && target != null) {
            tempProfileList.addAll(getAllProfiles());
            for (int i = 0; i < tempProfileList.size(); i++) {
                if (tempProfileList.get(i).getProfileTarget().equals(target)) {
                    profileList.add(tempProfileList.get(i));
                }
            }
        }
        else if (creator != null && target == null) {
            tempProfileList.addAll(getAllProfiles());
            for (int i = 0; i < tempProfileList.size(); i++) {
                if (tempProfileList.get(i).getProfileCreator().equals(creator)) {
                    profileList.add(tempProfileList.get(i));
                }
            }
        }
        else if (creator == null && target == null) {
            profileList.addAll(getAllProfiles());
        }
        return profileList;
    }

    @Override
    public Knowledge getKnowledge4Profiles(Iterator<Profile> profiles) throws SharkKBException {
        Knowledge k = InMemoSharkKB.createInMemoKnowledge();
        while (profiles.hasNext()) {
            Profile p = profiles.next();
            if (p instanceof ProfileImpl) {
                ProfileImpl pim = (ProfileImpl) p;
                k.addContextPoint(pim.getContextPoint());
            }
        }
        return k;
    }

    @Override
    public Profile createProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException {
        ProfileImpl profile = new ProfileImpl(kb, creator, target);
        return profile;
    }

    @Override
    public void removeProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException {
        SemanticTag pr = ProfileFactoryImpl.getProfileSemanticTag();
        if (creator != null && target != null) {
            ContextCoordinates cc = InMemoSharkKB.createInMemoContextCoordinates(pr, creator, target, null, null, null, SharkCS.DIRECTION_INOUT);
            kb.removeContextPoint(cc);
        }
    }
}
