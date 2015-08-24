package Profile;

import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.util.Date;
import java.util.List;

/**This profile is a generic container/storage for information about a thing mostly a person.
 * Profiles can be written over persons from other persons or the same person writes a profile from itself.
 * At the end if the profile is filled with information it should represent the person or the thing.
 * Generic container/storage means the type of the information which should be stored is not fixed.
 * It could be any type or shape and its possible to store it persistently.
 * Created by Thilo Stegemann on 16.04.2015.
 */
public interface Profile {
    /**This is a constant name to identify the shark profile version property.
     */
    String SHARK_PROFILE_VERSION_PROPERTY = "SHARK_PROFILE_VERSION_PROPERTY";

    /**Returned the context point of the profile.
     *
     * @return The context point of the profile.
     */
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

    /**Creates an empty entry and the identifier is the name of this entry.
     *
     * @param identifier Name of the entry.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void createProfileEntry(String identifier) throws SharkKBException;

    /**Creates an entry with content and a name.
     *
     * @param identifier Name of the entry.
     * @param entryContent Content of the entry.
     * @param <T> Generic type of the content.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    <T> void createProfileEntry(String identifier, T entryContent) throws SharkKBException;

    /**Creates an entry with a list of entries as content and a name.
     *
     * @param identifier Name of the entry.
     * @param entryList Content of the entry.
     * @param <T> Generic type of the content.
     * @throws SharkKBException
     */
    <T> void createProfileEntry(String identifier, List<Entry<T>> entryList) throws SharkKBException;

    /**Creates an empty sub entry in an existing entry under a specified entry from the root entry.
     *
     * @param superEntryName Name of the root entry.
     * @param saveNewSubEntryUnderThisEntryName Sub entry is stored under this entry.
     * @param subEntryName Name of the new sub entry.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void createSubEntry(String superEntryName, String saveNewSubEntryUnderThisEntryName, String subEntryName) throws SharkKBException;

    /**Creates an sub entry with generic content in an existing entry under a specified entry from the root entry.
     *
     * @param superEntryName Name of the root entry.
     * @param saveNewSubEntryUnderThisEntryName Sub entry is stored under this entry.
     * @param subEntryName Name of the new sub entry.
     * @param content Content of the new sub entry.
     * @param <T> Generic type of the content.l
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    <T> void createSubEntry(String superEntryName, String saveNewSubEntryUnderThisEntryName, String subEntryName, T content) throws SharkKBException;

    /**Returns a sub entry from an existing root entry.
     *
     * @param superEntryName Name of the root entry.
     * @param subEntryName Name of the sub entry.
     * @param <T> Generic type of the entry.
     * @return The entry with the name of the sub entry.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    <T> Entry<T> getSubEntry(String superEntryName, String subEntryName) throws SharkKBException;

    /**This function is similar to createSubEntry(), it adds an empty sub entry under a root entry.
     * The difference between these functions is addSubEntryInEntry can only add an entry under the root entry,
     * so if the root entry is dimension 1 and directly under the root entry is dimension 2 its only possible to
     * store entrys in dimension 2 but not in higher ones.
     * CreateSubEntry() can store entries in dimension 4 ... 5 and so on.
     *
     * @param superEntryName Name of teh root entry.
     * @param subEntryName Name of the new sub entry.
     * @param <T> Generic type of the root entry.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    <T> void addSubEntryInEntry(String superEntryName, String subEntryName) throws SharkKBException;


    /**This function is similar to createSubEntry(), it adds a sub entry with content under a root entry.
     * The difference between these functions is addSubEntryInEntry can only add an entry under the root entry,
     * so if the root entry is dimension 1 and directly under the root entry is dimension 2 its only possible to
     * store entrys in dimension 2 but not in higher ones.
     * CreateSubEntry() can store entries in dimension 4 ... 5 and so on.
     *
     * @param superEntryName Name of the root entry.
     * @param subEntryName Name of the new sub entry.
     * @param content Content of the new sub entry.
     * @param <T> Generic type of the content.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    <T> void addSubEntryInEntry(String superEntryName, String subEntryName, T content) throws SharkKBException;

    /**It changes the content of a sub entry.
     *
     * @param superEntryName Name of the root entry.
     * @param subEntryName Name of the sub entry.
     * @param content New content.
     * @param <T> Generic type of the content.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    <T> void alterSubEntryContent(String superEntryName, String subEntryName, T content) throws SharkKBException;

    /**Returns a profile entry.
     *
     * @param identifier Name of the profile entry.
     * @return The profile entry.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    Entry<?> getProfileEntry(String identifier) throws SharkKBException;

    /**Removes a profile entry.
     *
     * @param identifier Name of the entry which should be removed.
     */
    void removeProfileEntry(String identifier);

    /**Removes a sub entry of an root entry.
     *
     *
     * @param superEntryName Name of the root entry.
     * @param entryName Name of the sub entry which should be removed.
     * @param <T> Generic type of the root entry.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    <T> void removeSubEntry(String superEntryName, String entryName) throws SharkKBException;

    /**Removes an entry from a sub entry of an root entry.
     *
     * @param superEntryName Name of the root entry.
     * @param subEntryName Name of the sub entry.
     * @param entryName Name of the entry which should be deleted.
     * @param <T> Generic type of the root entry.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    <T> void removeEntryFromSubEntry(String superEntryName, String subEntryName, String entryName) throws SharkKBException;

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

}
