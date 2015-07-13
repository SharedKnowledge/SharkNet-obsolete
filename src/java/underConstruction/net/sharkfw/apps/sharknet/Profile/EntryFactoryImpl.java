package Profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mr.T on 03.07.2015.
 */
public class EntryFactoryImpl implements EntryFactory {
    private List<Entry<?>> entryList = new ArrayList<Entry<?>>();

    @Override
    public <T> void createEntry(String identifier, T content) {
        entryList.add(new EntryImpl<T>(identifier, content));
    }

    @Override
    public Entry getEntry(String identifier) {
        Entry<?> entry = null;
        for (int i = 0; i < entryList.size(); i++) {
            if (entryList.get(i).getEntryName().equals(identifier)) {
                entry = entryList.get(i);
            }
        }
        return entry;
    }

    @Override
    public Iterator<Entry<?>> getEntries() {
        Iterator<Entry<?>> entryIterator = entryList.iterator();
        return entryIterator;
    }
}
