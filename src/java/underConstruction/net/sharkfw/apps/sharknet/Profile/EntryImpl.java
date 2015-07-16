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
        if (this.entryName.equals(saveNewSubEntryUnderThisEntryName)) {
            this.addEntryInEntryList(subEntryName);
        }
        Iterator<Entry<T>> entryListIterator = entryList.iterator();
        while (!this.entryName.equals(saveNewSubEntryUnderThisEntryName)) {
            while (entryListIterator.hasNext()) {
                addSubEntry(entryListIterator.next(), saveNewSubEntryUnderThisEntryName, subEntryName);
            }
        }
    }

    @Override
    public void addSubEntry(Entry<T> rootEntry, String saveNewSubEntryUnderThisEntryName, String subEntryName, T content) {
        if (this.entryName.equals(saveNewSubEntryUnderThisEntryName)) {
            this.addEntryInEntryList(subEntryName, content);
        }
        else {
            Iterator<Entry<T>> entryListIterator = entryList.iterator();
            while (!this.entryName.equals(saveNewSubEntryUnderThisEntryName)) {
                while (entryListIterator.hasNext()) {
                    addSubEntry(entryListIterator.next(), saveNewSubEntryUnderThisEntryName, subEntryName, content);
                }
            }
        }
    }

    @Override
    public Entry<T> getSubEntry(Entry<T> rootEntry, String subEntryName) {
        Entry<T> subEntry = null;
        Entry<T> tempEntry;
        if (this.entryName.equals(subEntryName)) {
            return this;
        }
        else {
            Iterator<Entry<T>> subEntryIterator = entryList.iterator();
            while (subEntryIterator.hasNext()) {
                tempEntry = getSubEntry(subEntryIterator.next(), subEntryName);
                if (tempEntry.getEntryName().equals(subEntryName)) {
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
    public void alterEntryContentInEntryList(String identifier, T content) {
        Entry<T> entry = getEntryFromList(identifier);
        entry.setContent(content);
    }
}
