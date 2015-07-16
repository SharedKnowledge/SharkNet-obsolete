package GooglePlusProfile;

import Profile.Profile;
import Profile.ProfileFactory;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

import java.util.List;

/**
 * Created by Mr.T on 15.07.2015.
 */
public class GooglePlusProfileFactoryImpl implements GooglePlusProfileFactory{
    private ProfileFactory pf;

    public GooglePlusProfileFactoryImpl(ProfileFactory pf) {
        this.pf = pf;
    }

    public GooglePlusProfile createGooglePlusProfile(String name, String profileURL) throws SharkKBException {
        PeerSemanticTag peer = InMemoSharkKB.createInMemoPeerSemanticTag(name, profileURL, null);
        Profile p = pf.createProfile(peer, peer);
        return new GooglePlusProfileImpl(p);
    }

    public List<Profile> getAllGooglePlusProfiles() throws SharkKBException {
        return pf.getAllProfiles();
    }

    @Override
    public Profile getGooglePlusProfile(String name, String profileURL) throws SharkKBException {
        PeerSemanticTag peer = InMemoSharkKB.createInMemoPeerSemanticTag(name, profileURL, null);
        return pf.getProfile(peer);
    }

    @Override
    public void removeGooglePlusProfile(String name, String profileURL) throws SharkKBException {
        PeerSemanticTag peer = InMemoSharkKB.createInMemoPeerSemanticTag(name, profileURL, null);
        pf.removeProfile(peer, peer);
    }


}
