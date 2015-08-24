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
 * Created by Mr.T on 22.04.2015.
 */
public interface ProfileName {
    /**Sets the surname of a profile.
     *
     * @param surname The surname.
     * @throws SharkKBException This message is thrown when no SharkKB is found or if there is another problem with the SharkKB.
     */
    void setSurname(String surname) throws SharkKBException;

    /**
     *
     * @return
     */
    String getSurname();

    void setLastName(String lastName);
    String getLastName();

    void setTitle(String title);
    String getTitle();
}