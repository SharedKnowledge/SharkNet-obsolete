package Profile;

import java.util.List;

/**
 * Created by Mr.T on 03.07.2015.
 */
public interface Entry<T> {
    void setContent(T content);
    T getContent();
    String getEntryName();
    void addSubEntry(Entry<?> rootEntry, String saveNewSubEntryUnderThisEntryName, String subEntryName);
    void addSubEntry(Entry<T> rootEntry, String saveNewSubEntryUnderThisEntryName, String subEntryName, T content);
    Entry<T> getSubEntry(Entry<T> rootEntry, String subEntryName);
    void addEntryInEntryList(String identifier);
    void addEntryInEntryList(String identifier, T content);
    List<Entry<T>> getEntryList();
    Entry<T> getEntryFromList(String identifier);
    void alterContentFromEntry(Entry<T> rootEntry, String entryName, T content);
    void removeEntry(Entry<T> rootEntry, String entryName);
    void removeEntryFromSubEntry(Entry<T> rootEntry, String subEntry, String entryName);
}
