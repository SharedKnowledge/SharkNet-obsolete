package ProfileTests;

import Profile.*;
import junit.framework.Assert;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.SpatialSemanticTag;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thilo Stegemann on 13.07.2015.
 */
public class EntryImplTest {
    private SharkKB kb = new InMemoSharkKB();
    private ProfileFactory profileFactory;
    private String[] aliceAddresses = {"mail://alice@wonderland.net", "mail://alice@wizards.net", "http://www.sharksystem.net/alice.html"};
    private String[] aliceSis = {"http://www.sharksystem.net/alice.html"};
    private PeerSemanticTag alice = InMemoSharkKB.createInMemoPeerSemanticTag("Alice",
            aliceSis,
            aliceAddresses);
    public EntryImplTest() throws SharkKBException {
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

    @Test
    public void testCreatingProfileNameEntry() throws SharkKBException {
        Profile aliceProfile = createProfileAlice();
        List<Entry<?>> entryList = new ArrayList<>();
        entryList.add(new EntryImpl<String>("Surname", "Alice"));
        entryList.add(new EntryImpl<String>("LastName", "Alpha"));
        entryList.add(new EntryImpl<String>("Title", "Dr."));
        aliceProfile.createProfileEntry("ProfileName", entryList);
        entryList = (List<Entry<?>>) aliceProfile.getSubEntry("ProfileName", "ProfileName").getContent();
        assertEquals("Alice", entryList.get(0).getContent());
        assertEquals("Alpha", entryList.get(1).getContent());
        assertEquals("Dr.", entryList.get(2).getContent());
    }
    @Test
    public void testCreateProfileName() throws SharkKBException {
        Profile aliceProfile = createProfileAlice();
        aliceProfile.createProfileEntry("Surname", "Hannes");
        Profile newAlice = profileFactory.getProfile(alice, alice);
        assertEquals("Hannes", newAlice.getProfileEntry("Surname").getContent());
    }
    @Test
    public void testCreatingProfileProblemEntry() throws SharkKBException, IOException, ClassNotFoundException {
        Profile aliceProfile = createProfileAlice();
        byte[] bytes = Serializer.serialize("Test content" + 123 + "mehr content");
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>("ProblemName", "Bridge is destroyed"));
        entryList.add(new EntryImpl<byte[]>("Content", bytes));
        entryList.add(new EntryImpl<SpatialSemanticTag>("LocationOfTheProblem"));
        aliceProfile.createProfileEntry("ProfileProblem", entryList);

        Profile newAlice = profileFactory.getProfile(alice, alice);
        entryList = (List<Entry<?>>) newAlice.getProfileEntry("ProfileProblem").getContent();
        assertEquals("Bridge is destroyed", entryList.get(0).getContent());
        assertEquals(Serializer.deserialize(bytes), Serializer.deserialize((byte[]) entryList.get(1).getContent()));
    }

    @Test
    public void testGetEntry() throws Exception {
        Profile aliceProfile = createProfileAlice();
        aliceProfile.createProfileEntry("Root");
        aliceProfile.createSubEntry("Root", "Root", "Node1", "Information");
        assertEquals("Information", aliceProfile.getSubEntry("Root", "Node1").getContent());
    }
}