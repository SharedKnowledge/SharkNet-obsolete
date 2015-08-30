package ProfileTests;

import Profile.*;
import net.sharkfw.knowledgeBase.Knowledge;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.system.L;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Thilo Stegemann on 29.05.2015.
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

    private Profile createProfileFromBobOfAlice() throws SharkKBException {
        Profile bobProfileOfAlice = profileFactory.createProfile(bob, alice);
        ProfileName profileName = new ProfileNameImpl("Alice");
        profileName.setLastName("Alpha");
        profileName.setTitle("This is Bobs Alice");
        bobProfileOfAlice.setName(profileName);
        return bobProfileOfAlice;
    }

    @Test
    public void testGetAllProfiles() throws SharkKBException {
        createProfileAlice();
        createProfileBob();
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
        List<Profile> allProfilesThatAliceCreated = profileFactory.getProfiles(alice, null);
        assertTrue(allProfilesThatAliceCreated.get(0).getProfileCreator().equals(alice) && allProfilesThatAliceCreated.get(1).getProfileCreator().equals(alice));
    }

    @Test
    public void testGetAllProfilesFromTheSameTarget() throws SharkKBException {
        createProfileAlice();
        createProfileFromBobOfAlice();
        List<Profile> allProfilesWhereAliceIsTheTarget = profileFactory.getProfiles(null, alice);
        assertTrue(allProfilesWhereAliceIsTheTarget.get(0).getProfileTarget().equals(alice) && allProfilesWhereAliceIsTheTarget.get(1).getProfileTarget().equals(alice));
    }
    @Test
    public void testGetProfileWithDifferentPeers() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        createProfileFromAliceOfBob();
        Profile aliceProfileOfBob = profileFactory.getProfile(alice, bob);
        assertEquals("Bob", aliceProfileOfBob.getName().getSurname());
        L.d(aliceProfileOfBob.getName().getSurname(), this);
        L.d("TestGetProfile", this);
    }

    @Test
    public void testGetProfileWithSamePeers() throws SharkKBException {
        createProfileAlice();
        Profile aliceProfile = profileFactory.getProfile(alice);
        assertEquals(aliceProfile.getName().getSurname(), "Alice");
    }

    @Test
    public void testGetProfileThatNotExists() throws SharkKBException {
        Profile claraProfile = profileFactory.getProfile(clara, clara);
        assertTrue(claraProfile == null);
    }
    @Test
    public void testGetKnowledge4Profiles() throws SharkKBException {
        //L.setLogLevel(L.LOGLEVEL_ALL);
        List<Profile> profileList = new ArrayList<Profile>();
        profileList.add(createProfileAlice());
        profileList.add(createProfileBob());
        profileList.add(createProfileFromAliceOfBob());
        profileList.add(createProfileFromBobOfAlice());
        Iterator<Profile> pi = profileList.iterator();
        Knowledge k = profileFactory.getKnowledge4Profiles(pi);
        L.d(Integer.toString(k.getNumberOfContextPoints()), this);
        for (int i = 0; i < k.getNumberOfContextPoints(); i++) {
            L.d(k.getCP(i).getContextCoordinates().getOriginator().getName(), this);
        }
        Assert.assertEquals(k.getNumberOfContextPoints(), 4);
    }
    @Test
    public void testCreateProfile() throws Exception {
        Profile testProfile = profileFactory.createProfile(bob, alice);
        assertTrue(testProfile.getProfileCreator().equals(bob) && testProfile.getProfileTarget().equals(alice));
    }

    @Test
    public void testRemoveProfile() throws SharkKBException {
        Profile aliceProfile = createProfileAlice();
        L.d(aliceProfile.getName().getSurname(), this);
        profileFactory.removeProfile(alice, alice);
        aliceProfile = profileFactory.getProfile(alice, alice);
        Assert.assertNull(aliceProfile);
    }
}