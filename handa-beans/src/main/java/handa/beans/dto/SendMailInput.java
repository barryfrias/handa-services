package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sendMailInput")
public class SendMailInput
{
    private String appCode;
    private String from;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String message;

    public String getAppCode()
    {
        return appCode;
    }

    public void setAppCode(String appCode)
    {
        this.appCode = appCode;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String[] getTo()
    {
        return to;
    }

    public void setTo(String[] to)
    {
        this.to = to;
    }

    public String[] getCc()
    {
        return cc;
    }

    public void setCc(String[] cc)
    {
        this.cc = cc;
    }

    public String[] getBcc()
    {
        return bcc;
    }

    public void setBcc(String[] bcc)
    {
        this.bcc = bcc;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
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
               .add("from", from)
               .add("to", Arrays.toString(to))
               .add("cc", Arrays.toString(cc))
               .add("bcc", Arrays.toString(bcc))
               .add("subject", subject)
               .add("message", message)
               .toString();
    }
}