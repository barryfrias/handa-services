package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sendSmsInput")
public class SendSmsInput
{
    private String keyword;
    private String chargeId;
    private String mobile;
    private String message;
    private String queuePath;
    
    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getChargeId()
    {
        return chargeId;
    }

    public void setChargeId(String chargeId)
    {
        this.chargeId = chargeId;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getQueuePath()
    {
        return queuePath;
    }

    public void setQueuePath(String queuePath)
    {
        this.queuePath = queuePath;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("keyword", keyword)
               .add("chargeId", chargeId)
               .add("mobile", mobile)
               .add("message", message)
               .add("queuePath", queuePath)
               .toString();
    }
}