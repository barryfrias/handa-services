package handa.beans.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "closePrompt")
public class ClosePrompt
{
    private String referenceNumber;
    private String reason;
    private String username;

    public String getReferenceNumber()
    {
        return referenceNumber;
    }
    public void setReferenceNumber(String referenceNumber)
    {
        this.referenceNumber = referenceNumber;
    }
    public String getReason()
    {
        return reason;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
}