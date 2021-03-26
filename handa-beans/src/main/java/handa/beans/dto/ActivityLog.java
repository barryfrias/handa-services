package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "activityLog")
@JsonIgnoreProperties(ignoreUnknown=true)
public class ActivityLog
{
    private int rowNum;
    private String mobileNumber;
    private String type;
    private String message;
    private String activityDate;

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getActivityDate()
    {
        return activityDate;
    }

    public void setActivityDate(String activityDate)
    {
        this.activityDate = activityDate;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("rowNum", rowNum)
               .add("mobileNumber", mobileNumber)
               .add("message", message)
               .add("activityDate", activityDate)
               .toString();
    }
}