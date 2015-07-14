package Profile;

import java.util.List;

/**
 * Created by Mr.T on 03.07.2015.
 */
public interface Entry<T> {
    void setContent(T content);
    T getContent();
    String getEntryName();
    void addEntryInEntryList(String identifier);
    void addEntryInEntryList(String identifier, T content);
    List<Entry<T>> getEntryList();
    Entry<T> getEntryFromList(String identifier);
    void alterEntryContentInEntryList(String identifier, T content);
}
