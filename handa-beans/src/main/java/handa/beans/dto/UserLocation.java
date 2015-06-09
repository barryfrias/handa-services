package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "userLocation")
public class UserLocation
{
    private String name;
    private String mobileNumber;
    private String longitude;
    private String latitude;
    private String promptType;
    private String createdDate;

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

    public String getPromptType()
    {
        return promptType;
    }

    public void setPromptType(String promptType)
    {
        this.promptType = promptType;
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
               .add("name", name)
               .add("mobileNumber", mobileNumber)
               .add("longitude", longitude)
               .add("latitude", latitude)
               .add("promptType", promptType)
               .add("createdDate", createdDate)
               .toString();
    }
}