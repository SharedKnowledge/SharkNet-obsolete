package Profile;

import net.sharkfw.knowledgeBase.SharkKBException;

/**This is like a simple container for a profile name.
 * Profile names can consist of several elements like: surname, last name, titles or nicknames.
 * This class is a bit like an entry but a fixed one.
 * We can only use 3 variables in this class (surname, title, last name).
 * If we want to use nicknames too we have to make a new class.
 * This is the reason why there is the entry class.
 * Entries are generic so we could simply add nickname variable to our entry.
 * But for fixed testing issues this class is useful too.
 *
 * Created by Thilo Stegemann on 22.04.2015.
 */
public interface ProfileName {
    /**Sets the surname of a profile.
     *
     * @param surname The surname.
     */
    void setSurname(String surname);

    /**Returns the surname of a profile.
     *
     * @return The surname of a profile.
     */
    String getSurname();

    /**Sets the last name of a profile.
     *
     * @param lastName The last name of a profile.
     */
    void setLastName(String lastName);

    /**Returns the last name of a profile.
     *
     * @return The last name of a profile.
     */
    String getLastName();

    /**Sets the title of a profile.
     *
     * @param title Title of a profile.
     */
    void setTitle(String title);

    /**Returns the title of a profile.
     *
     * @return Title of a profile.
     */
    String getTitle();
}