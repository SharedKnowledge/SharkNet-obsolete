package Profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mr.T on 03.07.2015.
 */
public class EntryImpl<T> implements Entry<T>, Serializable {
    private String entryName = "";
    private T content = null;
    private List<Entry<T>> entryList = new ArrayList<Entry<T>>();

    public EntryImpl(String entryName) {
        this.entryName = entryName;
    }

    public EntryImpl(String entryName, T content) {
        this.entryName = entryName;
        this.content = content;
    }

    public EntryImpl(String entryName, List<Entry<T>> entryList) {
        this.entryName = entryName;
        this.entryList = entryList;
    }

    @Override
    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public T getContent() {
        return content;
    }

    @Override
    public String getEntryName() {
        return entryName;
    }

    @Override
    public void addSubEntry(Entry<?> rootEntry, String saveNewSubEntryUnderThisEntryName, String subEntryName) {
        if (rootEntry.getEntryName().equals(saveNewSubEntryUnderThisEntryName)) {
            rootEntry.addEntryInEntryList(subEntryName);
        } else {
            for (Entry<?> entry : rootEntry.getEntryList()) {
                addSubEntry(entry, saveNewSubEntryUnderThisEntryName, subEntryName);
            }
        }
    }

    @Override
    public void addSubEntry(Entry<T> rootEntry, String saveNewSubEntryUnderThisEntryName, String subEntryName, T content) {
        if (rootEntry.getEntryName().equals(saveNewSubEntryUnderThisEntryName)) {
            rootEntry.addEntryInEntryList(subEntryName, content);
        } else {
            for (Entry<T> entry : rootEntry.getEntryList()) {
                addSubEntry(entry, saveNewSubEntryUnderThisEntryName, subEntryName, content);
            }
        }
    }

    @Override
    public Entry<T> getSubEntry(Entry<T> rootEntry, String subEntryName) {
        Entry<T> subEntry = null;
        Entry<T> tempEntry;
        if (rootEntry.getEntryName().equals(subEntryName)) {
            return rootEntry;
        } else {
            for (Entry<T> entry : rootEntry.getEntryList()) {
                tempEntry = getSubEntry(entry, subEntryName);
                if (tempEntry != null) {
                    subEntry = tempEntry;
                }

            }
        }
        return subEntry;
    }

    @Override
    public void addEntryInEntryList(String identifier) {
        entryList.add(new EntryImpl<T>(identifier));
    }

    @Override
    public void addEntryInEntryList(String identifier, T content) {
        entryList.add(new EntryImpl<T>(identifier, content));
    }

    @Override
    public List<Entry<T>> getEntryList() {
        return entryList;
    }

    @Override
    public Entry<T> getEntryFromList(String identifier) {
        Entry<T> entry = null;
        for (int i = 0; i < entryList.size(); i++) {
            if (entryList.get(i).getEntryName().equals(identifier)) {
                entry = entryList.get(i);
            }
        }
        return entry;
    }

    @Override
    public void alterContentFromEntry(Entry<T> rootEntry, String entryName, T content) {
        Entry<T> entry = getSubEntry(rootEntry, entryName);
        entry.setContent(content);
    }

    @Override
    public void removeEntry(Entry<T> rootEntry, String entryName) {
        Entry<T> entry;
        Iterator<Entry<T>> entryIterator = rootEntry.getEntryList().iterator();
        while (entryIterator.hasNext()) {
            entry = entryIterator.next();
            if (entry.getEntryName().equals(entryName)) {
                entryIterator.remove();
            } else {
                    removeEntry(entry, entryName);
            }
        }
    }

    @Override
    public void removeEntryFromSubEntry(Entry<T> rootEntry, String subEntry, String entryName) {
        Entry<T> entry;
        Iterator<Entry<T>> entryIterator = rootEntry.getEntryList().iterator();
        while (entryIterator.hasNext()) {
            entry = entryIterator.next();
            if (entry.getEntryName().equals(subEntry)) {
                removeEntry(entry, entryName);
            } else {
                removeEntryFromSubEntry(entry, subEntry, entryName);
            }
        }
    }

}
