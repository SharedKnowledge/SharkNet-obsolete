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
     * @param firstName First name of the user
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
     * @param lastName Last name of the user
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
     * @param nickname Nickname of the user
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
     * @param tagline Tagline of the user
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
     * @param introduction Introduction of the user
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
     * @param braggingRights Bragging rights of the user
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
     * @param occupation Occupation of the user
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
     * @param skills Skills of the user
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
     * @param employerName Name of the employer
     * @param jobTitle Title of the job
     * @param start When the job starts
     * @param end When the job ends
     * @param current Is this the current job
     * @param jobDescription Description of the job
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
     * @param schoolName Name of the school
     * @param major Major of field of study
     * @param start Start year of education
     * @param end End year of education
     * @param current Flag if its the user`s current education
     * @param courseDescription Description of the course
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
     * @param city City of the user
     * @param isCurrent Flag if its the city where the user is currently living
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

    //##########################BasicInformationSection##########################

    /**
     * Sets the user`s gender.
     *
     * @param gender Gender of the user
     * @throws SharkKBException if the entry name does not exist
     */
    void setGender(String gender) throws SharkKBException;

    /**
     * @return Gender of the user.
     * @throws SharkKBException if the entry name does not exist
     */
    String getGender() throws SharkKBException;

    /**
     * Set a statement if the user is looking for friends.
     *
     * @param isLooking Flag if the user is looking for friends
     * @throws SharkKBException if the entry name does not exist
     */
    void setLookingforFriends(Boolean isLooking) throws SharkKBException;

    /**
     *
     * @return Flag if the user is looking for friends
     * @throws SharkKBException if the entry name does not exist
     */
    Boolean getLookingForFriends() throws SharkKBException;

    /**
     * Set a statement(flag) if the user is looking for a date.
     *
     * @param isLooking Flag if the user is looking for a date.
     * @throws SharkKBException if the entry name does not exist
     */
    void setLookingforDating(Boolean isLooking) throws SharkKBException;

    /**
     *
     * @return Flag if the user is looking for a date.
     * @throws SharkKBException if the entry name does not exist
     */
    Boolean getLookingForDating() throws SharkKBException;

    /**
     * Set a statement(flag) if the user is looking for a relationship.
     *
     * @param isLooking Flag if the user is looking for a relationship.
     * @throws SharkKBException if the entry name does not exist
     */
    void setLookingForRelationship(Boolean isLooking) throws SharkKBException;

    /**
     *
     * @return Flag if the user is looking for a relationship
     * @throws SharkKBException if the entry name does not exist
     */
    Boolean getLookingForRelationship() throws SharkKBException;

    /**
     * Set a statement(flag) if the user is looking for networking.
     * @param isLooking Flag if the user is looking for networking
     * @throws SharkKBException if the entry name does not exist
     */
    void setLookingForNetworking(Boolean isLooking) throws SharkKBException;

    /**
     *
     * @return Flag if the user is looking for networking
     * @throws SharkKBException if the entry name does not exist
     */
    Boolean getLookingForNetworking() throws SharkKBException;

    /**
     * Sets the birthday of the user.
     * @param birthday Birthday of the user
     * @throws SharkKBException if the entry name does not exist
     */
    void setBirthday(String birthday) throws SharkKBException;

    /**
     *
     * @return Birthday of the user
     * @throws SharkKBException
     */
    String getBirthday() throws SharkKBException;

    /**
     * Sets the relationship of the user.
     *
     * @param relationship Relationship of the user
     * @throws SharkKBException if the entry name does not exist
     */
    void setRelationship(String relationship) throws SharkKBException;

    /**
     *
     * @return Relationship of the user
     * @throws SharkKBException if the entry name does not exist
     */
    String getRelationship() throws SharkKBException;

    /**
     * @param nameNumber depends on the order in which the other name entries were added, e.g. the first added other name has the number "1", the second added other name is number "2" and so on.
     * @return For example maiden name or name with alternate spelling.
     * @throws SharkKBException if the entry name does not exist
     */
    String getOtherName(String nameNumber) throws SharkKBException;

    /**
     * Adds an other name so a name with different/alternate spelling or e.g. the maidens name.
     *
     * @param name Other Name of the user
     * @throws SharkKBException if the entry name does not exist
     */
    void addOtherName(String name) throws SharkKBException;

    /**
     * Removes an other name from the profile.
     *
     * @param nameNumber depends on the order in which the other name entries were added, e.g. the first added other name has the number "1", the second added other name is number "2" and so on.
     * @throws SharkKBException if the entry name does not exist
     */
    void removeOtherName(String nameNumber) throws SharkKBException;

    //##########################ContactInformationSection##########################

    /**
     * This function adds an home contact entry to the profile. It includes the contact type e.g. mobile number, home address, FAX number and so on.
     * And it includes contact info like the content of the contact type.
     * Contact type = Mobile Number and contact info = 0172-44552213.
     *
     * @param contactType The kind of information stored
     * @param contactInfo Content of the type
     * @throws SharkKBException if the entry name does not exist
     */
    void addHomeContact(String contactType, String contactInfo) throws SharkKBException;

    /**
     *
     * @param contactNumber depends on the order in which the home contact entries were added, e.g. the first added home contact has the number "1", the second added home contact is number "2" and so on.
     * @return Home contact type
     * @throws SharkKBException if the entry name does not exist
     */
    String getHomeContactType(int contactNumber) throws SharkKBException;

    /**
     *
     * @param contactNumber depends on the order in which the home contact entries were added, e.g. the first added home contact has the number "1", the second added home contact is number "2" and so on.
     * @return Home contact info
     * @throws SharkKBException if the entry name does not exist
     */
    String getHomeContactInfo(int contactNumber) throws SharkKBException;

    /**
     * Removes a home contact from the profile.
     *
     * @param contactNumber depends on the order in which the home contact entries were added, e.g. the first added home contact has the number "1", the second added home contact is number "2" and so on.
     * @throws SharkKBException if the entry name does not exist
     */
    void removeHomeContact(String contactNumber) throws SharkKBException;

    /**
     * This function adds an work contact entry to the profile. It includes the contact type e.g. mobile number, home address, FAX number and so on.
     * And it includes contact info like the content of the contact type.
     * Contact type = Mobile Number and contact info = 0172-44552213.
     *
     * @param contactType The kind of information stored
     * @param contactInfo Content of the type
     * @throws SharkKBException if the entry name does not exist
     */
    void addWorkContact(String contactType, String contactInfo) throws SharkKBException;

    /**
     *
     * @param contactNumber depends on the order in which the home contact entries were added, e.g. the first added home contact has the number "1", the second added home contact is number "2" and so on.
     * @return Work contact type
     * @throws SharkKBException if the entry name does not exist
     */
    String getWorkContactType(int contactNumber) throws SharkKBException;

    /**
     *
     * @param contactNumber depends on the order in which the home contact entries were added, e.g. the first added home contact has the number "1", the second added home contact is number "2" and so on.
     * @return Work contact info
     * @throws SharkKBException if the entry name does not exist
     */
    String getWorkContactInfo(int contactNumber) throws SharkKBException;

    /**
     * Removes a work contact from the profile.
     *
     * @param contactNumber depends on the order in which the home contact entries were added, e.g. the first added home contact has the number "1", the second added home contact is number "2" and so on.
     * @throws SharkKBException if the entry name does not exist
     */
    void removeWorkContact(String contactNumber) throws SharkKBException;

    //##########################LinkSection##########################

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
