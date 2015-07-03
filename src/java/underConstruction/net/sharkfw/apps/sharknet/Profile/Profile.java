package Profile;

import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.util.Date;

/**
 * Created by Mr.T on 16.04.2015.
 */
public interface Profile {
    String SHARK_PROFILE_VERSION_PROPERTY = "SHARK_PROFILE_VERSION_PROPERTY";
    ContextPoint getContextPoint();
    /**Returns the current Version of the Profile.
     * Every time the Profile is altered the version will increase by 1.
     * If the Profile is created the version starts by 0.
     *
     * @return The current Version number.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    String getProfileVersion() throws SharkKBException;
    /**Returns the owner of the profile.
     *
     * @return The owner of the profile.
     */
    PeerSemanticTag getProfileTarget();

    /**Returns the creator of the profile.
     *
     * @return The creator of the profile.
     */
    PeerSemanticTag getProfileCreator();

    void addProfileEntry(ProfileEntry profileEntry, String identifier);
    ProfileEntry getProfileEntry(String identifier);
    void clearProfileEntry(String identifier);
    /**Sets a profile name.
     * A profile name is represented as an profileName object.
     * A surname, a lastname and a title can be stored in such an profileName object.
     *
     * @param profileName This is the profile name object.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void setName(ProfileName profileName) throws SharkKBException;//Parameter vielleicht mit SemantixTagSet? Vor und Nachname

    /**Returns an profile name.
     * A profile name is represented as an profileName object.
     * A surname, a lastname and a title can be stored in such an profileName object.
     *
     * @return The profile name object.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    ProfileName getName() throws SharkKBException;

    /**Sets a profile picture.
     * A profile picture contains of three elements.
     * The first element is the content(the picture itself) as byte array.
     * The second element is the content type.
     * The third element is the identifier which specifies the picture with a name,
     * so several pictures could be stored and found by the identifier.
     *
     * @param content This is the picture itself as byte array.
     * @param contentType This is the type of the content.
     * @param identifier The name of the picture, it specifies the picture so it can be found later.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void setPicture(byte[] content, String contentType, String identifier) throws SharkKBException;

    /**Returns an information about a profile picture.
     * The returned information must be specified with an identifying string.
     * Information contains content.
     * Here the content is the profile picture and the content type of this picture.
     *
     * @param identifier This string identifies the picture which should be returned.
     * @return The profile picture stored in an Information object.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    Information getPicture(String identifier) throws SharkKBException;

    /**Clears a profile picture.
     * After you use that function a set profile picture from this profile is deleted.
     * You have to specify this profile picture with an identifier.
     * The identifier is like a little description or the name of the picture.
     *
     * @param identifier This string identifies the Picture which should be deleted.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void clearPicture(String identifier) throws SharkKBException;

    /**Sets a birthday "Date" in this profile.
     * The Birthday is represented as a Date.
     *
     * @param datum The Birthday Date.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void setBirthday(Date datum) throws SharkKBException;

    /**Returns the birthday "Date" of this profile.
     *
     * @return The birthday Date.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    Date getBirthday() throws SharkKBException;

    //das setProfileAdress ist überflüssig, aber das get ist gut in dem Fall

    /**Returns the addresses of the ProfileTarget.
     *
     * @return The addresses.
     */
    String[] getProfileAddresses();

    /**Sets a telephone number.
     * The telephone number is stored as string and is also identifies with a string.
     * The telephone number could be refund with the identifying string.
     *
     * @param number The telephone number.
     * @param identifier The identifying string.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void setTelephoneNumber(String number, String identifier) throws SharkKBException;

    /**Returns a telephone number.
     * This number must be specified with an identifying string so the function
     * knows which number should be returned.
     *
     * @param identifier The identifying string.
     * @return The telephone number.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    String getTelephoneNumber(String identifier) throws SharkKBException;

    //Standart für z.B. dt.dt oder eng.eng oder dt.sz suchen und überlegen, in welchem Format ich die Sprachen abspeichern möchte
    //ISO 639-1 Sprachcodes z.B. Germany = Deutschland = de, French = Französisch = fr

    /**Sets the languages which the profileOwner knows.
     * These languages only can be stored in a special form.
     * The ISO defined a norm 639-1 about language codes.
     * Every code is unique for a country.
     * The country/language codes are well explained in the class ProfileKnownLanguages.
     *
     * @param knownLanguages The languages the ProfileOwner knows.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void setKnownLanguages(ProfileKnownLanguages knownLanguages) throws SharkKBException;

    /**Returns the languages the ProfileOwner knows.
     *
     * @return The languages the Profile Owner knows.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    ProfileKnownLanguages getKnownLanguages() throws SharkKBException;
}
