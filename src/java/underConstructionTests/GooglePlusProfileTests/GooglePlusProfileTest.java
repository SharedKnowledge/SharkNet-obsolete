package GooglePlusProfileTests;

import GooglePlusProfile.GooglePlusProfile;
import GooglePlusProfile.GooglePlusProfileFactory;
import GooglePlusProfile.GooglePlusProfileFactoryImpl;
import Profile.ProfileFactory;
import Profile.ProfileFactoryImpl;
import Profile.ProfileKP;
import net.sharkfw.knowledgeBase.PeerSTSet;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.peer.J2SEAndroidSharkEngine;
import net.sharkfw.system.L;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.list;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testGooglePlusProfile() throws Exception {
        //##########CreatingProfile##########
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.setFirstName("Allllice");
        myGooglePlusProfile.setLastName("Alpha");
        myGooglePlusProfile.setNickname("Ali");
        myGooglePlusProfile.setFirstName("Alice");
        myGooglePlusProfile.setTagline("Hi I´m Alice I like to solve complex problems and writing good code.");
        myGooglePlusProfile.setIntroduction("My story begins a long time ago, I was 14 when I created my first little program." +
                "At this moment I was so interested in programing it even becomes one of my greatest hobbies.");
        myGooglePlusProfile.setBraggingRights("I wrote my own linux kernel.");
        myGooglePlusProfile.setOccupation("I do my bachelors degree of applied science at the HTW subject: applied computing");
        myGooglePlusProfile.setSkills("Java, Scala, C++, C, PHP, HTML, CSS");
        myGooglePlusProfile.addEmployment("HTW", "Programmer", 2012, 2015, true, "I write Scala and Java tests.");
        myGooglePlusProfile.addEducation("University of Applied Sciences", "Applied Computing", 2012, 2015, true, "I learned a lot about programming and software engineering.");
        myGooglePlusProfile.addPlace("Berlin", true);
        myGooglePlusProfile.addPlace("London", false);
        myGooglePlusProfile.setGender("Female");
        myGooglePlusProfile.setLookingforFriends(true);
        myGooglePlusProfile.setLookingforDating(true);
        myGooglePlusProfile.setLookingforNetworking(true);
        myGooglePlusProfile.setLookingforRelationship(false);
        myGooglePlusProfile.setBirthday("03.12.1992");
        myGooglePlusProfile.setRelationship("I´m in a relation with Bob.");
        myGooglePlusProfile.addOtherName("Jizy");
        myGooglePlusProfile.addHomeContact("Mobile", "0151-2357823");
        myGooglePlusProfile.addWorkContact("Phone", "030-28665432");
        myGooglePlusProfile.addWorkContact("Address", "Rathenau Str. 42");
        myGooglePlusProfile.addWorkContact("Fax", "030-28665433");
        myGooglePlusProfile.addOtherProfiles("BobsProfile", "https://google/googlePlus/Profiles/Bob");
        myGooglePlusProfile.addContributorTo("SharkNet", "http://www.sharksystem.net/apps.html");
        myGooglePlusProfile.addLinks("My favorite website", "http://google.com");

        //##########ReadingTheProfile##########
        assertEquals("Alice", myGooglePlusProfile.getFirstName());
        assertEquals("Alpha", myGooglePlusProfile.getLastName());
        assertEquals("Ali", myGooglePlusProfile.getNickname());
        assertEquals("Hi I´m Alice I like to solve complex problems and writing good code.", myGooglePlusProfile.getTagline());
        assertEquals("My story begins a long time ago, I was 14 when I created my first little program." +
                     "At this moment I was so interested in programing it even becomes one of my greatest hobbies.", myGooglePlusProfile.getIntroduction());
        assertEquals("I wrote my own linux kernel.", myGooglePlusProfile.getBraggingRights());
        assertEquals("I do my bachelors degree of applied science at the HTW subject: applied computing", myGooglePlusProfile.getOccupation());
        assertEquals("Java, Scala, C++, C, PHP, HTML, CSS", myGooglePlusProfile.getSkills());
        assertEquals("HTW", myGooglePlusProfile.getEmploymentName("0"));
        assertEquals("Programmer", myGooglePlusProfile.getJobTitle("0"));
        assertEquals(2012, myGooglePlusProfile.getStartOfEmployment("0"));
        assertEquals(2015, myGooglePlusProfile.getEndOfEmployment("0"));
        assertEquals(true, myGooglePlusProfile.getIsEmploymentCurrent("0"));
        assertEquals("I write Scala and Java tests.", myGooglePlusProfile.getJobDescription("0"));
        assertEquals("University of Applied Sciences", myGooglePlusProfile.getSchoolName("0"));
        assertEquals("Applied Computing", myGooglePlusProfile.getMajor("0"));
        assertEquals(2012, myGooglePlusProfile.getStartOfEducation("0"));
        assertEquals(2015, myGooglePlusProfile.getEndOfEducation("0"));
        assertEquals(true, myGooglePlusProfile.getIsEducationCurrent("0"));
        assertEquals("I learned a lot about programming and software engineering.", myGooglePlusProfile.getCourseDescription("0"));
        assertEquals("Berlin", myGooglePlusProfile.getCity("0"));
        assertEquals(true, myGooglePlusProfile.getIsPlaceCurrent("0"));
        assertEquals("London", myGooglePlusProfile.getCity("1"));
        assertEquals(false, myGooglePlusProfile.getIsPlaceCurrent("1"));
        assertEquals("Female", myGooglePlusProfile.getGender());
        assertEquals(true, myGooglePlusProfile.getLookingForFriends());
        assertEquals(true, myGooglePlusProfile.getLookingForDating());
        assertEquals(true, myGooglePlusProfile.getLookingForNetworking());
        assertEquals(false, myGooglePlusProfile.getLookingForRelationship());
        assertEquals("03.12.1992", myGooglePlusProfile.getBirthday());
        assertEquals("I´m in a relation with Bob.", myGooglePlusProfile.getRelationship());
        assertEquals("Jizy", myGooglePlusProfile.getOtherName("0"));
        assertTrue(myGooglePlusProfile.getHomeContactType(0).equals("Mobile") && myGooglePlusProfile.getHomeContactInfo(0).equals("0151-2357823"));
        assertTrue(myGooglePlusProfile.getWorkContactType(0).equals("Phone") && myGooglePlusProfile.getWorkContactInfo(0).equals("030-28665432"));
        assertTrue(myGooglePlusProfile.getWorkContactType(1).equals("Address") && myGooglePlusProfile.getWorkContactInfo(1).equals("Rathenau Str. 42"));
        assertTrue(myGooglePlusProfile.getWorkContactType(2).equals("Fax") && myGooglePlusProfile.getWorkContactInfo(2).equals("030-28665433"));
        assertTrue(myGooglePlusProfile.getOtherProfilesLabel(0).equals("BobsProfile") && myGooglePlusProfile.getOtherProfilesUrl(0).equals("https://google/googlePlus/Profiles/Bob"));
        assertTrue(myGooglePlusProfile.getContributorsLabel(0).equals("SharkNet") && myGooglePlusProfile.getContributorsUrl(0).equals("http://www.sharksystem.net/apps.html"));
        assertTrue(myGooglePlusProfile.getLinkLabel(0).equals("My favorite website") && myGooglePlusProfile.getLinkUrl(0).equals("http://google.com"));
    }

    @Test(expected=NullPointerException.class)
    public void testRemoveEmployment() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.addEmployment("HTW", "Programmer", 2012, 2015, true, "I write Scala and Java tests.");
        myGooglePlusProfile.removeEmployment("0");
        myGooglePlusProfile.getEndOfEmployment("0");
    }

    @Test(expected=NullPointerException.class)
    public void testRemoveEducation() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.addEducation("University of Applied Sciences", "Applied Computing", 2012, 2015, true, "I learned a lot about programming and software engineering.");
        myGooglePlusProfile.removeEducation("0");
        myGooglePlusProfile.getStartOfEducation("0");
    }

    @Test(expected=NullPointerException.class)
    public void testRemovePlace() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.addPlace("Berlin", true);
        myGooglePlusProfile.addPlace("London", false);
        myGooglePlusProfile.removePlace("0");
        myGooglePlusProfile.getCity("0");
    }

    @Test(expected=NullPointerException.class)
    public void testRemoveOtherName() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.addOtherName("Jizy");
        myGooglePlusProfile.removeOtherName("0");
        myGooglePlusProfile.getOtherName("0");
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testRemoveHomeContact() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.addHomeContact("Mobile", "0151-2357823");
        myGooglePlusProfile.removeHomeContact("0");
        myGooglePlusProfile.getHomeContactInfo(0);
    }

    @Test
    public void testRemoveWorkContact() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.addWorkContact("Phone", "030-28665432");
        myGooglePlusProfile.addWorkContact("Address", "Rathenau Str. 42");
        myGooglePlusProfile.addWorkContact("Fax", "030-28665433");
        myGooglePlusProfile.removeWorkContact("0");
        assertEquals("Address", myGooglePlusProfile.getWorkContactType(0));
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testRemoveOtherProfiles() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.addOtherProfiles("BobsProfile", "https://google/googlePlus/Profiles/Bob");
        myGooglePlusProfile.removeOtherProfiles("0");
        myGooglePlusProfile.getOtherProfilesLabel(0);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testRemoveContributorTo() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.addContributorTo("SharkNet", "http://www.sharksystem.net/apps.html");
        myGooglePlusProfile.removeContributor("0");
        myGooglePlusProfile.getContributorsLabel(0);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testRemoveLinks() throws Exception {
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.addLinks("My favorite website", "http://google.com");
        myGooglePlusProfile.removeLinks("0");
        myGooglePlusProfile.getLinkLabel(0);
    }

    @org.junit.Test
    public void testGooglePlusProfileExchange() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //Alice knows Bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of Alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();
        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);
        GooglePlusProfileFactory aliceGooglePlusPF = new GooglePlusProfileFactoryImpl(alicePF);
        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(1000);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();
        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        GooglePlusProfileFactory bobGooglePlusPF = new GooglePlusProfileFactoryImpl(bobPF);
        GooglePlusProfile bobProfile = bobGooglePlusPF.createGooglePlusProfile(bobLocal.getName(), bobLocal.getSI()[0]);
        bobProfile.setFirstName("Bob");

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(1000);

        //get profiles of Alice's contacts
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getFirstName(), aliceGooglePlusPF.getGooglePlusProfile(bob.getName(), bob.getSI()[0]).getFirstName());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }


}