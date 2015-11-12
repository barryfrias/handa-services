package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ldapUser")
public class LdapUser
{
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String position;
    private String department;
    private String immediateHead;
    private String company;
    private String adUsername;

    public String getEmployeeNumber()
    {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber)
    {
        this.employeeNumber = employeeNumber;
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

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
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

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }


    public String getAdUsername()
    {
        return adUsername;
    }

    public void setAdUsername(String adUsername)
    {
        this.adUsername = adUsername;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("employeeNumber", employeeNumber)
               .add("firstName", firstName)
               .add("lastName", lastName)
               .add("middleName", middleName)
               .add("position", position)
               .add("department", department)
               .add("immediateHead", immediateHead)
               .add("adUsername", adUsername)
               .toString();
    }
}