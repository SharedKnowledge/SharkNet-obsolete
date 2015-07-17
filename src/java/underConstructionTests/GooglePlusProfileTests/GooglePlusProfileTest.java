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

/**
 * Created by Mr.T on 16.07.2015.
 */
public class GooglePlusProfileTest {

    private SharkKB kb = new InMemoSharkKB();
    private ProfileFactory profileFactory;
    private GooglePlusProfileFactory googlePlusProfileFactory;

    public GooglePlusProfileTest() throws SharkKBException {
       profileFactory = new ProfileFactoryImpl(kb);
       googlePlusProfileFactory = new GooglePlusProfileFactoryImpl(profileFactory);
    }

    @Test
    public void testCreateGooglePlusProfile() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("MyProfile", "https://MyProfile");
        myGooglePlusProfile.addEmployment("Simens", "MaschinenBauer", 1993, 2000, false, "It makes fun");
        System.out.println(myGooglePlusProfile.getJobTitle("0"));
    }

}