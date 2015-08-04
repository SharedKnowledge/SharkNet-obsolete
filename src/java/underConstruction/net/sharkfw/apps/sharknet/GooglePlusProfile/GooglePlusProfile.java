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

    String EDUCATION = "Education";
    String SCHOOLNAME = "SchoolName";
    String MAJOR = "MajorOrFieldOfStudy";
    String COURSEDESCRIPTION = "DescriptionOfCourses";

    String PLACES = "Places";
    String CITY = "City";
    String ISCURRENT = "IsCurrent";

    String BASICINFORMATION = "BasicInformation";
    String GENDER = "Gender";
    String LOOKINGFORFRIENDS = "LookingForFriends";
    String LOOKINGFORDATING = "LookingForFriends";
    String LOOKINGFORRELATIONSHIP = "LookingForRelationship";
    String LOOKINGFORNETWORKING = "LookingForNetworking";
    String BIRTHDAY = "Birthday";
    String RELATIONSHIP = "Relationship";
    String OTHERNAMES = "OtherNames";

    String CONTACTINFORMATION = "ContactInformation";
    String HOME = "Home";
    String CONTACTTYPE = "ContactType";
    String CONTACTINFO = "ContactInfo";

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

    void setTagline(String tagline) throws SharkKBException;
    String getTagline() throws SharkKBException;
    void setIntroduction(String introduction) throws SharkKBException;
    String getIntroduction() throws SharkKBException;
    void setBraggingRights(String braggingRights) throws SharkKBException;
    String getBraggingRights() throws SharkKBException;

    void setOccupation(String occupation) throws SharkKBException;
    String getOccupation() throws SharkKBException;
    void setSkills(String skills) throws SharkKBException;
    String getSkills() throws SharkKBException;
    void addEmployment(String employmentName, String jobTitle, int start, int end, boolean current, String jobDescription) throws SharkKBException;
    void removeEmployment(String employmentNumber) throws SharkKBException;
    String getEmploymentName(String employmentNumber) throws SharkKBException;
    String getJobTitle(String employmentNumber) throws SharkKBException;
    int getStartOfEmployment(String employmentNumber) throws SharkKBException;
    int getEndOfEmployment(String employmentNumber) throws SharkKBException;
    Boolean getIsEmploymentCurrent(String employmentNumber) throws SharkKBException;
    String getJobDescription(String employmentNumber) throws SharkKBException;

    String getSchoolName(String educationNumber) throws SharkKBException;
    String getMajor(String educationNumber) throws SharkKBException;
    int getStartOfEducation(String educationNumber) throws SharkKBException;
    int getEndOfEducation(String educationNumber)throws SharkKBException;
    boolean getIsEducationCurrent(String educationNumber) throws SharkKBException;
    String getCourseDescription(String educationNumber) throws SharkKBException;
    void addEducation(String schoolName, String major, int start, int end, boolean current, String courseDescription) throws SharkKBException;
    void removeEducation(String educationNumber) throws SharkKBException;

    String getCity(String placeNumber) throws SharkKBException;
    boolean getIsPlaceCurrent(String placeNumber) throws SharkKBException;
    void addPlace(String city, boolean isCurrent) throws SharkKBException;
    void removePlace(String placeNumber) throws SharkKBException;

    void setGender(String gender) throws SharkKBException;
    String getGender() throws SharkKBException;
    void setLookingforFriends(Boolean isLooking) throws SharkKBException;
    Boolean getLookingForFriends() throws SharkKBException;
    void setLookingforDating(Boolean isLooking) throws SharkKBException;
    Boolean getLookingForDating() throws SharkKBException;
    void setLookingforRelationship(Boolean isLooking) throws SharkKBException;
    Boolean getLookingForRelationship() throws SharkKBException;
    void setLookingforNetworking(Boolean isLooking) throws SharkKBException;
    Boolean getLookingForNetworking() throws SharkKBException;
    void setBirthday(String birthday) throws SharkKBException;
    String getBirthday() throws SharkKBException;
    void setRelationship(String relationship) throws SharkKBException;
    String getRelationship() throws SharkKBException;
    String getOtherName(String nameNumber) throws SharkKBException;
    void addOtherName(String name) throws SharkKBException;
    void removeOtherName(String nameNumber) throws SharkKBException;

    void addHomeContact(String contactType, String contactInfo) throws SharkKBException;
    String getHomeContactType(int contactNumber) throws SharkKBException;
    String getHomeContactInfo(int contactNumber) throws SharkKBException;
    void removeHomeContact(String contactNumber) throws SharkKBException;
    void addWorkContact(String contactType, String contactInfo) throws SharkKBException;
    String getWorkContactType(int contactNumber) throws SharkKBException;
    String getWorkContactInfo(int contactNumber) throws SharkKBException;
    void removeWorkContact(String contactNumber) throws SharkKBException;

    void addOtherProfiles(String label, String url) throws SharkKBException;
    String getOtherProfilesLabel(int otherProfilesNumber) throws SharkKBException;
    String getOtherProfilesUrl(int otherProfilesNumber) throws SharkKBException;
    void removeOtherProfiles(String otherProfilesNumber) throws SharkKBException;
    void addContributorTo(String label, String url) throws SharkKBException;
    String getContributorsLabel(int contributorsNumber) throws SharkKBException;
    String getContributorsUrl(int contributorsNumber) throws SharkKBException;
    void removeContributor(String contributorsNumber) throws SharkKBException;
    void addLinks(String label, String url) throws SharkKBException;
    String getLinkLabel(int linkNumber) throws SharkKBException;
    String getLinkUrl(int linkNumber) throws SharkKBException;
    void removeLinks(String linkNumber) throws SharkKBException;
}
