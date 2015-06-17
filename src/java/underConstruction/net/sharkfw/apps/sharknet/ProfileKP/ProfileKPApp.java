package ProfileKP;

import Profile.*;
import net.sharkfw.knowledgeBase.PeerSTSet;

import java.util.Iterator;

/**
 * Created by s0539720 on 29.04.2015.
 */
public interface ProfileKPApp {
    /**
     * Ask a peer or multiple peers to send their profiles
     * @param peers
     */
    public void ask4Profiles(PeerSTSet peers);

    /**
     * Send a list of profiles to one or multiple recipients
     * @param profiles
     * @param recipients
     */
    public void sendProfiles(Iterator<Profile> profiles, PeerSTSet recipients);

    /**
     * Send my own profile to one or multiple recipients
     * @param recipients
     */
    public void sendMyProfile(PeerSTSet recipients);

    /**
     * Send all profiles to one or multiple recipients
     * @param recipients
     */
    public void sendAllProfiles(PeerSTSet recipients);
}
