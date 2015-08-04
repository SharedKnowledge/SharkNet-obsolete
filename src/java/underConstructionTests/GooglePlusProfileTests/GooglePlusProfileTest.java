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
        GooglePlusProfile myGooglePlusProfile = googlePlusProfileFactory.createGooglePlusProfile("Alice", "http://www.sharksystem.net/alice.html");
        myGooglePlusProfile.setFirstName("Allllice");
        myGooglePlusProfile.setLastName("Alpha");
        myGooglePlusProfile.setNickname("Ali");
        myGooglePlusProfile.setFirstName("AliceAlice");
        assertEquals(myGooglePlusProfile.getFirstName(), "AliceAlice");
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
    }

    @org.junit.Test
    public void testAsk4Profiles() throws Exception {
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
        GooglePlusProfile bobGooglePlusProfile = bobGooglePlusPF.createGooglePlusProfile(bobLocal.getName(), bobLocal.getSI()[0]);


        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(1000);

        //get profiles of Alice's contacts
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        //assertEquals("Bob profiles should be the same", bobGooglePlusProfileProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }


}