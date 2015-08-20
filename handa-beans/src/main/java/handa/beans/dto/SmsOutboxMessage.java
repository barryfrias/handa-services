package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "smsOutboxMessage")
public class SmsOutboxMessage
{
    private int id;
    private String recipients;
    private String message;
    private String status;
    private String createdBy;
    private String createdDate;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getRecipients()
    {
        return recipients;
    }

    public void setRecipients(String recipients)
    {
        this.recipients = recipients;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("id", id)
               .add("recipients", recipients)
               .add("message", message)
               .add("status", status)
               .add("createdBy", createdBy)
               .add("createdDate", createdDate)
               .toString();
    }
}