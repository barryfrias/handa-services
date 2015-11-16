package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dtrInput")
public class DtrInput
{
    private String mobileNumber;
    private String latitude;
    private String longitude;

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
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

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("mobileNumber", mobileNumber)
               .add("latitude", latitude)
               .add("longitude", longitude)
               .toString();
    }
}