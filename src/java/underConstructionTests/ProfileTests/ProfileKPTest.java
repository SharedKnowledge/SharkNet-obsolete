package ProfileTests;

import Profile.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.filesystem.FSSharkKB;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkCS;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.peer.J2SEAndroidSharkEngine;
import net.sharkfw.pki.SharkCertificate;
import net.sharkfw.pki.SharkPublicKeyStorage;
import net.sharkfw.pki.SigningPeer;
import net.sharkfw.system.L;

import java.security.*;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.list;
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

    /*
    @org.junit.Test
    public void testMessageSigning() throws Exception {
        // kb to store keys
        FSSharkKB baseKB = new FSSharkKB("testKB");
        //each store needs an engine - WHY IS THAT?
        J2SEAndroidSharkEngine se = new J2SEAndroidSharkEngine(
        );
        // create storaga
        SharkPublicKeyStorage ks = new FSSharkKBPublicKeyStorage(baseKB, se);
        //generate key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();

        // create or get private key;
        PrivateKey privateKey = pair.getPrivate();;
        // save with key store
        ks.setPrivateKey(privateKey);
        // define a peer
        PeerSemanticTag peer = baseKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");;
        // assume that’s peers public key
        PublicKey publicKey = pair.getPublic();
        // assume we have signed that public key
        byte[] signature = null;
        // create a structure
        SigningPeer signingPeer = new SigningPeer(peer, signature);
        long valid = 1000 * 60 * 24 * 12 * 365; // 365 Tage
        ks.addPublicKey(publicKey, peer, signingPeer, valid);
        // retake that structure from key store
        SharkCertificate certificate = ks.getCertificate(peer);
        // get private key from key store (there is only one)
        ks.getPrivateKey();
    }*/

    //Alice knows Bob and asks for his profile
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

        //get profiles of Alice' contacts
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(100);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

    @org.junit.Test
    public void testAcceptWithoutVerification() throws Exception {
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
        ProfileKP aliceKP = new ProfileKP(aliceSE, aliceKB, alicePF, alice);
        aliceKP.setAcceptWithoutVerification(true);

        aliceSE.startTCP(5555);
        aliceSE.setConnectionTimeOut(connectionTimeOut);

        //create bob
        J2SEAndroidSharkEngine bobSE = new J2SEAndroidSharkEngine();
        SharkKB bobKB = new InMemoSharkKB();
        PeerSemanticTag bobLocal = bobKB.getPeerSTSet().createPeerSemanticTag("Bob", "http://bob.org", "tcp://localhost:5556");
        bobKB.setOwner(bob);

        //create bobs profile
        ProfileFactory bobPF = new ProfileFactoryImpl(bobKB);
        Profile bobProfile = bobPF.createProfile(bobLocal, bobLocal);
        ProfileNameImpl profileName = new ProfileNameImpl("Bob");
        bobProfile.setName(profileName);

        ProfileKP bobKP = new ProfileKP(bobSE, bobKB, bobPF, bobLocal);
        bobKP.setSendProfiles2UnknownPeer(true);

        bobSE.startTCP(5556);
        bobSE.setConnectionTimeOut(connectionTimeOut);

        //get profiles of alice' contacts
        //alices message is not signed. bob doesn't accept messages without verification
        List<PeerSemanticTag> aliceContactList = list(aliceContacts.peerTags());
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);
        //Thread.sleep(Integer.MAX_VALUE);

        assertNull("Bob profile should not exist", alicePF.getProfile(bob, bob));

        //bob now accepts messages without verification
        bobKP.setAcceptWithoutVerification(true);

        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(1000);
        //Thread.sleep(Integer.MAX_VALUE);
        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        //TODO: wie verifiziert man?

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

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

        //get profiles of alice' contacts
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(100);

        //bob doesn't know alice yet and he doesn't want to send his profile to unknown peers
        assertNull("Bob profile should not exist", alicePF.getProfile(bob, bob));

        //now bob wants to send his profile to unknown peers
        bobKP.setSendProfiles2UnknownPeer(true);

        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(100);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        //Alice doesn't want to send her profile to unknown peers. But she knows Bob, so she will send her profile to him if he asks for it
        PeerSTSet bobContacts = bobKB.getPeerSTSet();
        List<PeerSemanticTag> bobContactList = list(bobContacts.peerTags());
        bobContactList.add(alice);
        bobKP.ask4Profiles(bobContactList.iterator());
        Thread.sleep(100);

        assertEquals("Alice profiles should be the same", aliceProfile.getName().getSurname(), bobPF.getProfile(alice, alice).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

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
        Thread.sleep(100);
        //aliceSE.setSilentPeriod(100);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());
        //Alice sends her profile to Bob automatically
        assertEquals("Alice profiles should be the same", aliceProfile.getName().getSurname(), bobPF.getProfile(alice).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }


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
        Thread.sleep(100);
        //aliceSE.setSilentPeriod(100);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());
        //Bob asks for Alice's profile automatically
        Thread.sleep(100);
        assertEquals("Alice profiles should be the same", aliceProfile.getName().getSurname(), bobPF.getProfile(alice).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

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
        Thread.sleep(100);

        //Alice doesn't accept profiles with a different owner, so she should only receive Bobs profile
        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob).getName().getSurname());
        assertNull("Clara profile should not exist", alicePF.getProfile(bobLocal, claraLocal));

        //Now Alice accepts profiles with a different owner. She should receive Claras profile
        aliceKP.setAcceptProfileWithDifferentOwner(true);

        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(100);
        assertEquals("Clara profiles should be the same", claraProfile.getName().getSurname(), alicePF.getProfile(bobLocal, claraLocal).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);

    }

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
        Thread.sleep(100);

        assertNull("Bob profile should not exist", alicePF.getProfile(bob));

        //Alice now wants to ask for profiles automatically on a WiFi direct connection
        aliceKP.setAsk4ProfilesAutomaticallyOnWiFiDirectConnection(true);

        bobKP.sendInterest(anyInterest, alice);
        Thread.sleep(100);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

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
        Thread.sleep(100);

        assertNull("Bob profile should not exist", alicePF.getProfile(bob));

        //Bob now wants to send his profile automatically on a WiFi direct connection
        bobKP.setSendProfileAutomaticallyOnWiFiDirectConnection(true);

        aliceKP.sendInterest(anyInterest, bob);
        Thread.sleep(100);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob, bob).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        Thread.sleep(100);
    }

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

        //Clara knows Bob and Alice
        PeerSemanticTag alicePeer = aliceKB.getPeerSTSet().createPeerSemanticTag("Alice", "http://alice.org", "tcp://localhost:5555");
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
        Thread.sleep(100);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), claraPF.getProfile(bob).getName().getSurname());

        //Bob changes his profile
        bobName.setSurname("Bobby");
        bobProfile.setName(bobName);

        //Alice asks for Bobs profiles
        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(100);

        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob).getName().getSurname());

        //Now Alice knows Clara and wants to ask for her profiles
        PeerSemanticTag clara = aliceKB.getPeerSTSet().createPeerSemanticTag("Clara", "http://clara.org", "tcp://localhost:5557");
        aliceContactList = list(aliceKB.getPeerSTSet().peerTags());
        aliceContactList.remove(1); //remove Bob

        aliceKP.ask4Profiles(aliceContactList.iterator());
        Thread.sleep(100);

        //Since Clara has an older version ob Bobs profile, it should still be the same
        assertEquals("Bob profiles should be the same", bobProfile.getName().getSurname(), alicePF.getProfile(bob).getName().getSurname());

        bobSE.stopTCP();
        aliceSE.stopTCP();
        claraSE.stopTCP();
        Thread.sleep(100);
    }

}