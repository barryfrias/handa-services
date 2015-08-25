package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "smsOutboxMessage")
public class SmsOutboxMessage
{
    private int id;
    private String recipients;
    private String message;
    private String distributionListKey;
    private String distributionListValues;
    private String anonymousNumbers;
    private String status;
    private String createdBy;
    private String createdDate;
    private String deletedFlag;
    private String deletedBy;
    private String deletedDate;

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

    public String getDistributionListKey()
    {
        return distributionListKey;
    }

    public void setDistributionListKey(String distributionListKey)
    {
        this.distributionListKey = distributionListKey;
    }

    public String getDistributionListValues()
    {
        return distributionListValues;
    }

    public void setDistributionListValues(String distributionListValues)
    {
        this.distributionListValues = distributionListValues;
    }

    public String getAnonymousNumbers()
    {
        return anonymousNumbers;
    }

    public void setAnonymousNumbers(String anonymousNumbers)
    {
        this.anonymousNumbers = anonymousNumbers;
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

    public String getDeletedFlag()
    {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag)
    {
        this.deletedFlag = deletedFlag;
    }

    public String getDeletedBy()
    {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy)
    {
        this.deletedBy = deletedBy;
    }

    public String getDeletedDate()
    {
        return deletedDate;
    }

    public void setDeletedDate(String deletedDate)
    {
        this.deletedDate = deletedDate;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("id", id)
               .add("recipients", recipients)
               .add("message", message)
               .add("distributionListKey", distributionListKey)
               .add("distributionListValues", distributionListValues)
               .add("anonymousNumbers", anonymousNumbers)
               .add("status", status)
               .add("createdBy", createdBy)
               .add("createdDate", createdDate)
               .add("deletedFlag", deletedFlag)
               .add("deletedBy", deletedBy)
               .add("deletedDate", deletedDate)
               .toString();
    }
}