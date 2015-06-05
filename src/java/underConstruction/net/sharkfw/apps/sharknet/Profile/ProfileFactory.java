package Profile;

import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.util.List;

/**
 * Created by Mr.T on 06.05.2015.
 */
public interface ProfileFactory {
    List<Profile> getAllProfiles() throws SharkKBException;
    List<Profile> getProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;
    Profile createProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;

}
