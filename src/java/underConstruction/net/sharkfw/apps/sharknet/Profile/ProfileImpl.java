package Profile;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.InMemoInformation;
import net.sharkfw.system.L;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mr.T on 16.04.2015.
 */
public class ProfileImpl implements Profile, Serializable {
    public static final String PROFILEENTRYFACTORY = "ProfileEntryFactory";
    public static final String PROFILENAME = "ProfileName";
    public static final String PROFILEBIRTHDAY = "ProfileBirthday";
    public static final String KNOWNLANGUAGES = "KnownLanguages";
    public static final String CURRENTPOSITION = "CurrentPosition";
    private ContextPoint cp;
    private SharkKB kb;
    private PeerSemanticTag profileTarget = null;
    private PeerSemanticTag profileCreator = null;
    private EntryFactory entryFactory = null;
    ProfileImpl(SharkKB kb, PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException {
        this.kb = kb;
        this.profileTarget = target;
        this.profileCreator = creator;
        this.entryFactory = new EntryFactoryImpl();
        SemanticTag pr = ProfileFactoryImpl.getProfileSemanticTag();
        pr.setProperty(Profile.SHARK_PROFILE_VERSION_PROPERTY, "0");
        ContextCoordinates cc = kb.createContextCoordinates(pr, creator, target, null, null, null, SharkCS.DIRECTION_INOUT);
        cp = this.kb.createContextPoint(cc);
    }


    ProfileImpl(SharkKB kb, ContextPoint cp) {
        this.kb = kb;
        this.cp = cp;
        profileTarget = cp.getContextCoordinates().getPeer();
        profileCreator = cp.getContextCoordinates().getOriginator();
    }
    //This Function should increase a property every time when a setter changes something of the Profile
    //The name of the property should be very unique
    //private increaseVersion
    private void increaseVersion() throws SharkKBException {
        String version = cp.getContextCoordinates().getTopic().getProperty(Profile.SHARK_PROFILE_VERSION_PROPERTY);
        int versionCount = Integer.parseInt(version);
        versionCount += 1;
        cp.getContextCoordinates().getTopic().setProperty(Profile.SHARK_PROFILE_VERSION_PROPERTY, Integer.toString(versionCount));
    }

    private void addAndSerializeObjInContextPoint(String objName, Object obj) throws SharkKBException {
        Information i = new InMemoInformation();
        try {
            i.setContent(Serializer.serialize(obj));
        } catch (IOException e) {
            throw new SharkKBException(e.getMessage());
        }
        i.setName(objName);
        if (cp.getInformation(objName).hasNext()) {
            cp.removeInformation(cp.getInformation(objName).next());
        }
        cp.addInformation(i);
    }

    private Object getAndDeserializeObjFromContextPoint(String objName) throws SharkKBException {
        if (cp.getInformation(objName).hasNext()) {
            Information i = cp.getInformation(objName).next();
            try {
                return Serializer.deserialize(i.getContentAsByte());
            } catch (IOException e) {
                throw new SharkKBException(e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new SharkKBException(e.getMessage());
            }
        }
        else {
            return null;
        }
    }
    @Override
    public ContextPoint getContextPoint() {
        return cp;
    }
    @Override
    public String getProfileVersion() throws SharkKBException {
        return this.cp.getContextCoordinates().getTopic().getProperty(Profile.SHARK_PROFILE_VERSION_PROPERTY);
    }

    @Override
    public PeerSemanticTag getProfileTarget() {
        return profileTarget;
    }

    @Override
    public PeerSemanticTag getProfileCreator() {
        return profileCreator;
    }

    @Override
    public <T> void addProfileEntry(String identifier, T entryContent) throws SharkKBException {
        entryFactory.createEntry(identifier, entryContent);
        addAndSerializeObjInContextPoint(PROFILEENTRYFACTORY, entryFactory);
    }

    @Override
    public Entry<?> getProfileEntry(String identifier) throws SharkKBException {
        entryFactory = (EntryFactory) getAndDeserializeObjFromContextPoint(PROFILEENTRYFACTORY);
        return entryFactory.getEntry(identifier);
    }

    @Override
    public Iterator<Entry<?>> getAllProfileEntries() {
        try {
            entryFactory = (EntryFactory) getAndDeserializeObjFromContextPoint(PROFILEENTRYFACTORY);
        } catch (SharkKBException e) {
            L.e(e.getMessage(), this);
        }
        return entryFactory.getEntries();
    }

    @Override
    public void clearProfileEntry(String identifier) {
        try {
            entryFactory = (EntryFactory) getAndDeserializeObjFromContextPoint(PROFILEENTRYFACTORY);
        } catch (SharkKBException e) {
            L.e(e.getMessage(), this);
        }
        entryFactory.removeEntry(identifier);
        try {
            addAndSerializeObjInContextPoint(PROFILEENTRYFACTORY, entryFactory);
        } catch (SharkKBException e) {
            L.e(e.getMessage(), this);
        }
    }

    public void createEntryName() throws SharkKBException {
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>("Surname"));
        entryList.add(new EntryImpl<String>("LastName"));
        entryList.add(new EntryImpl<String>("Title"));
        addProfileEntry("ProfileName", entryList);
    }

    public Entry<List<Entry<String>>> getEntryName() throws SharkKBException {
        return (Entry<List<Entry<String>>>) getProfileEntry("ProfileName");
    }

    @Override
    public void setName(ProfileName profileName) throws SharkKBException {
        addAndSerializeObjInContextPoint(PROFILENAME, profileName);
        increaseVersion();
    }

    @Override
    public ProfileName getName() throws SharkKBException {
        return (ProfileName) getAndDeserializeObjFromContextPoint(PROFILENAME);
    }

    @Override
    public void setPicture(byte[] content, String contentType, String identifier) throws SharkKBException {
        Information i = cp.addInformation(content);
        i.setContentType(contentType);
        i.setName(identifier);
        increaseVersion();
    }

    @Override
    public Information getPicture(String identifier) throws SharkKBException {
        return cp.getInformation(identifier).next();
    }

    @Override
    public void clearPicture(String identifier) throws SharkKBException {
        cp.removeInformation(getPicture(identifier));
    }


    @Override
    public void setBirthday(Date datum) throws SharkKBException {
        addAndSerializeObjInContextPoint(PROFILEBIRTHDAY, datum);
        increaseVersion();
    }

    @Override
    public Date getBirthday() throws SharkKBException {
        return (Date) getAndDeserializeObjFromContextPoint(PROFILEBIRTHDAY);
    }

    @Override
    public String[] getProfileAddresses() {
        return profileTarget.getAddresses();
    }

    @Override
    public void setTelephoneNumber(String number, String identifier) throws SharkKBException {
        addAndSerializeObjInContextPoint(identifier, number);
        increaseVersion();
    }

    @Override
    public String getTelephoneNumber(String identifier) throws SharkKBException {
            return (String) getAndDeserializeObjFromContextPoint(identifier);
    }

    @Override
    public void setKnownLanguages(ProfileKnownLanguages knownLanguages) throws SharkKBException {
        addAndSerializeObjInContextPoint(KNOWNLANGUAGES, knownLanguages);
        increaseVersion();
    }

    @Override
    public ProfileKnownLanguages getKnownLanguages() throws SharkKBException {
        return (ProfileKnownLanguages) getAndDeserializeObjFromContextPoint(KNOWNLANGUAGES);
    }
}
