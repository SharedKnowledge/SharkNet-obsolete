package Profile.ProfileTests;

import Profile.*;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mr.T on 29.05.2015.
 */
public class ProfileFactoryImplTest {
    private SharkKB kb = new InMemoSharkKB();
    private ProfileFactory profileFactory;
    private String[] aliceAddresses = {"mail://alice@wonderland.net", "mail://alice@wizards.net", "http://www.sharksystem.net/alice.html"};
    private PeerSemanticTag alice = this.kb.createPeerSemanticTag("Alice",
            "http://www.sharksystem.net/alice.html",
    aliceAddresses);
    private String[] bobAddresses = {"mail://bob@sharknet.net", "mail://bob@wizards.net", "http://www.sharksystem.net/bob.html"};
    private PeerSemanticTag bob = this.kb.createPeerSemanticTag("Bob", "http://www.sharksystem.net/bob.html", bobAddresses);

    public ProfileFactoryImplTest() throws SharkKBException {
        profileFactory = new ProfileFactoryImpl(kb);
    }

    private void createProfileAlice() throws SharkKBException {
        Profile profile = profileFactory.createProfile(alice, alice);
        ProfileName profileName = new ProfileNameImpl("Alice");
        profileName.setLastName("Alpha");
        profileName.setTitle("Prof.");
        profile.setName(profileName);
    }

    private void createProfileBob() throws SharkKBException {
        Profile profile = profileFactory.createProfile(bob, bob);
        ProfileName profileName = new ProfileNameImpl("Bob");
        profileName.setLastName("Beta");
        profileName.setTitle("Dr.Dr.");
        profile.setName(profileName);
    }

    @Test
    public void testGetProfile() throws Exception {
        createProfileAlice();
        Profile aliceProfile = profileFactory.getProfile(alice, alice);
        assertEquals("Alice", aliceProfile.getName().getSurname());
        System.out.println(aliceProfile.getName().getSurname());
    }

    @Test
    public void testCreateProfile() throws Exception {

    }
}