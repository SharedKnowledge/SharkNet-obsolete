package Profile;

import net.sharkfw.knowledgeBase.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProfileImpl implements Profile, Serializable {
    public static final String PROFILENAME = "ProfileName";
    public static final String PROFILEBIRTHDAY = "ProfileBirthday";
    private ContextPoint cp;
    private SharkKB kb;
    private PeerSemanticTag profileTarget = null;
    private PeerSemanticTag profileCreator = null;
    public ProfileImpl(SharkKB kb, PeerSemanticTag creator, PeerSemanticTag target) throws SharkKBException {
        this.kb = kb;
        this.profileTarget = target;
        this.profileCreator = creator;
        SemanticTag pr = ProfileFactoryImpl.getProfileSemanticTag();
        pr.setProperty(Profile.SHARK_PROFILE_VERSION_PROPERTY, "0");
        ContextCoordinates cc = this.kb.createContextCoordinates(pr, creator, target, null, null, null, SharkCS.DIRECTION_INOUT);
        cp = this.kb.createContextPoint(cc);
    }


    public ProfileImpl(SharkKB kb, ContextPoint cp) {
        this.kb = kb;
        this.cp = cp;
        profileTarget = cp.getContextCoordinates().getPeer();
        profileCreator = cp.getContextCoordinates().getOriginator();
    }


    private void increaseVersion() throws SharkKBException {
        String version = cp.getContextCoordinates().getTopic().getProperty(Profile.SHARK_PROFILE_VERSION_PROPERTY);
        int versionCount = Integer.parseInt(version);
        versionCount += 1;
        cp.getContextCoordinates().getTopic().setProperty(Profile.SHARK_PROFILE_VERSION_PROPERTY, Integer.toString(versionCount));
    }

    private void addAndSerializeObjInContextPoint(String objName, Object obj) throws SharkKBException {
        if (cp.getInformation(objName).hasNext()) {
            cp.removeInformation(cp.getInformation(objName).next());
        }
        Information i = cp.addInformation();
        try {
            i.setContent(Serializer.serialize(obj));
        } catch (IOException e) {
            throw new SharkKBException(e.getMessage());
        }
        i.setName(objName);
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
    public void createProfileEntry(String identifier) throws SharkKBException {
        Entry<?> entry = new EntryImpl<Object>(identifier);
        addAndSerializeObjInContextPoint(identifier, entry);
    }

    @Override
    public <T> void createProfileEntry(String identifier, T entryContent) throws SharkKBException {
        Entry<T> entry = new EntryImpl<T>(identifier, entryContent);
        addAndSerializeObjInContextPoint(identifier, entry);
    }

    @Override
    public <T> void createProfileEntry(String identifier, List<Entry<T>> entryList) throws SharkKBException {
        Entry<T> entry = new EntryImpl<T>(identifier, entryList);
        addAndSerializeObjInContextPoint(identifier, entry);
    }

    @Override
    public void createSubEntry(String superEntryName, String saveNewSubEntryUnderThisEntryName, String subEntryName) throws SharkKBException {
        Entry<?> entry = (Entry<?>) getAndDeserializeObjFromContextPoint(superEntryName);
        entry.addSubEntry(entry, saveNewSubEntryUnderThisEntryName, subEntryName);
        addAndSerializeObjInContextPoint(superEntryName, entry);
    }

    @Override
    public <T> void createSubEntry(String superEntryName, String saveNewSubEntryUnderThisEntryName, String subEntryName, T content) throws SharkKBException {
        Entry<T> entry = (Entry<T>) getAndDeserializeObjFromContextPoint(superEntryName);
        entry.addSubEntry(entry, saveNewSubEntryUnderThisEntryName, subEntryName, content);
        addAndSerializeObjInContextPoint(superEntryName, entry);
    }

    @Override
    public <T> Entry<T> getSubEntry(String superEntryName, String subEntryName) throws SharkKBException {
        Entry<T> rootEntry = (Entry<T>) getAndDeserializeObjFromContextPoint(superEntryName);
        return rootEntry.getSubEntry(rootEntry, subEntryName);
    }

    @Override
    public <T> void addSubEntryInEntry(String superEntryName, String subEntryName) throws SharkKBException {
        Entry<T> entry = (Entry<T>) getAndDeserializeObjFromContextPoint(superEntryName);
        entry.addEntryInEntryList(subEntryName);
        addAndSerializeObjInContextPoint(superEntryName, entry);
    }


    @Override
    public <T> void addSubEntryInEntry(String superEntryName, String subEntryName, T content) throws SharkKBException {
        Entry<T> entry = (Entry<T>) getAndDeserializeObjFromContextPoint(superEntryName);
        entry.addEntryInEntryList(subEntryName, content);
        addAndSerializeObjInContextPoint(superEntryName, entry);
    }

    @Override
    public <T> void alterSubEntryContent(String superEntryName, String subEntryName, T content) throws SharkKBException {
        Entry<T> entry = (Entry<T>) getAndDeserializeObjFromContextPoint(superEntryName);
        entry.alterContentFromEntry(entry, subEntryName, content);
        addAndSerializeObjInContextPoint(superEntryName, entry);
    }

    @Override
    public Entry<?> getProfileEntry(String identifier) throws SharkKBException {
        return (Entry<?>) getAndDeserializeObjFromContextPoint(identifier);
    }

    @Override
    public void removeProfileEntry(String identifier) {
        if (cp.getInformation(identifier).hasNext()) {
            cp.removeInformation(cp.getInformation(identifier).next());
        }
    }

    @Override
    public <T> void removeSubEntry(String superEntryName, String entryName) throws SharkKBException {
        Entry<T> entry = (Entry<T>) getAndDeserializeObjFromContextPoint(superEntryName);
        entry.removeEntry(entry, entryName);
        addAndSerializeObjInContextPoint(superEntryName, entry);

    }

    @Override
    public <T> void removeEntryFromSubEntry(String superEntryName, String subEntryName, String entryName) throws SharkKBException {
        Entry<T> entry = (Entry<T>) getAndDeserializeObjFromContextPoint(superEntryName);
        entry.removeEntryFromSubEntry(entry, subEntryName, entryName);
        addAndSerializeObjInContextPoint(superEntryName, entry);
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
}
