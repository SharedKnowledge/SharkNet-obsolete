package ProfileTests;

import Profile.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.peer.J2SEAndroidSharkEngine;
import net.sharkfw.system.L;

import java.util.List;

import static java.util.Collections.list;
import static org.junit.Assert.*;

/**
 * Created by s0539720 on 06.05.2015.
 */
public class ProfileKPTest {
    private long connectionTimeOut = 2000;

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    /**
     * This scenario tests the ask4Profiles function. The sendProfiles function is tested implicitly as well.
     * Alice asks for Bobs profiles. Bob sends his profile to Alice.
     *
     * @throws Exception
     */
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
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();
        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl profileName = new ProfileNameImpl("Bob");
        bobProfile.setName(profileName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //get profiles of Alice's contacts
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

    /**
     * In this scenario the configuration acceptWithoutVerification is tested.
     * Alice knows Bob and asks for his profiles. She didn't sign her message and Bobs KnowledgePort doesn't accept
     * unsigned messages. So the request doesn't go through and Alice doesn't receive Bob's  profile.
     * Then Bob sets the configuration acceptWithoutVerification to true and Alice asks for Bob's profiles again. Now
     * she should receive it.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testAcceptWithoutVerification() throws Exception {
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
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();
        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl profileName = new ProfileNameImpl("Bob");
        bobProfile.setName(profileName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //get profiles of Alice' contacts
        //Alices message is not signed. Bob doesn't accept messages without verification
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        assertNull("Bob profile should not exist", alicePF.getProfile(bob, bob));

        //bob now accepts messages without verification
        bobKP.setAcceptWithoutVerification(true);

        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

    /**
     * @throws Exception
     */
    @org.junit.Test
    public void testSignedMessage() throws Exception {

        //TODO: test if a signed message can be send if the configuration setAcceptWithoutVerification is false

    }

    /**
     * This scenario tests the the configuration sendProfiles2UnknownPeer.
     * Alice knows Bob but Bob doesn't know Alice. Alice asks for Bobs profiles but since Bob's configuration
     * sendProfiles2UnknownPeer is set to false, he doesn't send his profile to Alice.
     * Now he gets to know Alice (PeerSemanticTag alice is in Bob's KnowledgeBase). Alice asks again for Bob's
     * profiles and receives it.
     * Then Bob asks for Alice's profiles. Alice has her sendProfiles2UnknownPeer configuration set to false, but she
     * already knows Bob, so she sends her profile.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testSendProfiles2UnknownPeer() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //Alice knows Bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of Alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());

        //create Alice profile
        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);
        Profile aliceProfile = alicePF.createProfile(alice, alice);
        ProfileNameImpl aliceName = new ProfileNameImpl("Alice");
        aliceProfile.setName(aliceName);

        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();
        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl bobName = new ProfileNameImpl("Bob");
        bobProfile.setName(bobName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //get profiles of Alice's contacts
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        //Bob doesn't know alice yet and he doesn't want to send his profile to unknown peers
        assertNull("Bob profile should not exist", alicePF.getProfile(bob, bob));

        //now Bob wants to send his profile to unknown peers
        bobKP.setSendProfiles2UnknownPeer(true);

        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        //Alice doesn't want to send her profile to unknown peers. But she knows Bob, so she will send her profile to him if he asks for it
        PeerSemanticTag aliceLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        PeerSTSet bobContacts = bobKB.getPeerSTSet();
        List<PeerSemanticTag> bobContactList = list(bobContacts.peerTags());
        bobKP.ask4Profiles(bobContactList.iterator());
        Thread.sleep(1000);

        assertEquals("Alice profiles should be the same", aliceProfile.getName().getSurname(), bobPF.getProfile(alice, alice).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

    /**
     * This scenario tests the configuration sendMyProfileAutomatically.
     * Alice has her configuration sendMyProfileAutomatically set to true. So when she asks Bob for his profiles and he
     * sends them to Alice, she automatically sends her profile to Bob.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testSendMyProfileAutomatically() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //Alice knows Bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of Alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();

        //create Alices profile
        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);
        Profile aliceProfile = alicePF.createProfile(alice, alice);
        ProfileName aliceName = new ProfileNameImpl("Alice");
        aliceProfile.setName(aliceName);

        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);
        aliceKP.setSendMyProfileAutomatically(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();

        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl profileName = new ProfileNameImpl("Bob");
        bobProfile.setName(profileName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //get profiles of alice' contacts
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());
        //Alice sends her profile to Bob automatically
        assertEquals("Alice profiles should be the same", aliceProfile.getName().getSurname(), bobPF.getProfile(alice).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

    /**
     * In this scenario the configuration askForProfilesAutomatically is tested.
     * Bob's configuration aksForProfilesAutomatically is set to true. So when Alice asks for his profiles he
     * automatically asks Alice for her profiles as well. So he should receive her profile without explicitly calling
     * the ask4Profiles function.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testAsk4ProfilesAutomatically() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //Alice knows Bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of Alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();

        //create Alices profile
        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);
        Profile aliceProfile = alicePF.createProfile(alice, alice);
        ProfileName aliceName = new ProfileNameImpl("Alice");
        aliceProfile.setName(aliceName);

        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();

        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl profileName = new ProfileNameImpl("Bob");
        bobProfile.setName(profileName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendProfiles2UnknownPeer(true);
        bobKP.setAsk4ProfilesAutomatically(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //get profiles of alice' contacts
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());
        //Bob asks for Alice's profile automatically
        Thread.sleep(1000);
        assertEquals("Alice profiles should be the same", aliceProfile.getName().getSurname(), bobPF.getProfile(alice).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

    /**
     * This scenario tests the configuration acceptProfileWithDifferentOwner.
     * Alice's configuration acceptProfileWithDifferentOwner is set to false. She asks Bob for his profiles. Bob has a
     * profile of Clara which he made himself. So the profile is about someone else (Clara) than the owner (Bob). Since
     * Alice doesn't accept profiles like that, she doesn't receive Claras profile from Bob.
     * Then she sets acceptProfileWithDifferentOwner to true and asks again. Now Alice receives the profile of Clara.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testAcceptProfileWithDifferentOwner() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //Alice knows Bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of Alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());

        //create Alice profile
        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);
        Profile aliceProfile = alicePF.createProfile(alice, alice);
        ProfileNameImpl aliceName = new ProfileNameImpl("Alice");
        aliceProfile.setName(aliceName);

        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();
        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        PeerSemanticTag claraLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Clara", "http://clara.org", "tcp://localhost:5557");
        bobKB.setOwner(bob);

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl bobName = new ProfileNameImpl("Bob");
        bobProfile.setName(bobName);

        //create Claras profile
        Profile claraProfile = bobPF.createProfile(bobLocal, claraLocal);
        ProfileNameImpl claraName = new ProfileNameImpl("Clara");
        claraProfile.setName(claraName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //get profiles of alice' contacts
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        //Alice doesn't accept profiles with a different owner, so she should only receive Bobs profile
        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob).getName().getSurname());
        assertNull("Clara profile should not exist", alicePF.getProfile(bobLocal, claraLocal));

        //Now Alice accepts profiles with a different owner. She should receive Claras profile
        aliceKP.setAcceptProfileWithDifferentOwner(true);

        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);
        assertEquals("Clara profiles should be the same", claraProfile.getName().getSurname(), alicePF.getProfile(bobLocal, claraLocal).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);

    }

    /**
     * In this scenario the configuration askForProfilesAutomaticallyOnWiFiDirectConnection is tested.
     * Alice's configuration aksForProfilesAutomaticallyOnWiFiDirectConnection is set to false. So when a WiFi direct
     * connection to Bob is established (an any interest is sent) nothing happens.
     * Then she changes the configuration to true and a connection is established again. Now she asks for Bob's profiles
     * automatically. So he should receive his profile without explicitly calling the ask4Profiles function.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testAsk4ProfilesAutomaticallyOnWiFiDirectConnection() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //Alice knows Bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of Alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());

        //create Alice profile
        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);
        Profile aliceProfile = alicePF.createProfile(alice, alice);
        ProfileNameImpl aliceName = new ProfileNameImpl("Alice");
        aliceProfile.setName(aliceName);

        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();
        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl bobName = new ProfileNameImpl("Bob");
        bobProfile.setName(bobName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //on WiFi direct connection an anyinterest is sent
        SharkCS anyInterest = InMemoSharkKB.createInMemoContextCoordinates(null, null, null, null, null, null, SharkCS.DIRECTION_INOUT);
        bobKP.sendInterest(anyInterest, alice);
        Thread.sleep(1000);

        assertNull("Bob profile should not exist", alicePF.getProfile(bob));

        //Alice now wants to ask for profiles automatically on a WiFi direct connection
        aliceKP.setAsk4ProfilesAutomaticallyOnWiFiDirectConnection(true);

        bobKP.sendInterest(anyInterest, alice);
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

    /**
     * In this scenario the configuration sendProfileAutomaticallyOnWiFiDirectConnection is tested.
     * Bob's configuration sendProfileAutomaticallyOnWiFiDirectConnection is set to false. So when a WiFi direct
     * connection to Alice is established (an any interest is sent) nothing happens.
     * Then he changes the configuration to true and a connection is established again. Now Bob sends his profile
     * automatically. So alice should receive his profile without calling the ask4Profiles function.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testSendProfileAutomaticallyOnWiFiDirectConnection() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //Alice knows Bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of Alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());

        //create Alice profile
        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);
        Profile aliceProfile = alicePF.createProfile(alice, alice);
        ProfileNameImpl aliceName = new ProfileNameImpl("Alice");
        aliceProfile.setName(aliceName);

        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();
        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl bobName = new ProfileNameImpl("Bob");
        bobProfile.setName(bobName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //on WiFi direct connection an anyinterest is sent
        SharkCS anyInterest = InMemoSharkKB.createInMemoContextCoordinates(null, null, null, null, null, null, SharkCS.DIRECTION_INOUT);
        aliceKP.sendInterest(anyInterest, bob);
        Thread.sleep(1000);

        assertNull("Bob profile should not exist", alicePF.getProfile(bob));

        //Bob now wants to send his profile automatically on a WiFi direct connection
        bobKP.setSendProfileAutomaticallyOnWiFiDirectConnection(true);

        aliceKP.sendInterest(anyInterest, bob);
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

    /**
     * This scenario tests if the profile with the latest version is saved to the KnowledgeBase.
     * First Clara asks her peer Bob for his profiles and receives them. Then Bob changes hes profile. So
     * when Alice asks for Bobs profiles she receives a different profile of Bob than Clara did. Now Alice gets to know
     * Clara and asks for her profiles as well. Clara sends her old profile of Bob, but since Alice has the profile
     * with the latest profile version, her profile of Bob doesn't change.
     * Finally Clara asks her peers for their profiles again. The new profile of Bob is sent to her and since it has the
     * latest profile version the old profile of Bob in Clara's KnowledgeBase is overwritten with Bob's current profile.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testLatestProfileVersion() throws Exception {
        L.setLogLevel(L.LOGLEVEL_ALL);
        J2SEAndroidSharkEngine aliceSE = new J2SEAndroidSharkEngine();
        SharkKB aliceKB = new InMemoSharkKB();

        PeerSemanticTag alice = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        aliceKB.setOwner(alice);

        //Alice knows Bob
        PeerSemanticTag bob = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of Alice
        PeerSTSet aliceContacts = aliceKB.getPeerSTSet();
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());

        //create Alices profile
        ProfileFactory alicePF = new ProfileFactoryImpl(aliceKB);
        Profile aliceProfile = alicePF.createProfile(alice, alice);
        ProfileName aliceName = new ProfileNameImpl("Alice");
        aliceProfile.setName(aliceName);

        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);
        aliceKP.setSendProfiles2UnknownPeer(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create Bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();
        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bobLocal);

        //Bob knows Alice and Clara
        PeerSemanticTag aliceLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
        PeerSemanticTag claraLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Clara", "http://clara.org", "tcp://localhost:5557");

        //create contactlist of Bob
        PeerSTSet bobContacts = bobKB.getPeerSTSet();
        List<PeerSemanticTag> bobContactList = list(bobContacts.peerTags());

        //create Bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl bobName = new ProfileNameImpl("Bob");
        bobProfile.setName(bobName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setAcceptWithoutVerification(true);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //create Clara
        J2SEAndroidSharkEngine claraSE = new J2SEAndroidSharkEngine();
        SharkKB claraKB = new InMemoSharkKB();
        PeerSemanticTag claraPeer = claraKB.getPeerSTSet().createPeerSemanticTag("Clara", "http://clara.org", "tcp://localhost:5557");
        claraKB.setOwner(claraPeer);

        //Clara knows Bob
        PeerSemanticTag bobPeer = aliceKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");

        //create contactlist of Clara
        PeerSTSet claraContacts = claraKB.getPeerSTSet();
        List<PeerSemanticTag> claraContactList = list(claraContacts.peerTags());

        //create Claras profile
        ProfileFactory claraPF = new ProfileFactoryImpl(bobKB);
        Profile claraProfile = claraPF.createProfile(claraPeer, claraPeer);
        ProfileNameImpl claraName = new ProfileNameImpl("Clara");
        claraProfile.setName(claraName);

        ProfileKP claraKP = new ProfileKP(claraSE, claraKB, claraPF, claraPeer);
        claraKP.setAcceptWithoutVerification(true);
        claraKP.setSendProfiles2UnknownPeer(true);

        claraSE.startTCP(5557);
        claraSE.setConnectionTimeOut(connectionTimeOut);

        //Clara asks for profiles
        claraKP.ask4Profiles(claraContactList.iterator());
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), claraPF.getProfile(bob).getName().getSurname());

        //Now Clara knows Alice too
        PeerSemanticTag alicePeer = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");

        //Bob changes his profile
        bobName.setSurname("Bobby");
        bobProfile.setName(bobName);

        //Alice asks for Bobs profiles
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob).getName().getSurname());

        //Now Alice knows Clara and wants to ask for her profiles
        PeerSemanticTag clara = aliceKB.getPeerSTSet().createPeerSemanticTag("Clara", "http://clara.org", "tcp://localhost:5557");
        aliceContactList = list(aliceKB.getPeerSTSet().peerTags());
        aliceContactList.remove(1); //remove Bob so only Clara sends the profile ob Bob

        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);

        //Since Clara has an older version ob Bobs profile, it should still be the same
        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob).getName().getSurname());

        claraKP.ask4Profiles(claraContactList.iterator());
        Thread.sleep(1000);

        //Clara should now have the latest version ob Bob's profile as well
        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), claraPF.getProfile(bob).getName().getSurname());


        bobSE.stopTCP();
        aliceSE.stopTCP();
        claraSE.stopTCP();
        Thread.sleep(100);
    }

}