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


    //##########################NameSection##########################

    /**
     * Sets the first name of the user.
     *
     * @param firstName
     * @throws SharkKBException if the entry name does not exist
     */
    void setFirstName(String firstName) throws SharkKBException;

    /**
     * @return the first name of the user
     * @throws SharkKBException
     */
    String getFirstName() throws SharkKBException;

    /**
     * Sets the last name of the user.
     *
     * @param lastName
     * @throws SharkKBException if the entry name does not exist
     */
    void setLastName(String lastName) throws SharkKBException;

    /**
     * @return the last name of the user
     * @throws SharkKBException if the entry name does not exist
     */
    String getLastName() throws SharkKBException;

    /**
     * Sets the nickname of the user.
     *
     * @param nickname
     * @throws SharkKBException if the entry name does not exist
     */
    void setNickname(String nickname) throws SharkKBException;

    /**
     * @return the nickname of the user
     * @throws SharkKBException if the entry name does not exist
     */
    String getNickname() throws SharkKBException;


    //##########################StorySection##########################

    /**
     * Sets the tagline of the user.
     *
     * @param tagline
     * @throws SharkKBException if the entry name does not exist
     */
    void setTagline(String tagline) throws SharkKBException;

    /**
     * @return the user's tagline
     * @throws SharkKBException if the entry name does not exist
     */
    String getTagline() throws SharkKBException;

    /**
     * Sets the introduction of the user.
     *
     * @param introduction
     * @throws SharkKBException if the entry name does not exist
     */
    void setIntroduction(String introduction) throws SharkKBException;

    /**
     * @return the introduction of the user.
     * @throws SharkKBException if the entry name does not exist
     */
    String getIntroduction() throws SharkKBException;

    /**
     * Sets the user's bragging rights
     *
     * @param braggingRights
     * @throws SharkKBException if the entry name does not exist
     */
    void setBraggingRights(String braggingRights) throws SharkKBException;

    /**
     * @return the user's bragging rights
     * @throws SharkKBException
     */
    String getBraggingRights() throws SharkKBException;


    //##########################WorkSection##########################

    /**
     * Sets the user's occupation
     *
     * @param occupation
     * @throws SharkKBException if the entry name does not exist
     */
    void setOccupation(String occupation) throws SharkKBException;

    /**
     * @return the user's occupation
     * @throws SharkKBException if the entry name does not exist
     */
    String getOccupation() throws SharkKBException;

    /**
     * Sets the skills of the user.
     *
     * @param skills
     * @throws SharkKBException if the entry name does not exist
     */
    void setSkills(String skills) throws SharkKBException;

    /**
     * @return the skills of the user
     * @throws SharkKBException if the entry name does not exist
     */
    String getSkills() throws SharkKBException;

    /**
     * This function adds an employment entry to the user's profile. It includes the employer name, job title, start
     * and end year of the employment, a job description and it states if it is the current employment of the user.
     *
     * @param employerName
     * @param jobTitle
     * @param start
     * @param end
     * @param current
     * @param jobDescription
     * @throws SharkKBException if the entry name does not exist
     */
    void addEmployment(String employerName, String jobTitle, int start, int end, boolean current, String jobDescription) throws SharkKBException;

    /**
     * Removes the employment entry from the profile with the given employment number.
     *
     * @param employmentNumber depends on the order in which the employment entries were added, e.g. the first added employment has the number "1", the second added employment is number "2" and so on.
     * @throws SharkKBException if the entry name does not exist
     */
    void removeEmployment(String employmentNumber) throws SharkKBException;

    /**
     * @param employmentNumber depends on the order in which the employment entries were added, e.g. the first added employment has the number "1", the second added employment is number "2" and so on.
     * @return the employer name of the employment entry with the given employment number
     * @throws SharkKBException if the entry name does not exist
     */
    String getEmployerName(String employmentNumber) throws SharkKBException;

    /**
     * @param employmentNumber depends on the order in which the employment entries were added, e.g. the first added employment has the number "1", the second added employment is number "2" and so on.
     * @return the job title of the employment entry with the given employment number
     * @throws SharkKBException if the entry name does not exist
     */
    String getJobTitle(String employmentNumber) throws SharkKBException;

    /**
     * @param employmentNumber depends on the order in which the employment entries were added, e.g. the first added employment has the number "1", the second added employment is number "2" and so on.
     * @return the year the employment of the employment entry with the given employment number started
     * @throws SharkKBException if the entry name does not exist
     */
    int getStartOfEmployment(String employmentNumber) throws SharkKBException;

    /**
     * @param employmentNumber depends on the order in which the employment entries were added, e.g. the first added employment has the number "1", the second added employment is number "2" and so on.
     * @return the year the employment of the employment entry with the given employment number ended
     * @throws SharkKBException if the entry name does not exist
     */
    int getEndOfEmployment(String employmentNumber) throws SharkKBException;

    /**
     * @param employmentNumber depends on the order in which the employment entries were added, e.g. the first added employment has the number "1", the second added employment is number "2" and so on.
     * @return true if the employment is the user's current employment, false otherwise
     * @throws SharkKBException if the entry name does not exist
     */
    Boolean getIsEmploymentCurrent(String employmentNumber) throws SharkKBException;

    /**
     * @param employmentNumber depends on the order in which the employment entries were added, e.g. the first added employment has the number "1", the second added employment is number "2" and so on.
     * @return a job description of the employment entry with the given employment number
     * @throws SharkKBException if the entry name does not exist
     */
    String getJobDescription(String employmentNumber) throws SharkKBException;


    //##########################EducationSection##########################

    /**
     * @param educationNumber depends on the order in which the education entries were added, e.g. the first added education has the number "1", the second added education is number "2" and so on.
     * @return the school name of the education entry with the given education number
     * @throws SharkKBException if the entry name does not exist
     */
    String getSchoolName(String educationNumber) throws SharkKBException;

    /**
     * @param educationNumber depends on the order in which the education entries were added, e.g. the first added education has the number "1", the second added education is number "2" and so on.
     * @return the major of field of study of the education entry with the given education number
     * @throws SharkKBException if the entry name does not exist
     */
    String getMajor(String educationNumber) throws SharkKBException;

    /**
     * @param educationNumber depends on the order in which the education entries were added, e.g. the first added education has the number "1", the second added education is number "2" and so on.
     * @return the start year of the education entry with the given education number
     * @throws SharkKBException if the entry name does not exist
     */
    int getStartOfEducation(String educationNumber) throws SharkKBException;

    /**
     * @param educationNumber depends on the order in which the education entries were added, e.g. the first added education has the number "1", the second added education is number "2" and so on.
     * @return the end year of the education entry with the given education number
     * @throws SharkKBException if the entry name does not exist
     */
    int getEndOfEducation(String educationNumber) throws SharkKBException;

    /**
     * @param educationNumber depends on the order in which the education entries were added, e.g. the first added education has the number "1", the second added education is number "2" and so on.
     * @return true if the education is the user's current education, false otherwise
     * @throws SharkKBException if the entry name does not exist
     */
    boolean getIsEducationCurrent(String educationNumber) throws SharkKBException;

    /**
     * @param educationNumber depends on the order in which the education entries were added, e.g. the first added education has the number "1", the second added education is number "2" and so on.
     * @return a course description of the education entry with the given education number
     * @throws SharkKBException if the entry name does not exist
     */
    String getCourseDescription(String educationNumber) throws SharkKBException;

    /**
     * This function adds an education entry to the profile. It includes the name of the school, the major of field of study,
     * the start and end year of the education, a course description and it states if it is the user's current education.
     *
     * @param schoolName
     * @param major
     * @param start
     * @param end
     * @param current
     * @param courseDescription
     * @throws SharkKBException if the entry name does not exist
     */
    void addEducation(String schoolName, String major, int start, int end, boolean current, String courseDescription) throws SharkKBException;

    /**
     * Removes the education entry with the given education number
     *
     * @param educationNumber depends on the order in which the education entries were added, e.g. the first added education has the number "1", the second added education is number "2" and so on.
     * @throws SharkKBException if the entry name does not exist
     */
    void removeEducation(String educationNumber) throws SharkKBException;


    //##########################PlacesSection##########################

    /**
     * @param placeNumber depends on the order in which the place entries were added, e.g. the first added place has the number "1", the second added place is number "2" and so on.
     * @return the city of the place entry with the given place number
     * @throws SharkKBException if the entry name does not exist
     */
    String getCity(String placeNumber) throws SharkKBException;

    /**
     * @param placeNumber depends on the order in which the place entries were added, e.g. the first added place has the number "1", the second added place is number "2" and so on.
     * @return true if the place is where the user is currently living, false otherwise
     * @throws SharkKBException if the entry name does not exist
     */
    boolean getIsPlaceCurrent(String placeNumber) throws SharkKBException;

    /**
     * This function adds a place entry to the user's profile. It includes a city and states if this is the place where
     * the user is currently living.
     *
     * @param city
     * @param isCurrent
     * @throws SharkKBException if the entry name does not exist
     */
    void addPlace(String city, boolean isCurrent) throws SharkKBException;

    /**
     * Removes the place entry with the given place number.
     *
     * @param placeNumber depends on the order in which the place entries were added, e.g. the first added place has the number "1", the second added place is number "2" and so on.
     * @throws SharkKBException if the entry name does not exist
     */
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
