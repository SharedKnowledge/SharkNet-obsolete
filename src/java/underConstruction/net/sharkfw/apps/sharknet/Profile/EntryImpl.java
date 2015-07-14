package Profile;

import java.io.Serializable;
import java.util.ArrayList;
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
