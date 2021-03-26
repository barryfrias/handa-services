package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "userPromptInput")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserPromptInput
{
    private String mobileNumber;
    private String promptType;
    private String deviceInfo;
    private String longitude;
    private String latitude;
    private String province;
    private String city;
    private String barangay;
    private String address;
    private String batteryLevel;

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getPromptType()
    {
        return promptType;
    }

    public void setPromptType(String promptType)
    {
        this.promptType = promptType;
    }

    public String getDeviceInfo()
    {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo)
    {
        this.deviceInfo = deviceInfo;
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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getBatteryLevel()
    {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel)
    {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("mobileNumber", mobileNumber)
               .add("promptType", promptType)
               .add("deviceInfo", deviceInfo)
               .add("longitude", longitude)
               .add("latitude", latitude)
               .add("province", province)
               .add("city", city)
               .add("barangay", barangay)
               .add("address", address)
               .add("batteryLevel", batteryLevel)
               .toString();
    }
}