package Profile;

import java.io.Serializable;

/**
 * Created by Mr.T on 03.07.2015.
 */
public class EntryImpl<T> implements Entry<T>, Serializable {
    private String entryName;
    private T content;
    public EntryImpl(String entryName) {
        this.entryName = entryName;
    }

    public EntryImpl(String entryName, T content) {
        this.entryName = entryName;
        this.content = content;
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
}
