package GooglePlusProfile;

import Profile.Entry;
import Profile.EntryImpl;
import Profile.Profile;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.T on 03.07.2015.
 */
public class GooglePlusProfileImpl implements GooglePlusProfile {

    private Profile p;



    GooglePlusProfileImpl(Profile p) throws SharkKBException {
        this.p = p;
        createNameEntry();
        createHistory();
        createWork();
    }

    private void createNameEntry() throws SharkKBException {
        p.createProfileEntry(NAME);
        p.addSubEntryInEntry(NAME, FIRTSTNAME, "");
        p.addSubEntryInEntry(NAME, LASTNAME, "");
        p.addSubEntryInEntry(NAME, NICKNAME, "");
    }

    private void createHistory() throws SharkKBException {
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

    public int getStart(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Integer) entryList.get(2).getContent();
    }

    public int getEnd(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Integer) entryList.get(3).getContent();
    }

    public Boolean getCurrent(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (Boolean) entryList.get(4).getContent();
    }

    public String getJobDescription(String employmentNumber) throws SharkKBException {
        Entry<?> entry = p.getSubEntry(WORK, employmentNumber);
        List<Entry<?>> entryList = (List<Entry<?>>) entry.getContent();
        return (String) entryList.get(5).getContent();
    }




}
