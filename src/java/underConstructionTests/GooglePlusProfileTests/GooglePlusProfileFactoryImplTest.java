package GooglePlusProfileTests;

import GooglePlusProfile.GooglePlusProfile;
import GooglePlusProfile.GooglePlusProfileFactory;
import GooglePlusProfile.GooglePlusProfileFactoryImpl;
import Profile.ProfileFactory;
import Profile.ProfileFactoryImpl;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Wayne on 05.08.2015.
 */
public class GooglePlusProfileFactoryImplTest {

    private SharkKB kb = new InMemoSharkKB();
    private ProfileFactory profileFactory;
    private GooglePlusProfileFactory googlePlusProfileFactory;

    public GooglePlusProfileFactoryImplTest() throws SharkKBException {
        profileFactory = new ProfileFactoryImpl(kb);
        googlePlusProfileFactory = new GooglePlusProfileFactoryImpl(profileFactory);
    }

    @Test
    public void testGooglePlusProfileFactory() throws Exception {
        GooglePlusProfile aliceProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        GooglePlusProfile bobProfile = googlePlusProfileFactory.createGooglePlusProfile("Bob", "http://www.sharksystem.net/bob.html");

        aliceProfile.setFirstName("Alice");
        bobProfile.setFirstName("Bob");

        GooglePlusProfile newAlice = googlePlusProfileFactory.getGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        assertEquals("Alice", newAlice.getFirstName());

        List<GooglePlusProfile> profileList = googlePlusProfileFactory.getAllGooglePlusProfiles();
        assertEquals("Alice", profileList.get(0).getFirstName());
        assertEquals("Bob", profileList.get(1).getFirstName());

        googlePlusProfileFactory.removeGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        profileList = googlePlusProfileFactory.getAllGooglePlusProfiles();
        assertEquals(1, profileList.size());

    }
}