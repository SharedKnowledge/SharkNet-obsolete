package ProfileTests;

import Profile.Entry;
import Profile.EntryFactory;
import Profile.EntryFactoryImpl;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by Mr.T on 13.07.2015.
 */
public class EntryFactoryImplTest {

    @Test
    public void testCreateEntry() throws Exception {
        EntryFactory factory = new EntryFactoryImpl();
        factory.createEntry("Alter", 22);
        factory.createEntry("Name", "Thomas");
        factory.createEntry("Geschlecht", "M");
        Entry<?> integerEntry = factory.getEntry("Alter");
        System.out.println(integerEntry.getEntryName());
        System.out.println(integerEntry.getContent());
        Iterator<Entry<?>> ie = factory.getEntries();
        while (ie.hasNext() == true) {
            System.out.println(ie.next().getEntryName());
        }
    }

    @Test
    public void testGetEntry() throws Exception {

    }

    @Test
    public void testGetEntries() throws Exception {

    }
}