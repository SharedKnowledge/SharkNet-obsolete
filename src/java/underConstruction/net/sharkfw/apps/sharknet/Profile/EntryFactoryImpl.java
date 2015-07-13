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
    public <T> Entry<T> createEntry(String identifier, T content) {
        Entry<T> entry = new EntryImpl<T>(identifier, content);
        entryList.add(entry);
        return entry;
    }

    @Override
    public Entry<?> getEntry(String identifier) {
        Entry<?> entry = null;
        for (int i = 0; i < entryList.size(); i++) {
            if (entryList.get(i).getEntryName().equals(identifier)) {
                System.out.println(entryList.get(i).getContent().toString());
                entry = entryList.get(i);
            }
        }
        return entry;
    }

    @Override
    public Iterator<Entry<?>> getEntries() {
        return entryList.iterator();
    }

    @Override
    public void removeEntry(String identifier) {
        for (int i = 0; i < entryList.size(); i++) {
            if (entryList.get(i).getEntryName().equals(identifier)) {
                entryList.remove(i);
            }
        }
    }
}
