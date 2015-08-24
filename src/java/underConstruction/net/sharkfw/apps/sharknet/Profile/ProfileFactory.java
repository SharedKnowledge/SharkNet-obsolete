package Profile;

import net.sharkfw.knowledgeBase.Knowledge;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.util.Iterator;
import java.util.List;

/**This factory should handle different tasks all about profiles.
 * Its like a profile manager.
 * It can create profiles.
 * It stores created profiles.
 * It can get and return profiles.
 * It can remove stored profiles.
 * Created by Thilo Stegemann on 06.05.2015.
 */
public interface ProfileFactory {
    /**Returns a list of all stored profiles.
     *
     * @return List of all stored profiles.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    List<Profile> getAllProfiles() throws SharkKBException;

    /**Returns a profile from a peer.
     *
     * @param creatorAndTarget A peer who is creator and owner of the profile.
     * @return A profile.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    Profile getProfile(PeerSemanticTag creatorAndTarget) throws SharkKBException;

    /**Returns a profile from a peer where creator and target peers are different.
     *
     * @param creator The creator of the profile.
     * @param target The target of the profile, person or thing where the profile information belongs to.
     * @return A profile.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    Profile getProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;

    /**Returns a list of profiles.
     * If only a creator peer is given,
     * than a list of all profiles which are created from this peer is returned.
     * If there are no peers given then a list of all profiles is returned.
     * If there is a creator and a target peer given then a spacial profile is returned which has exactly this creator and this target peer.
     * @param creator The creator of the profile.
     * @param target The target of the profile, person or thing where the profile information belongs to.
     * @return A list of profiles.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    List<Profile> getProfiles(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;

    /**Returns the knowledge of an iterator of profiles.
     *
     * @param profiles Profiles where the knowledge should be extracted.
     * @return Knowledge of the profiles.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    Knowledge getKnowledge4Profiles(Iterator<Profile> profiles) throws SharkKBException;

    /**Creates a profile.
     *
     * @param creator The creator of the profile.
     * @param target The target of the profile, person or thing where the profile information belongs to.
     * @return The profile.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    Profile createProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;

    /**Removes a specialized profile.
     *
     * @param creator The creator of the profile.
     * @param target The target of the profile, person or thing where the profile information belongs to.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void removeProfile(PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException;

}
