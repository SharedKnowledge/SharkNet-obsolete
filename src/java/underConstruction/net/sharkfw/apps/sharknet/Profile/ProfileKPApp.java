package Profile;

import net.sharkfw.knowledgeBase.PeerSemanticTag;

import java.util.Iterator;

/**
 * Created by s0539720 on 29.04.2015.
 */
public interface ProfileKPApp {
    /**
     * With this function a peer or multiple peers can be asked to send their profiles to the user.
     *
     * @param peers
     */
    public void ask4Profiles(Iterator<PeerSemanticTag> peers);

    /**
     * This function is used to send the user's profiles to other peers.
     *
     * @param profiles
     * @param recipients
     */
    public void sendProfiles(Iterator<Profile> profiles, Iterator<PeerSemanticTag> recipients);

    /**
     * With this function only the user's own profile is sent to the peers stated as recipients.
     *
     * @param recipients
     */
    public void sendMyProfile(Iterator<PeerSemanticTag> recipients);

    /**
     * This function is used to send all profiles of the user to the peers stated as recipients.
     *
     * @param recipients
     */
    public void sendAllProfiles(Iterator<PeerSemanticTag> recipients);
}
