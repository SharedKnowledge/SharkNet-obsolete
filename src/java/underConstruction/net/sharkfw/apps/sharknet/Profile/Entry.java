package Profile;

/**
 * Created by Mr.T on 03.07.2015.
 */
public interface Entry<T> {
    void setContent(T content);
    T getContent();
    String getEntryName();
}
