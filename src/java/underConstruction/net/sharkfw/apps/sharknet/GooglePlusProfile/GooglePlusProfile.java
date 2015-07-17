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

    String getEmploymentName(String employmentNumber) throws SharkKBException;
    String getJobTitle(String employerName) throws SharkKBException;
    int getStart(String employmentNumber) throws SharkKBException;
    int getEnd(String employmentNumber) throws SharkKBException;
    Boolean getCurrent(String employmentNumber) throws SharkKBException;
    String getJobDescription(String employmentNumber) throws SharkKBException;
    void addEmployment(String employmentName, String jobTitle, int start, int end, boolean current, String jobDescription) throws SharkKBException;
}
