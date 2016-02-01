package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "userVerificationResult")
public class UserVerificationResult
{
    private String message;
    private String authMethod;
    @JsonIgnore private String passcode;
    private String mobileNumber;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getAuthMethod()
    {
        return authMethod;
    }

    public void setAuthMethod(String authMethod)
    {
        this.authMethod = authMethod;
    }

    public String getPasscode()
    {
        return passcode;
    }

    public void setPasscode(String passcode)
    {
        this.passcode = passcode;
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
               .add("authMethod", authMethod)
               .add("passcode", passcode)
               .add("mobileNumber", mobileNumber)
               .toString();
    }
}