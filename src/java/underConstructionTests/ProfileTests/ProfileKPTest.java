package ProfileTests;

import Profile.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.peer.J2SEAndroidSharkEngine;
import net.sharkfw.system.L;

import static org.junit.Assert.*;

/**
 * Created by _Wayne- on 06.05.2015.
 */
public class ProfileKPTest {
    private long connectionTimeOut = 2000;

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    //Alice knows Bob and asks for his profile
    @org.junit.Test
    public void testAsk4Profiles() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //alice knows bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();

        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);

        //create empty bob profile
        //Profile bobTestProfile = alicePF.createProfile(bob, bob);

        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();

        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        PeerSemanticTag aliceLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");

        //create contactlist of bob
        PeerSTSet bobContacts = bobKB.getPeerSTSet();

        //create bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl profileName = new ProfileNameImpl("Bob");
        bobProfile.setName(profileName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //get profiles of alice' contacts
        aliceKP.ask4Profiles(aliceContacts);
        Thread.sleep(1000);
        //Thread.sleep(Integer.MAX_VALUE);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());
    }

    @org.junit.Test
    public void testSendProfileAutomatically() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //alice knows bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();

        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);

        //create alices profile
        Profile aliceProfile = alicePF.createProfile(alice, alice);
        ProfileName aliceName = new ProfileNameImpl("Alice");
        aliceProfile.setName(aliceName);

        //create empty bob profile
        //Profile bobTestProfile = alicePF.createProfile(bob, bob);

        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);
        aliceKP.setSendMyProfileAutomatically(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(10000);

        //create bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();

        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        PeerSemanticTag aliceLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");

        //create contactlist of bob
        PeerSTSet bobContacts = bobKB.getPeerSTSet();

        //create bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl profileName = new ProfileNameImpl("Bob");
        bobProfile.setName(profileName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendMyProfileAutomatically(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(10000);

        //get profiles of alice' contacts
        aliceKP.ask4Profiles(aliceContacts);
        Thread.sleep(1000);
        //Thread.sleep(Integer.MAX_VALUE);
        //aliceSE.setSilentPeriod(100);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());
        assertEquals("Alice profiles should be the same", aliceProfile.getName().getSurname(), bobPF.getProfile(alice).getName().getSurname());
    }

}