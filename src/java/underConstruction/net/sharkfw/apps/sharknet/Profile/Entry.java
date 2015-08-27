package Profile;

import java.util.List;

/**An entry is an approach to be a generic container for information.
 * Different kinds of information should be stored in entries.
 * So the structure of entries is also customisable.
 * Entry structure is a little bit like a file system or a tree
 * and if you navigate through the paths at the end there is an information.
 *
 * Example:
 * Imagine an entry like a job employment.
 * A job employment can contain things like:
 * -employer name(String)
 * -job title(String)
 * -start and end of the job(Date or Int)
 * -information if the job is the current job(Boolean)
 * -job description(String)
 * So now you can create an entry named employment
 * and add the different paths with the information as content.
 *
 * Created by Thilo Stegemann on 03.07.2015.
 */
public interface Entry<T> {
    /**Sets the content of an entry.
     *
     * @param content The generic content.
     */
    void setContent(T content);

    /**Returns the generic content of an entry.
     *
     * @return Generic content.
     */
    T getContent();

    /**An entry is identified by a name so it can be accessed later.
     * This function returns the name of the entry.
     *
     * @return Name of the entry.
     */
    String getEntryName();

    /**Adds an empty path element(path element = entry node) under an existing entry path.
     *
     * @param rootEntry Main entry like the root node of a tree where all paths start.
     * @param saveNewSubEntryUnderThisEntryName Last node of an entry path where the new entry node should be added.
     * @param subEntryName Name of the new entry node.
     */
    void addSubEntry(Entry<?> rootEntry, String saveNewSubEntryUnderThisEntryName, String subEntryName);

    /**Adds an path element(path element = entry node) with generic content(information) under an existing entry path.
     *
     * @param rootEntry Main entry like the root node of a tree where all paths start.
     * @param saveNewSubEntryUnderThisEntryName Last node of an entry path where the new entry node should be added.
     * @param subEntryName Name of the new entry node.
     * @param content The generic information at the end of the path.
     */
    void addSubEntry(Entry<T> rootEntry, String saveNewSubEntryUnderThisEntryName, String subEntryName, T content);

    /**This function searches a sub entry name in an root entry and returns it.
     *
     * @param rootEntry The main node or start point of all entry paths.
     * @param subEntryName Name of the entry which should be searched.
     * @return A found entry or null.
     */
    Entry<T> getSubEntry(Entry<T> rootEntry, String subEntryName);

    /**Every entry can have 2 elements at the end of an entry path.
     * A content or a list of more entries.
     * This function adds an empty entry in the entryList of an entry.
     * But it can only iterate one step into a path.
     * If you want to go deeper than just one step into an entry path
     * use addSubEntry().
     *
     * @param identifier Name of the new entry.
     */
    void addEntryInEntryList(String identifier);

    /**This function is like addEntryInEntryList(String identifier) but it adds
     * an entry with content.
     *
     * @param identifier Name of the new entry.
     * @param content Generic content of the new entry.
     */
    void addEntryInEntryList(String identifier, T content);

    /**Returns the entry list of an entry.
     *
     * @return The entry list.
     */
    List<Entry<T>> getEntryList();

    /**Returns a specified entry from the entry list.
     *
     * @param identifier Name of the entry which should be returned.
     * @return The entry with the same name as the identifier.
     */
    Entry<T> getEntryFromList(String identifier);

    /**Alters the content of an entry.
     *
     * @param rootEntry The main node or starting point of all entry paths.
     * @param entryName The name of the entry node where the content should be altered.
     * @param content The new content.
     */
    void alterContentFromEntry(Entry<T> rootEntry, String entryName, T content);

    /**Removes an entry.
     *
     * @param rootEntry The main node or starting point of all entry paths.
     * @param entryName Name of the entry which should be removed.
     */
    void removeEntry(Entry<T> rootEntry, String entryName);

    /**Removes an entry but is used if the identifiers of entries in the entry list of multiple entries is equal.
     * So you can specify the location or the path where the entry should be deleted.
     *
     * @param rootEntry The main node or starting point of all entry paths.
     * @param subEntry Specifying entry.
     * @param entryName Name of the entry which should be deleted.
     */
    void removeEntryFromSubEntry(Entry<T> rootEntry, String subEntry, String entryName);
}
