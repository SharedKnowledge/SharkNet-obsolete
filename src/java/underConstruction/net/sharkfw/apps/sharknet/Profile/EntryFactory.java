package Profile;

import java.util.Iterator;

/**
 * Created by Mr.T on 03.07.2015.
 */
public interface EntryFactory {
    <T> void createEntry(String identifier, T content);
    Entry<?> getEntry(String identifier);
    Iterator<Entry<?>> getEntries();
}
