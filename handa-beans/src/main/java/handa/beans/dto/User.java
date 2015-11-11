package handa.beans.dto;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User
implements SQLData
{
    private long employeeNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String department;
    private String landlineNo;
    private String mobileNo;
    private String permanentAddress;
    private String currentAddress;
    private String city;
    private String iceContactPerson;
    private String iceLandlineNo;
    private String iceMobileNo;
    private String bloodType;
    private String remarks;
    private String latitude;
    private String longitude;
    private String username;
    private String immediateHead;
    private String company;
    private String position;
    private String province;
    private String modifiedBy;

    public long getEmployeeNumber()
    {
        return employeeNumber;
    }

    public void setEmployeeNumber(long employeeNumber)
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

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getLandlineNo()
    {
        return landlineNo;
    }

    public void setLandlineNo(String landlineNo)
    {
        this.landlineNo = landlineNo;
    }

    public String getMobileNo()
    {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }

    public String getPermanentAddress()
    {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress)
    {
        this.permanentAddress = permanentAddress;
    }

    public String getCurrentAddress()
    {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress)
    {
        this.currentAddress = currentAddress;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getIceContactPerson()
    {
        return iceContactPerson;
    }

    public void setIceContactPerson(String iceContactPerson)
    {
        this.iceContactPerson = iceContactPerson;
    }

    public String getIceLandlineNo()
    {
        return iceLandlineNo;
    }

    public void setIceLandlineNo(String iceLandlineNo)
    {
        this.iceLandlineNo = iceLandlineNo;
    }

    public String getIceMobileNo()
    {
        return iceMobileNo;
    }

    public void setIceMobileNo(String iceMobileNo)
    {
        this.iceMobileNo = iceMobileNo;
    }

    public String getBloodType()
    {
        return bloodType;
    }

    public void setBloodType(String bloodType)
    {
        this.bloodType = bloodType;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
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

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }

    @JsonIgnore @Override
    public String getSQLTypeName() throws SQLException
    {
        return "USER_TYPE";
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeLong(getEmployeeNumber());
        stream.writeString(getFirstName());
        stream.writeString(getLastName());
        stream.writeString(getMiddleName());
        stream.writeString(getDepartment());
        stream.writeString(getLandlineNo());
        stream.writeString(getMobileNo());
        stream.writeString(getPermanentAddress());
        stream.writeString(getCurrentAddress());
        stream.writeString(getCity());
        stream.writeString(getIceContactPerson());
        stream.writeString(getIceLandlineNo());
        stream.writeString(getIceMobileNo());
        stream.writeString(getBloodType());
        stream.writeString(getRemarks());
        stream.writeString(getLatitude());
        stream.writeString(getLongitude());
        stream.writeString(getUsername());
        stream.writeString(getImmediateHead());
        stream.writeString(getCompany());
        stream.writeString(getPosition());
        stream.writeString(getProvince());
        stream.writeString(getModifiedBy());
    }
}