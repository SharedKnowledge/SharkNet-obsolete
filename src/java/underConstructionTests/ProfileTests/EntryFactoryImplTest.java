package ProfileTests;

import Profile.*;
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

/**
 * Created by Mr.T on 13.07.2015.
 */
public class EntryFactoryImplTest {
    private SharkKB kb = new InMemoSharkKB();
    private ProfileFactory profileFactory;
    private String[] aliceAddresses = {"mail://alice@wonderland.net", "mail://alice@wizards.net", "http://www.sharksystem.net/alice.html"};
    private String[] aliceSis = {"http://www.sharksystem.net/alice.html"};
    private PeerSemanticTag alice = InMemoSharkKB.createInMemoPeerSemanticTag("Alice",
            aliceSis,
            aliceAddresses);
    public EntryFactoryImplTest() throws SharkKBException {
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
    public void testCreateEntry() throws Exception {
        EntryFactory factory = new EntryFactoryImpl();
        factory.createEntry("Alter", 22);
        factory.createEntry("Name", "Thomas");
        factory.createEntry("Geschlecht", "M");
        factory.removeEntry("Name");
        Entry<?> integerEntry = factory.getEntry("Alter");
        System.out.println(integerEntry.getEntryName());
        System.out.println(integerEntry.getContent());
        Iterator<Entry<?>> ie = factory.getEntries();
        while (ie.hasNext()) {
            System.out.println(ie.next().getEntryName());
        }
    }

    @Test
    public void testCreatingProfileNameEntry() {
        EntryFactory factory = new EntryFactoryImpl();
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>("Surname"));
        entryList.add(new EntryImpl<String>("LastName"));
        entryList.add(new EntryImpl<String>("Title"));
        factory.createEntry("ProfileName", entryList);
        Entry<?> profileNameEntry = factory.getEntry("ProfileName");
        List<Entry<String>> entryList1 = (List<Entry<String>>) profileNameEntry.getContent();
        if (entryList1.get(0).getEntryName().equals("Surname")) {
            entryList1.get(0).setContent("Max");
        }
        System.out.println(entryList1.get(0).getContent());
    }
    @Test
    public void testCreateProfileName() throws SharkKBException {
        Profile aliceProfile = createProfileAlice();
        aliceProfile.addProfileEntry("Surname", "Hannes");
        Profile newAlice = profileFactory.getProfile(alice, alice);
        Entry<?> entry = newAlice.getProfileEntry("Surname");
        System.out.println(entry.getEntryName());
    }
    @Test
    public void testCreatingProfileProblemEntry() throws SharkKBException, IOException, ClassNotFoundException {
        Profile aliceProfile = createProfileAlice();
        byte[] bytes = Serializer.serialize("Test content" + 123 + "mehr content");
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>("ProblemName", "Bridge is destroyed"));
        entryList.add(new EntryImpl<byte[]>("Content", bytes));
        entryList.add(new EntryImpl<SpatialSemanticTag>("LocationOfTheProblem"));
        aliceProfile.addProfileEntry("ProfileProblem", entryList);

        Profile newAlice = profileFactory.getProfile(alice, alice);
        Entry<?> profileProblem = newAlice.getProfileEntry("ProfileProblem");
        List<Entry<?>> entryListNew = (List<Entry<?>>) profileProblem.getContent();
        Entry<String> problemName = (Entry<String>) entryListNew.get(0);
        System.out.println(problemName.getContent());
        Entry<byte[]> content = (Entry<byte[]>) entryListNew.get(1);
        System.out.println(Serializer.deserialize(content.getContent()));
    }

    @Test
    public void testGetEntry() throws Exception {

    }

    @Test
    public void testGetEntries() throws Exception {

    }
}