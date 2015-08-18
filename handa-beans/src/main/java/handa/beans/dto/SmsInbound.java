package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "smsInbound")
public class SmsInbound
{
    private String mobileNumber;
    private String message;
    private String timestamp;

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("mobileNumber", mobileNumber)
               .add("message", message)
               .add("timestamp", timestamp)
               .toString();
    }
}