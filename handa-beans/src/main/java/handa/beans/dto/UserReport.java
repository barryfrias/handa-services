package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "userReport")
public class UserReport
{
    private int id;
    private String name;
    private String mobileNumber;
    private String macAddress;
    private String osVersion;
    private String batteryLevel;
    private String longitude;
    private String latitude;
    private String locationIndicator;
    private String message;
    private String eventType;
    private String imageFilename;
    private String status;
    private String createdDate;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

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

    public String getLocationIndicator()
    {
        return locationIndicator;
    }

    public void setLocationIndicator(String locationIndicator)
    {
        this.locationIndicator = locationIndicator;
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("id", id)
               .add("name", name)
               .add("mobileNumber", mobileNumber)
               .add("macAddress", macAddress)
               .add("osVersion", osVersion)
               .add("batteryLevel", batteryLevel)
               .add("longitude", longitude)
               .add("latitude", latitude)
               .add("message", message)
               .add("eventType", eventType)
               .add("imageFilename", imageFilename)
               .add("status", status)
               .add("createdDate", createdDate)
               .toString();
    }
}