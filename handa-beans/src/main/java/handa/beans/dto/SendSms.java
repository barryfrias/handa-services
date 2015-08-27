package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sendSms")
public class SendSms
{
    private String recipients;
    private String message;
    private String distributionListKey;
    private List<String> distributionListValues;
    private List<String> anonymousNumbers;
    private String createdBy;

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

    public List<String> getDistributionListValues()
    {
        return distributionListValues;
    }

    public void setDistributionListValues(List<String> distributionListValues)
    {
        this.distributionListValues = distributionListValues;
    }

    public List<String> getAnonymousNumbers()
    {
        return anonymousNumbers;
    }

    public void setAnonymousNumbers(List<String> anonymousNumbers)
    {
        this.anonymousNumbers = anonymousNumbers;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("recipients", recipients)
               .add("message", message)
               .add("distributionListKey", distributionListKey)
               .add("distributionListValues", distributionListValues)
               .add("anonymousNumbers", anonymousNumbers)
               .add("createdBy", createdBy)
               .toString();
    }
}