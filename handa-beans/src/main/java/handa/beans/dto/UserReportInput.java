package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "userReportInput")
public class UserReportInput
{
    private String mobileNumber;
    private String macAddress;
    private String osVersion;
    private String appVersion;
    private String batteryLevel;
    private String longitude;
    private String latitude;
    private String province;
    private String city;
    private String barangay;
    private String address;
    private String message;
    private String eventType;
    private String imageFilename;

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getMacAddress()
    {
        return macAddress;
    }

    public void setMacAddress(String macAddress)
    {
        this.macAddress = macAddress;
    }

    public String getOsVersion()
    {
        return osVersion;
    }

    public void setOsVersion(String osVersion)
    {
        this.osVersion = osVersion;
    }

    public String getAppVersion()
    {
        return appVersion;
    }

    public void setAppVersion(String appVersion)
    {
        this.appVersion = appVersion;
    }

    public String getBatteryLevel()
    {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel)
    {
        this.batteryLevel = batteryLevel;
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

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public String getImageFilename()
    {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename)
    {
        this.imageFilename = imageFilename;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("mobileNumber", mobileNumber)
               .add("macAddress", macAddress)
               .add("osVersion", osVersion)
               .add("appVersion", appVersion)
               .add("batteryLevel", batteryLevel)
               .add("longitude", longitude)
               .add("latitude", latitude)
               .add("province", province)
               .add("city", city)
               .add("barangay", barangay)
               .add("address", address)
               .add("message", message)
               .add("eventType", eventType)
               .add("imageFilename", imageFilename)
               .toString();
    }
}