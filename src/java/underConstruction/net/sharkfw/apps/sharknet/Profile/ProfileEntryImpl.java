package Profile;

import net.sharkfw.kep.format.XMLSerializer;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.system.L;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Mr.T on 06.05.2015.
 * Wir brauchen noch eine Art allgemeine Entry die von jedem datentyp sein kann also generic,
 * davon muss dann mindestens einer im konstruktor erstellt werden und es können aber beliebig
 * viele erstellt werden.
 * Damit solche Entrys wie setLocation und getLocation erstellt werden können^^
 */
public class ProfileEntryImpl implements ProfileEntry, Serializable {
    private static int uniqueID = 0;
    private byte[] descriptionByte = null;
    private String descriptionString = null;
    private String location = null;
    private String timeFrom = null;
    private String timeTo = null;
    private String contentType = null;

    ProfileEntryImpl(String descriptionString, String content) {
        this.uniqueID += 1;
        this.descriptionString = descriptionString;
        this.contentType = content;
    }

    ProfileEntryImpl(byte[] descriptionByte, String content) {
        this.uniqueID += 1;
        this.descriptionByte = descriptionByte;
        this.contentType = content;
    }

    @Override
    public int getUniqueID() {
        return uniqueID;
    }

    @Override
    public byte[] getDescriptionByte() {
        byte[] bytes = null;
        if (descriptionByte != null) {
            bytes = descriptionByte;
        }
        else {
            try {
                bytes = Serializer.serialize(descriptionString);
            } catch (IOException e) {
                L.e(e.getMessage(), this);
            }
        }
        return bytes;
    }

    @Override
    public String getDescriptionString() {
        if (descriptionString != null) {
            return descriptionString;
        }
        else {
            throw new NullPointerException("Description can not be returned with type string.");
        }
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setLocation(SpatialSemanticTag sst) throws SharkKBException {
        SpatialSTSet sstSet = InMemoSharkKB.createInMemoSpatialSTSet();
        sstSet.merge(sst);
        XMLSerializer xs = new XMLSerializer();
        location = xs.serializeSTSet(sstSet);
    }

    @Override
    public SpatialSemanticTag getLocation() throws SharkKBException {
        SpatialSTSet SSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        XMLSerializer xs = new XMLSerializer();
        xs.deserializeSTSet(SSTSet, location);
        return SSTSet.spatialTags().nextElement();
    }

    @Override
    public void setTimeFrom(TimeSemanticTag timeFrom) throws SharkKBException {
        TimeSTSet TSTSet = InMemoSharkKB.createInMemoTimeSTSet();
        TSTSet.createTimeSemanticTag(timeFrom.getDuration(), timeFrom.getFrom());
        XMLSerializer xs = new XMLSerializer();
        this.timeFrom = xs.serializeSTSet(TSTSet);
    }

    @Override
    public TimeSemanticTag getTimeFrom() throws SharkKBException {
        TimeSTSet TSTSet = InMemoSharkKB.createInMemoTimeSTSet();
        XMLSerializer xs = new XMLSerializer();
        xs.deserializeSTSet(TSTSet, timeFrom);
        return TSTSet.timeTags().nextElement();
    }

    @Override
    public void setTimeTo(TimeSemanticTag timeTo) throws SharkKBException {
        TimeSTSet TSTSet = InMemoSharkKB.createInMemoTimeSTSet();
        TSTSet.createTimeSemanticTag(timeTo.getDuration(), timeTo.getFrom());
        XMLSerializer xs = new XMLSerializer();
        this.timeTo = xs.serializeSTSet(TSTSet);
    }

    @Override
    public TimeSemanticTag getTimeTo() throws SharkKBException {
        TimeSTSet TSTSet = InMemoSharkKB.createInMemoTimeSTSet();
        XMLSerializer xs = new XMLSerializer();
        xs.deserializeSTSet(TSTSet, timeTo);
        return TSTSet.timeTags().nextElement();
    }
}
