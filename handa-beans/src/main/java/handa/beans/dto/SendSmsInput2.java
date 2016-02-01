package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sendSmsInput")
public class SendSmsInput2
{
    private String appCode;
    private String keyword;
    private String mobile;
    private String message;

    public String getAppCode()
    {
        return appCode;
    }

    public void setAppCode(String appCode)
    {
        this.appCode = appCode;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
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

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("appCode", appCode)
               .add("keyword", keyword)
               .add("mobile", mobile)
               .add("message", message)
               .toString();
    }
}