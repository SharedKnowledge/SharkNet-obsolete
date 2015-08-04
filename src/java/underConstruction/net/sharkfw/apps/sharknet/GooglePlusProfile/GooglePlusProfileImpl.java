package GooglePlusProfile;

import Profile.Entry;
import Profile.EntryImpl;
import Profile.Profile;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mr.T on 03.07.2015.
 */
public class GooglePlusProfileImpl implements GooglePlusProfile {

    private Profile p;



    GooglePlusProfileImpl(Profile p) throws SharkKBException {
        this.p = p;
        createNameEntry();
        createStory();
        createWork();
        p.createProfileEntry(EDUCATION);
        p.createProfileEntry(PLACES);
        createBasicInformation();
        createContactInformation();
        createLink();
    }

    private void createNameEntry() throws SharkKBException {
        p.createProfileEntry(NAME);
        p.addSubEntryInEntry(NAME, FIRTSTNAME, "");
        p.addSubEntryInEntry(NAME, LASTNAME, "");
        p.addSubEntryInEntry(NAME, NICKNAME, "");
    }

    private void createStory() throws SharkKBException {
        p.createProfileEntry(STORY);
        p.addSubEntryInEntry(STORY, TAGLINE, "");
        p.addSubEntryInEntry(STORY, INTRODUCTION, "");
        p.addSubEntryInEntry(STORY, BRAGGINGRIGHTS, "");
    }

    private void createWork() throws SharkKBException {
        p.createProfileEntry(WORK);
        p.addSubEntryInEntry(WORK, OCCUPATION, "");
        p.addSubEntryInEntry(WORK, SKILLS, "");
        p.addSubEntryInEntry(WORK, EMPLOYMENTS);
    }

    private void createBasicInformation() throws SharkKBException {
        p.createProfileEntry(BASICINFORMATION);
        p.addSubEntryInEntry(BASICINFORMATION, GENDER, "");
        p.addSubEntryInEntry(BASICINFORMATION, LOOKINGFOR, "");
        p.addSubEntryInEntry(BASICINFORMATION, BIRTHDAY, "");
        p.addSubEntryInEntry(BASICINFORMATION, RELATIONSHIP, "");
        p.addSubEntryInEntry(BASICINFORMATION, OTHERNAMES);
    }

    private void createContactInformation() throws SharkKBException {
        p.createProfileEntry(CONTACTINFORMATION);
        p.addSubEntryInEntry(CONTACTINFORMATION, HOME);
        p.addSubEntryInEntry(CONTACTINFORMATION, WORK);
    }

    private void createLink() throws SharkKBException {
        p.createProfileEntry(LINK);
        p.addSubEntryInEntry(LINK, OTHERPROFILES);
        p.addSubEntryInEntry(LINK, CONTRIBUTORTO);
        p.addSubEntryInEntry(LINK, LINKS);
    }

    //##########################NameSection##########################
    public void setFirstName(String firstName) throws SharkKBException {
        p.addSubEntryInEntry(NAME, FIRTSTNAME, firstName);
    }

