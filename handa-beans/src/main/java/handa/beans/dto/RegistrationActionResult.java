package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "registrationActionResult")
public class RegistrationActionResult
{
    private String message;
    @JsonIgnore private String passcode;
    @JsonIgnore private String mail;
    @JsonIgnore private String mobileNumber;

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

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("message", message)
               .add("passcode", passcode)
               .add("mail", mail)
               .add("mobileNumber", mobileNumber)
               .toString();
    }
}