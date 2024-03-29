package ProfileTests;

import Profile.*;
import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ProfileImplTest {
    private SharkKB kb = new InMemoSharkKB();
    private ProfileFactory profileFactory;
    private String[] aliceAddresses = {"mail://alice@wonderland.net", "mail://alice@wizards.net", "http://www.sharksystem.net/alice.html"};
    private String[] aliceSis = {"http://www.sharksystem.net/alice.html"};
    private PeerSemanticTag createAlice() throws SharkKBException{
        return InMemoSharkKB.createInMemoPeerSemanticTag("Alice",
                aliceSis,
                aliceAddresses);

    }

    @Before
    public void setUp() throws SharkKBException {
        profileFactory = new ProfileFactoryImpl(kb);
    }

    @Test
    public void testGetProfileVersion() throws SharkKBException {
        PeerSemanticTag alice = createAlice();
        Profile p = profileFactory.createProfile(alice, alice);
        ProfileName profileName = new ProfileNameImpl("Alice");
        profileName.setLastName("Alpha");
        profileName.setTitle("Prof.");
        p.setName(profileName);
        profileName.setLastName("NotAlpha");
        profileName.setTitle("NotProf.");
        p.setName(profileName);
        p.setTelephoneNumber("0151-28764539", "My Mobile");
        p.setTelephoneNumber("0151-666666", "Hotline");
        Assert.assertEquals("4", p.getProfileVersion());
    }

    @Test
    public void testOverridingInformation() throws SharkKBException {
        PeerSemanticTag alice = createAlice();
        Profile p = profileFactory.createProfile(alice, alice);
        ProfileName profileName = new ProfileNameImpl("Alice");
        p.setName(profileName);
        Assert.assertEquals("Alice", p.getName().getSurname());
        profileName.setSurname("NewAlice");
        p.setName(profileName);
        Assert.assertEquals("NewAlice", p.getName().getSurname());
    }

    @Test
    public void testGetProfileTarget() throws SharkKBException {
        PeerSemanticTag alice = createAlice();
        Profile p = profileFactory.createProfile(alice, alice);
        Assert.assertEquals(alice, p.getProfileTarget());
    }

    @Test
    public void testGetProfileCreator() throws SharkKBException {
        PeerSemanticTag alice = createAlice();
        Profile p = profileFactory.createProfile(alice, alice);
        Assert.assertEquals(alice, p.getProfileCreator());
    }

    @Test
    public void testCreateSimpleProfileEntry() throws SharkKBException {
        PeerSemanticTag alice = createAlice();
        Profile p = profileFactory.createProfile(alice, alice);
        p.createProfileEntry("Name");
        p.addSubEntryInEntry("Name", "Surname", "Peter");
        p.addSubEntryInEntry("Name", "LastName", "Schmitt");
        p.addSubEntryInEntry("Name", "Alter", 22);
        Assert.assertEquals("Peter", p.getProfileEntry("Name").getEntryFromList("Surname").getContent());
        Assert.assertEquals("Schmitt", p.getProfileEntry("Name").getEntryFromList("LastName").getContent());
        Assert.assertEquals(22, p.getProfileEntry("Name").getEntryFromList("Alter").getContent());
        p.alterSubEntryContent("Name", "Surname", "HansPeter");
        Assert.assertEquals("HansPeter", p.getProfileEntry("Name").getEntryFromList("Surname").getContent());
    }

    @Test
    public void testProfileName() throws SharkKBException, IOException, ClassNotFoundException {
        PeerSemanticTag alice = createAlice();
        Profile profile = profileFactory.createProfile(alice, alice);
        ProfileName profileName = new ProfileNameImpl("Alice");
        profileName.setLastName("Alpha");
        profileName.setTitle("Prof.");
        profile.setName(profileName);
        ProfileName profileName1 = profile.getName();
        Assert.assertTrue(profileName1.getSurname().equals("Alice") && profileName1.getLastName().equals("Alpha") && profileName1.getTitle().equals("Prof."));
    }

    @Test(expected=NoSuchElementException.class)
    public void testProfilePicture() throws Exception {
        PeerSemanticTag alice = createAlice();
        Profile profile = profileFactory.createProfile(alice, alice);
        byte[] examplepicture = "I am a picture".getBytes();
        String profilbild = "ProfilePicture";
        String identifier = "mainpic";
        profile.setPicture(examplepicture, profilbild, identifier);
        Information i = profile.getPicture(identifier);
        Assert.assertEquals("I am a picture", i.getContentAsString());
        Assert.assertEquals("ProfilePicture", i.getContentType());
        profile.clearPicture(identifier);
        profile.getPicture(identifier);
    }

    @Test
    public void testGetProfileAddresses() throws Exception {
        PeerSemanticTag alice = createAlice();
        Profile profile = profileFactory.createProfile(alice, alice);
        String[] addresses = profile.getProfileAddresses();
        for (int i = 0; i < addresses.length; i++) {
            Assert.assertEquals("This addresses sould be equal", aliceAddresses[i], addresses[i]);
        }

    }

    @Test
    public void testTelephoneNumber() throws Exception {
        PeerSemanticTag alice = createAlice();
        Profile profile = profileFactory.createProfile(alice, alice);
        profile.setTelephoneNumber("0151-28764539", "My Mobile");
        Assert.assertEquals("This telephonenumbers sould be equal", "0151-28764539", profile.getTelephoneNumber("My Mobile"));
    }
}