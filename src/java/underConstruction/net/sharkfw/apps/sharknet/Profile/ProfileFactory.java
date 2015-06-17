package Profile;

import net.sharkfw.knowledgeBase.Knowledge;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Mr.T on 06.05.2015.
 */
public interface ProfileFactory {
    List<Profile> getAllProfiles() throws SharkKBException;
    Profile getProfile(PeerSemanticTag creatorAndTarget) throws SharkKBException;
    Profile getProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;
    List<Profile> getProfiles(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;
    Knowledge getKnowledge4Profiles(Iterator<Profile> profiles) throws SharkKBException;
    Profile createProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;
    void removeProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;

}