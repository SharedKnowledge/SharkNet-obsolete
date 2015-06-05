package Profile.ProfileTests;

import Profile.*;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mr.T on 29.05.2015.
 */
public class ProfileFactoryImplTest {
    private SharkKB kb = new InMemoSharkKB();
    private ProfileFactory profileFactory;
    private String[] aliceAddresses = {"mail://alice@wonderland.net", "mail://alice@wizards.net", "http://www.sharksystem.net/alice.html"};
    private String[] aliceSis = {"http://www.sharksystem.net/alice.html"};
    private PeerSemanticTag alice = InMemoSharkKB.createInMemoPeerSemanticTag("Alice",
            aliceSis,
            aliceAddresses);
    private String[] bobAddresses = {"mail://bob@sharknet.net", "mail://bob@wizards.net", "http://www.sharksystem.net/bob.html"};
    private String[] bobSis = {"http://www.sharksystem.net/bob.html"};
    private PeerSemanticTag bob = InMemoSharkKB.createInMemoPeerSemanticTag("Bob", bobSis, bobAddresses);
    private String[] claraAddresses = {"mail://clara@sharknet.net", "mail://clara@wizards.net", "http://www.sharksystem.net/clara.html"};
    private String[] claraSis = {"http://www.sharksystem.net/clara.html"};
    private PeerSemanticTag clara = InMemoSharkKB.createInMemoPeerSemanticTag("Clara", claraSis, claraAddresses);

    public ProfileFactoryImplTest() throws SharkKBException {
        profileFactory = new ProfileFactoryImpl(kb);
    }

    private Profile createProfileAlice() throws SharkKBException {
        Profile aliceProfile = profileFactory.createProfile(alice, alice);
        ProfileName profileName = new ProfileNameImpl("Alice");
        profileName.setLastName("Alpha");
        profileName.setTitle("Prof.");
        aliceProfile.setName(profileName);
        return aliceProfile;
    }

    private Profile createProfileFromAliceOfBob() throws SharkKBException {
        Profile aliceProfileOfBob = profileFactory.createProfile(alice, bob);
        ProfileName profileName = new ProfileNameImpl("Bob");
        profileName.setLastName("Beta");
        profileName.setTitle("Dr.Dr.");
        aliceProfileOfBob.setName(profileName);
        return aliceProfileOfBob;
    }

    private Profile createProfileBob() throws SharkKBException {
        Profile bobProfile = profileFactory.createProfile(bob, bob);
        ProfileName profileName = new ProfileNameImpl("Bob");
        profileName.setLastName("Beta");
        profileName.setTitle("Dr.Dr.");
        bobProfile.setName(profileName);
        return bobProfile;
    }

    @Test
    public void testGetAllProfiles() throws SharkKBException {
        Profile aliceProfile = createProfileAlice();
        Profile bobProfile = createProfileBob();
        List<Profile> profileList = new ArrayList<Profile>();
        profileList.addAll(profileFactory.getAllProfiles());
        Profile newAlice = null;
        Profile newBob = null;
        for (int i = 0; i < profileList.size(); i++) {
            if (profileList.get(i).getProfileCreator() == alice) {
                newAlice = profileList.get(i);
            }
            else if (profileList.get(i).getProfileCreator() == bob) {
                newBob = profileList.get(i);
            }
        }
        assertTrue(newAlice.getName().getLastName().equals("Alpha") && newBob.getName().getLastName().equals("Beta"));
    }

    @Test
    public void testGetAllProfilesFromTheSameCreator() throws Exception {
        createProfileAlice();
        createProfileFromAliceOfBob();
        List<Profile> allProfilesThatAliceCreated = profileFactory.getProfile(alice, null);
        System.out.println(allProfilesThatAliceCreated.get(0).getName().getSurname());
        System.out.println(allProfilesThatAliceCreated.get(1).getName().getSurname());
    }
    @Test
    public void testGetProfile() throws Exception {
        createProfileAlice();
        List<Profile> aliceProfile = profileFactory.getProfile(alice, alice);
        assertEquals("Alice", aliceProfile.get(0).getName().getSurname());
        //System.out.println(aliceProfile.get(0).getName().getSurname());
    }

    @Test
    public void testGetProfileThatNotExists() throws SharkKBException {
        List<Profile> aliceProfile = profileFactory.getProfile(clara, clara);
       assertTrue(aliceProfile.isEmpty());
    }

    @Test
    public void testCreateProfile() throws Exception {

    }
}