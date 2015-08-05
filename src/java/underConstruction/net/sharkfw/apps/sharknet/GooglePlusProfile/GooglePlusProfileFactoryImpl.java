package GooglePlusProfile;

import Profile.Profile;
import Profile.ProfileFactory;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

import java.util.ArrayList;
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
        return new GooglePlusProfileImpl(p, true);
    }

    public List<GooglePlusProfile> getAllGooglePlusProfiles() throws SharkKBException {
        List<GooglePlusProfile> googlePlusProfilesList = new ArrayList<GooglePlusProfile>();
        for (Profile profile : pf.getAllProfiles()){
            googlePlusProfilesList.add(new GooglePlusProfileImpl(profile, false));
        }
        return googlePlusProfilesList;
    }

    @Override
    public GooglePlusProfile getGooglePlusProfile(String name, String profileURL) throws SharkKBException {
        PeerSemanticTag peer = InMemoSharkKB.createInMemoPeerSemanticTag(name, profileURL, null);
        return new GooglePlusProfileImpl(pf.getProfile(peer), false);
    }

    @Override
    public void removeGooglePlusProfile(String name, String profileURL) throws SharkKBException {
        PeerSemanticTag peer = InMemoSharkKB.createInMemoPeerSemanticTag(name, profileURL, null);
        pf.removeProfile(peer, peer);
    }


}
