package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "registrationAction")
public class RegistrationAction
{
    private String action;
    private String username;
    private String reason;

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("action", action)
               .add("username", username)
               .add("reason", reason)
               .toString();
    }
}