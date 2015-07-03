package Profile;

/**
 * Created by Mr.T on 03.07.2015.
 */
public class EntryImpl<T> implements Entry<T> {
    private String entryName;
    private T content;
    EntryImpl(String entryName) {
        this.entryName = entryName;
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
