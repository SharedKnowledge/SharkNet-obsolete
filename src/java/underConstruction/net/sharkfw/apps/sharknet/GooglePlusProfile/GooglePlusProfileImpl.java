package GooglePlusProfile;

import Profile.ProfileFactoryImpl;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

/**
 * Created by Mr.T on 03.07.2015.
 */
public class GooglePlusProfileImpl {
    private ProfileFactoryImpl myProfileFactory;
    private SharkKB myKb = new InMemoSharkKB();
    private String[] myAdresses;
    private String[] mySemanticIdentifiers;
    private PeerSemanticTag myPeer;

    private static void initializeProfileFactory() throws SharkKBException {
    }
}
