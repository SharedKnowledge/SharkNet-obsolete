package GooglePlusProfile;

import Profile.Profile;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.util.List;

/**
 * Created by Mr.T on 15.07.2015.
 */
public interface GooglePlusProfileFactory {
    GooglePlusProfile createGooglePlusProfile(String profileURL) throws SharkKBException;
    List<Profile> getAllGooglePlusProfiles() throws SharkKBException;
    Profile getGooglePlusProfile(String profileURL) throws SharkKBException;
    void removeGooglePlusProfile(String profileURL) throws SharkKBException;
}