    public String getFirstName() throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(NAME);
        return (String) entry.getEntryFromList(FIRTSTNAME).getContent();
    }

    public void setLastName(String lastName) throws SharkKBException {
        p.addSubEntryInEntry(NAME, LASTNAME, lastName);
    }

    public String getLastName() throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(NAME);
        return (String) entry.getEntryFromList(LASTNAME).getContent();
    }
    public void setNickname(String nickname) throws SharkKBException {
        p.addSubEntryInEntry(NAME, NICKNAME, nickname);
    }

    public String getNickname() throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(NAME);
        return (String) entry.getEntryFromList(NICKNAME).getContent();
    }

    //##########################StorySection##########################
    public void setTagline(String tagline) throws SharkKBException {
        p.addSubEntryInEntry(STORY, TAGLINE, tagline);
    }

    public String getTagline() throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(STORY);
        return (String) entry.getEntryFromList(TAGLINE).getContent();
    }

    public void setIntroduction(String introduction) throws SharkKBException {
        p.addSubEntryInEntry(STORY, INTRODUCTION, introduction);
    }

    public String getIntroduction() throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(STORY);
        return (String) entry.getEntryFromList(INTRODUCTION).getContent();
    }

    public void setBraggingRights(String braggingRights) throws SharkKBException {
        p.addSubEntryInEntry(STORY, BRAGGINGRIGHTS, braggingRights);
    }

    public String getBraggingRights() throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(STORY);
        return (String) entry.getEntryFromList(BRAGGINGRIGHTS).getContent();
    }

    //##########################WorkSection##########################
    public void setOccupation(String occupation) throws SharkKBException {
        p.addSubEntryInEntry(WORK, OCCUPATION, occupation);
    }

    public String getOccupation() throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(WORK);
        return (String) entry.getEntryFromList(OCCUPATION).getContent();
    }

    public void setSkills(String skills) throws SharkKBException {
        p.addSubEntryInEntry(WORK, SKILLS, skills);
    }

    public String getSkills() throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(WORK);
        return (String) entry.getEntryFromList(SKILLS).getContent();
    }

    public void addEmployment(String employmentName, String jobTitle, int start, int end, boolean current, String jobDescription) throws SharkKBException {
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>(EMPLOYERNAME, employmentName));
        entryList.add(new EntryImpl<String>(JOBTITLE, jobTitle));
        entryList.add(new EntryImpl<Integer>(START, start));
        entryList.add(new EntryImpl<Integer>(END, end));
        entryList.add(new EntryImpl<Boolean>(CURRENT, current));
        entryList.add(new EntryImpl<String>(JOBDESCRIPTION, jobDescription));

        int count = p.getSubEntry(WORK, EMPLOYMENTS).getEntryList().size();
        p.createSubEntry(WORK, EMPLOYMENTS, Integer.toString(count), entryList);
    }

    public void removeEmployment(String entryName) throws SharkKBException {
        p.removeSubEntry(WORK, entryName);
    }

    public String getEmploymentName(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (String) entryList.get(0).getContent();
    }

    public String getJobTitle(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (String) entryList.get(1).getContent();
    }

    public int getStartOfEmployment(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Integer) entryList.get(2).getContent();
    }

    public int getEndOfEmployment(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Integer) entryList.get(3).getContent();
    }

    public Boolean getIsEmploymentCurrent(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Boolean) entryList.get(4).getContent();
    }

    public String getJobDescription(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (String) entryList.get(5).getContent();
    }

    //##########################EducationSection##########################
    @Override
    public String getSchoolName(String educationNumber) throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(EDUCATION);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (String) entryList.get(0).getContent();
    }

    @Override
    public String getMajor(String educationNumber) throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(EDUCATION);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (String) entryList.get(1).getContent();
    }

    @Override
    public int getStartOfEducation(String educationNumber) throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(EDUCATION);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Integer) entryList.get(2).getContent();
    }

    @Override
    public int getEndOfEducation(String educationNumber) throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(EDUCATION);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Integer) entryList.get(3).getContent();
    }

    @Override
    public boolean getIsEducationCurrent(String educationNumber) throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(EDUCATION);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Boolean) entryList.get(4).getContent();
    }

    @Override
    public String getCourseDescription(String educationNumber) throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(EDUCATION);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (String) entryList.get(5).getContent();
    }

    @Override
    public void addEducation(String schoolName, String major, int start, int end, boolean current, String courseDescription) throws SharkKBException {
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>(SCHOOLNAME, schoolName));
        entryList.add(new EntryImpl<String>(MAJOR, major));
        entryList.add(new EntryImpl<Integer>(START, start));
        entryList.add(new EntryImpl<Integer>(END, end));
        entryList.add(new EntryImpl<Boolean>(CURRENT, current));
        entryList.add(new EntryImpl<String>(COURSEDESCRIPTION, courseDescription));

        int count = p.getProfileEntry(EDUCATION).getEntryList().size();
        p.createSubEntry(EDUCATION, EDUCATION, Integer.toString(count), entryList);
    }

    @Override
    public void removeEducation(String educationNumber) throws SharkKBException {

    }

    //##########################PlacesSection##########################
    @Override
    public String getCity(String placeNumber) throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(PLACES);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (String) entryList.get(0).getContent();
    }

    @Override
    public boolean getIsPlaceCurrent(String placeNumber) throws SharkKBException {
        Entry<?> entry = p.getProfileEntry(PLACES);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Boolean) entryList.get(1).getContent();
    }

    @Override
    public void addPlace(String city) throws SharkKBException {
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>(CITY, city));

        int count = p.getProfileEntry(PLACES).getEntryList().size();
        p.createSubEntry(PLACES, PLACES, Integer.toString(count), entryList);
    }

    @Override
    public void removePlace(String placeNumber) throws SharkKBException {

    }

    //##########################BasicInformationSection##########################
    @Override
    public String getGender() throws SharkKBException {
        return null;
    }

    @Override
    public String getLookingFor() throws SharkKBException {
        return null;
    }

    @Override
    public Date getBirthday() throws SharkKBException {
        return null;
    }

    @Override
    public String getRelationship() throws SharkKBException {
        return null;
    }

    @Override
    public String getOtherName(String nameNumber) throws SharkKBException {
        return null;
    }

    @Override
    public void addOtherName(String name) {

    }

    @Override
    public void removeOtherName(String nameNumber) {

    }

    //##########################ContactInformationSection##########################
    public void addHomeContact(String contactType, String contactInfo) throws SharkKBException {
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>(CONTACTTYPE, contactType));
        entryList.add(new EntryImpl<String>(CONTACTINFO, contactInfo));

        int count = p.getSubEntry(CONTACTINFORMATION, HOME).getEntryList().size();
        p.createSubEntry(CONTACTINFORMATION, HOME, Integer.toString(count), entryList);
    }

    public String getHomeContactType(int contactNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(CONTACTINFORMATION, HOME).getEntryList().get(contactNumber).getContent();
        return (String) entryList.get(0).getContent();
    }

    public String getHomeContactInfo(int contactNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(CONTACTINFORMATION, HOME).getEntryList().get(contactNumber).getContent();
        return (String) entryList.get(1).getContent();
    }

    public void removeHomeContact(String entryName) throws SharkKBException {
        p.removeEntryFromSubEntry(CONTACTINFORMATION, HOME, entryName);
    }



    public void addWorkContact(String contactType, String contactInfo) throws SharkKBException {
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>(CONTACTTYPE, contactType));
        entryList.add(new EntryImpl<String>(CONTACTINFO, contactInfo));

        int count = p.getSubEntry(CONTACTINFORMATION, WORK).getEntryList().size();
        p.createSubEntry(CONTACTINFORMATION, WORK, Integer.toString(count), entryList);
    }

    public String getWorkContactType(int contactNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(CONTACTINFORMATION, WORK).getEntryList().get(contactNumber).getContent();
        return (String) entryList.get(0).getContent();
    }

    public String getWorkContactInfo(int contactNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(CONTACTINFORMATION, WORK).getEntryList().get(contactNumber).getContent();
        return (String) entryList.get(1).getContent();
    }

    public void removeWorkContact(String entryName) throws SharkKBException {
        p.removeEntryFromSubEntry(CONTACTINFORMATION, WORK, entryName);
    }


    //##########################LinkSection##########################
    public void addOtherProfiles(String label, String url) throws SharkKBException {
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>(LABEL, label));
        entryList.add(new EntryImpl<String>(URL, url));

        int count = p.getSubEntry(LINK, OTHERPROFILES).getEntryList().size();
        p.createSubEntry(LINK, OTHERPROFILES, Integer.toString(count), entryList);
    }

    public String getOtherProfilesLabel(int otherProfilesNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(LINK, OTHERPROFILES).getEntryList().get(otherProfilesNumber).getContent();
        return (String) entryList.get(0).getContent();
    }

    public String getOtherProfilesUrl(int otherProfilesNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(LINK, OTHERPROFILES).getEntryList().get(otherProfilesNumber).getContent();
        return (String) entryList.get(1).getContent();
    }

    public void removeOtherProfiles(String entryName) throws SharkKBException {
        p.removeEntryFromSubEntry(LINK, OTHERPROFILES, entryName);
    }

    public void addContributorTo(String label, String url) throws SharkKBException {
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>(LABEL, label));
        entryList.add(new EntryImpl<String>(URL, url));

        int count = p.getSubEntry(LINK, CONTRIBUTORTO).getEntryList().size();
        p.createSubEntry(LINK, CONTRIBUTORTO, Integer.toString(count), entryList);
    }

    public String getContributorsLabel(int contributorsNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(LINK, CONTRIBUTORTO).getEntryList().get(contributorsNumber).getContent();
        return (String) entryList.get(0).getContent();
    }

    public String getContributorsUrl(int contributorsNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(LINK, CONTRIBUTORTO).getEntryList().get(contributorsNumber).getContent();
        return (String) entryList.get(1).getContent();
    }


    public void removeContributor(String entryName) throws SharkKBException {
        p.removeEntryFromSubEntry(LINK, CONTRIBUTORTO, entryName);
    }

    public void addLinks(String label, String url) throws SharkKBException {
        List<Entry<?>> entryList = new ArrayList<Entry<?>>();
        entryList.add(new EntryImpl<String>(LABEL, label));
        entryList.add(new EntryImpl<String>(URL, url));

        int count = p.getSubEntry(LINK, LINKS).getEntryList().size();
        p.createSubEntry(LINK, LINKS, Integer.toString(count), entryList);
    }

    public String getLinkLabel(int linkNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(LINK, LINKS).getEntryList().get(linkNumber).getContent();
        return (String) entryList.get(0).getContent();
    }

    public String getLinkUrl(int linkNumber) throws SharkKBException {
        List<Entry<?>> entryList = (List<Entry<?>>) p.getSubEntry(LINK, LINKS).getEntryList().get(linkNumber).getContent();
        return (String) entryList.get(1).getContent();
    }

    public void removeLinks(String entryName) throws SharkKBException {
        p.removeEntryFromSubEntry(LINK, LINKS, entryName);
    }



}
