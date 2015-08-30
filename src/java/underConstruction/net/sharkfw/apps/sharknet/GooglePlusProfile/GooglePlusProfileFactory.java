package GooglePlusProfile;

import Profile.Profile;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.util.List;

/**
 * Created by Thilo Stegemann and Maximilian Herzog on 15.07.2015.
 */
public interface GooglePlusProfileFactory {
    /**
     * Creates an empty google plus profile object and returns it.
     *
     * @param name Name of the person the profile refers to
     * @param profileURL URL of the profile
     * @return Google plus profile object
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB
     */
    GooglePlusProfile createGooglePlusProfile(String name, String profileURL) throws SharkKBException;

    /**
     * @return All google plus profiles that are stored in the SharkKB
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB
     */
    List<GooglePlusProfile> getAllGooglePlusProfiles() throws SharkKBException;

    /**
     * Returns a specified google plus profile.
     * It is specified by its parameters.
     *
     * @param name Name of the profile owner
     * @param profileURL URL of the profile
     * @return The specified google plus profile
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB
     */
    GooglePlusProfile getGooglePlusProfile(String name, String profileURL) throws SharkKBException;

    /**
     * Removes a specified google plus profile.
     * It is specified by its parameters.
     *
     * @param name Name of the profile owner
     * @param profileURL URL of the profile
     * @throws SharkKBException
     */
    void removeGooglePlusProfile(String name, String profileURL) throws SharkKBException;
}
