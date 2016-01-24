package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "registrationActionResult")
public class RegistrationActionResult
{
    private String message;
    private String passcode;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getPasscode()
    {
        return passcode;
    }

    public void setPasscode(String passcode)
    {
        this.passcode = passcode;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("message", message)
               .add("passcode", passcode)
               .toString();
    }
}