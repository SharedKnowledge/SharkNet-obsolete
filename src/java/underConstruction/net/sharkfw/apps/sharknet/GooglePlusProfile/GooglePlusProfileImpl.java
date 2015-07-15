package GooglePlusProfile;

import Profile.Profile;
import Profile.Entry;
import net.sharkfw.knowledgeBase.SharkKBException;

/**
 * Created by Mr.T on 03.07.2015.
 */
public class GooglePlusProfileImpl implements GooglePlusProfile {

    private Profile p;



    GooglePlusProfileImpl(Profile p) throws SharkKBException {
        this.p = p;
        createNameEntry();
        createHistory();
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

    public String getLastName() throws SharkKBException{
        Entry<?> entry = p.getProfileEntry(NAME);
        return (String) entry.getEntryFromList(LASTNAME).getContent();
    }
    public void setNickname(String nickname) throws SharkKBException {
        p.addSubEntryInEntry(NAME, NICKNAME, nickname);
    }

    public String getNickname() throws SharkKBException{
        Entry<?> entry = p.getProfileEntry(NAME);
        return (String) entry.getEntryFromList(NICKNAME).getContent();
    }

    public void setTagline(String tagline) throws SharkKBException {
        p.addSubEntryInEntry(STORY, TAGLINE, tagline);
    }

    public String getTagline() throws SharkKBException{
        Entry<?> entry = p.getProfileEntry(STORY);
        return (String) entry.getEntryFromList(TAGLINE).getContent();
    }

    public void setIntroduction(String introduction) throws SharkKBException {
        p.addSubEntryInEntry(STORY, INTRODUCTION, introduction);
    }

    public String getIntroduction() throws SharkKBException{
        Entry<?> entry = p.getProfileEntry(STORY);
        return (String) entry.getEntryFromList(INTRODUCTION).getContent();
    }

    public void setBraggingRights(String braggingRights) throws SharkKBException {
        p.addSubEntryInEntry(STORY, BRAGGINGRIGHTS, braggingRights);
    }

    public String getBraggingRights() throws SharkKBException{
        Entry<?> entry = p.getProfileEntry(STORY);
        return (String) entry.getEntryFromList(BRAGGINGRIGHTS).getContent();
    }




}
