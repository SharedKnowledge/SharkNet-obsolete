package sharknet;

import net.sharkfw.knowledgeBase.PeerSTSet;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.system.SharkSecurityException;

import java.io.IOException;

/**
 * Created by s0539720 on 29.04.2015.
 */
public interface ProfileKPApp {
    /**
     * Ask a peer or multiple peers to send their profiles
     * alle!!
     * @param peers
     */
    public void ask4Profiles(PeerSTSet peers) throws SharkKBException, SharkSecurityException, IOException;

    /**
     * Ask all peers in the surrounding area for their profiles
     */
    public void ask4Profiles();

    /**
     * Send your profile to everyone in the surrounding area
     */
    public void sendMyProfile();

    /**
     * Send one (e.g. your own) or multiple profiles to one or multiple recipients
     * @param profiles
     * @param recipients
     */
    public void sendProfiles(PeerSTSet profiles, PeerSTSet recipients) throws SharkSecurityException, IOException, SharkKBException;
}
