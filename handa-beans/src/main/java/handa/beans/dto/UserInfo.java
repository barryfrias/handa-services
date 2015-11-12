package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "userInfo")
public class UserInfo
{
    private int rowNum;
    private String key;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String position;
    private String department;
    private String immediateHead;
    private String company;
    private String landlineNo;
    private String mobileNumber;
    private String permanentAddress;
    private String currentAddress;
    private String iceContactPerson;
    private String iceLandlineNumber;
    private String iceMobileNumber;
    private String bloodType;
    private String remarks;
    private String longitude;
    private String latitude;
    private String city;
    private String createdDate;
    private String adUsername;
    private String province;
    private String modifiedBy;

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

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

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getAdUsername()
    {
        return adUsername;
    }

    public void setAdUsername(String adUsername)
    {
        this.adUsername = adUsername;
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

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("rowNum", rowNum)
               .add("key", key)
               .add("employeeNumber", employeeNumber)
               .add("firstName", firstName)
               .add("lastName", lastName)
               .add("middleName", middleName)
               .add("position", position)
               .add("department", department)
               .add("immediateHead", immediateHead)
               .add("landlineNo", landlineNo)
               .add("mobileNumber", mobileNumber)
               .add("permanentAddress", permanentAddress)
               .add("currentAddress", currentAddress)
               .add("iceContactPerson", iceContactPerson)
               .add("iceLandlineNumber", iceLandlineNumber)
               .add("iceMobileNumber", iceMobileNumber)
               .add("bloodType", bloodType)
               .add("remarks", remarks)
               .add("longitude", longitude)
               .add("latitude", latitude)
               .add("city", city)
               .add("createdDate", createdDate)
               .add("adUsername", adUsername)
               .add("province", province)
               .add("modifiedBy", modifiedBy)
               .toString();
    }
}