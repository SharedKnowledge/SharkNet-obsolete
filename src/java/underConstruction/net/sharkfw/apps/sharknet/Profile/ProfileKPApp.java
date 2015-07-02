package Profile;

import Profile.*;
import net.sharkfw.knowledgeBase.PeerSTSet;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SemanticTag;

import java.util.Iterator;

/**
 * Created by s0539720 on 29.04.2015.
 */
public interface ProfileKPApp {
    /**
     * Ask a peer or multiple peers to send their profiles
     * @param peers
     */
    public void ask4Profiles(Iterator<PeerSemanticTag> peers);

    /**
     * Send a list of profiles to one or multiple recipients
     * @param profiles
     * @param recipients
     */
    public void sendProfiles(Iterator<Profile> profiles, Iterator<PeerSemanticTag> recipients);

    /**
     * Send my own profile to one or multiple recipients
     * @param recipients
     */
    public void sendMyProfile(Iterator<PeerSemanticTag> recipients);

    /**
     * Send all profiles to one or multiple recipients
     * @param recipients
     */
    public void sendAllProfiles(Iterator<PeerSemanticTag> recipients);
}
