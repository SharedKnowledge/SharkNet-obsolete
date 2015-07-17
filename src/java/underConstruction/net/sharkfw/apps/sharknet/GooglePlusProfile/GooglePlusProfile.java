package GooglePlusProfile;

import net.sharkfw.knowledgeBase.SharkKBException;

/**
 * Created by Mr.T on 03.07.2015.
 */
public interface GooglePlusProfile {

    String NAME = "Name";
    String FIRTSTNAME = "FirstName";
    String LASTNAME = "LastName";
    String NICKNAME = "Nickname";

    String STORY = "Story";
    String TAGLINE = "Tagline";
    String INTRODUCTION = "Introduction";
    String BRAGGINGRIGHTS = "BraggingRights";

    String WORK = "Work";
    String OCCUPATION = "Occupation";
    String SKILLS = "Skills";
    String EMPLOYMENT = "Employment";
    String EMPLOYMENTS = "Employments";
    String EMPLOYERNAME = "EmployerName";
    String JOBTITLE = "JobTitle";
    String START = "Start";
    String END = "End";
    String CURRENT = "Current";
    String JOBDESCRIPTION = "JobDescription";

    String LINK = "Link";
    String OTHERPROFILES = "OtherProfiles";
    String LABEL = "Label";
    String URL = "Url";
    String CONTRIBUTORTO = "ContributorTo";
    String LINKS = "Links";


    void setFirstName(String firstName) throws SharkKBException;
    String getFirstName() throws SharkKBException;
    void setLastName(String lastName) throws SharkKBException;
    String getLastName() throws SharkKBException;
    void setNickname(String nickname) throws SharkKBException;
    String getNickname() throws SharkKBException;
    String getEmploymentName(String employmentNumber) throws SharkKBException;
    void setTagline(String tagline) throws SharkKBException;
    String getTagline() throws SharkKBException;
    void setIntroduction(String introduction) throws SharkKBException;
    String getIntroduction() throws SharkKBException;
    void setBraggingRights(String braggingRights) throws SharkKBException;
    String getBraggingRights() throws SharkKBException;
    String getJobTitle(String employmentNumber) throws SharkKBException;
    int getStartOfEmployment(String employmentNumber) throws SharkKBException;
    int getEndOfEmployment(String employmentNumber) throws SharkKBException;
    Boolean getIsEmploymentCurrent(String employmentNumber) throws SharkKBException;
    String getJobDescription(String employmentNumber) throws SharkKBException;
    void addEmployment(String employmentName, String jobTitle, int start, int end, boolean current, String jobDescription) throws SharkKBException;
    void removeEmployment(String entryName) throws SharkKBException;
    void addOtherProfiles(String label, String url) throws SharkKBException;
    void removeOtherProfiles(String entryName) throws SharkKBException;
}
