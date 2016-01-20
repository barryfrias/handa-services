package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "userRegistration")
public class UserRegistration
{
    private String usernameOrEmailAdd;
    private String firstName;
    private String lastName;
    private String companyCode;
    private String mobileNumber;

    public String getUsernameOrEmailAdd()
    {
        return usernameOrEmailAdd;
    }

    public void setUsernameOrEmailAdd(String usernameOrEmailAdd)
    {
        this.usernameOrEmailAdd = usernameOrEmailAdd;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getCompanyCode()
    {
        return companyCode;
    }

    public void setCompanyCode(String companyCode)
    {
        this.companyCode = companyCode;
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
               .add("usernameOrEmailAdd", usernameOrEmailAdd)
               .add("firstName", firstName)
               .add("lastName", lastName)
               .add("mobileNumber", mobileNumber)
               .add("companyCode", companyCode)
               .toString();
    }
}