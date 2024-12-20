package handa.beans.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@XmlRootElement(name = "userPrompt")
@JsonIgnoreProperties(ignoreUnknown=true)
public class SosPrompt
{
    private static ObjectMapper om = new ObjectMapper();
    private int rowNum;
    private int id;
    private String name;
    private String company;
    private String mobileNumber;
    private String deviceInfo;
    private String longitude;
    private String latitude;
    private String locationIndicator;
    private String batteryLevel;
    private String promptType;
    private String status;
    private String referenceNumber;
    private String remarks;
    private String createdDate;

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
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

    public String getLocationIndicator()
    {
        return locationIndicator;
    }

    public void setLocationIndicator(String locationIndicator)
    {
        this.locationIndicator = locationIndicator;
    }

    public String getBatteryLevel()
    {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel)
    {
        this.batteryLevel = batteryLevel;
    }

    public String getPromptType()
    {
        return promptType;
    }

    public void setPromptType(String promptType)
    {
        this.promptType = promptType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getReferenceNumber()
    {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber)
    {
        this.referenceNumber = referenceNumber;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
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
        try
        {
            return om.writeValueAsString(this);
        }
        catch(JsonProcessingException e)
        {
            return this.toString();
        }
    }
}