package Profile;

import net.sharkfw.knowledgeBase.*;

/**
 * Created by Mr.T on 22.04.2015.
 */
public interface ProfileEntry {
    int getUniqueID();
    byte[] getDescriptionByte();
    String getDescriptionString();
    String getContentType();

    void setLocation(SpatialSemanticTag sst) throws SharkKBException;
    SpatialSemanticTag getLocation() throws SharkKBException;

    void setTimeFrom(TimeSemanticTag timeFrom) throws SharkKBException;
    TimeSemanticTag getTimeFrom() throws SharkKBException;

    void setTimeTo(TimeSemanticTag timeTo) throws SharkKBException;
    TimeSemanticTag getTimeTo() throws SharkKBException;
}
