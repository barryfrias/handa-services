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
    private String mobileNumber;
    private String permanentAddress;
    private String currentAddress;
    private String city;
    private String iceContactPerson;
    private String iceLandlineNumber;
    private String iceMobileNumber;
    private String bloodType;
    private String remarks;
    private String latitude;
    private String longitude;
    private String adUsername;
    private String immediateHead;
    private String company;
    private String position;
    private String province;
    private String modifiedBy;
    private String createdDate;
    private String key;
    private String barangay;
    private String permAddProvince;
    private String permAddCity;
    private String permAddBarangay;

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

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
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

    public String getIceLandlineNumber()
    {
        return iceLandlineNumber;
    }

    public void setIceLandlineNumber(String iceLandlineNumber)
    {
        this.iceLandlineNumber = iceLandlineNumber;
    }

    public String getIceMobileNumber()
    {
        return iceMobileNumber;
    }

    public void setIceMobileNumber(String iceMobileNumber)
    {
        this.iceMobileNumber = iceMobileNumber;
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

    public String getAdUsername()
    {
        return adUsername;
    }

    public void setAdUsername(String adUsername)
    {
        this.adUsername = adUsername;
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

    public String getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getBarangay()
    {
        return barangay;
    }

    public void setBarangay(String barangay)
    {
        this.barangay = barangay;
    }

    public String getPermAddProvince()
    {
        return permAddProvince;
    }

    public void setPermAddProvince(String permAddProvince)
    {
        this.permAddProvince = permAddProvince;
    }

    public String getPermAddCity()
    {
        return permAddCity;
    }

    public void setPermAddCity(String permAddCity)
    {
        this.permAddCity = permAddCity;
    }

    public String getPermAddBarangay()
    {
        return permAddBarangay;
    }

    public void setPermAddBarangay(String permAddBarangay)
    {
        this.permAddBarangay = permAddBarangay;
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
        stream.writeString(getMobileNumber());
        stream.writeString(getCurrentAddress());
        stream.writeString(getProvince());
        stream.writeString(getCity());
        stream.writeString(getBarangay());
        stream.writeString(getPermanentAddress());
        stream.writeString(getPermAddProvince());
        stream.writeString(getPermAddCity());
        stream.writeString(getPermAddBarangay());
        stream.writeString(getIceContactPerson());
        stream.writeString(getIceLandlineNumber());
        stream.writeString(getIceMobileNumber());
        stream.writeString(getBloodType());
        stream.writeString(getRemarks());
        stream.writeString(getLatitude());
        stream.writeString(getLongitude());
        stream.writeString(getAdUsername());
        stream.writeString(getImmediateHead());
        stream.writeString(getCompany());
        stream.writeString(getPosition());
        stream.writeString(getModifiedBy());
        stream.writeString(getCreatedDate());
        stream.writeString(getKey());
    }
}