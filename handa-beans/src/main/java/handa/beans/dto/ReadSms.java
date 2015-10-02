package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "readSms")
public class ReadSms
{
    private String readBy;

    public String getReadBy()
    {
        return readBy;
    }

    public void setReadBy(String readBy)
    {
        this.readBy = readBy;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("readBy", readBy)
               .toString();
    }
}