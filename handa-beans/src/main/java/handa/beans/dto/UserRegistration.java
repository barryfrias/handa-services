package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "userRegistration")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserRegistration
{
    private long employeeNumber;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String position;
    private String department;
    private String immediateHead;
    private String companyCode;
    private String mobileNumber;
    private String username;
    private String password;
    private String address;
    private String province;
    private String city;
    private String barangay;

    public long getEmployeeNumber()
    {
        return employeeNumber;
    }

    public void setEmployeeNumber(long employeeNumber)
    {
        this.employeeNumber = employeeNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getImmediateHead()
    {
        return immediateHead;
    }

    public void setImmediateHead(String immediateHead)
    {
        this.immediateHead = immediateHead;
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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getBarangay()
    {
        return barangay;
    }

    public void setBarangay(String barangay)
    {
        this.barangay = barangay;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("email", email)
               .add("username", username)
               .add("firstName", firstName)
               .add("lastName", lastName)
               .add("mobileNumber", mobileNumber)
               .add("companyCode", companyCode)
               .add("address", address)
               .add("province", province)
               .add("city", city)
               .add("barangay", barangay)
               .toString();
    }
}