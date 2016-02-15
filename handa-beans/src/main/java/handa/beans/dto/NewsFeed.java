package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "newsFeed")
@JsonIgnoreProperties(ignoreUnknown=true)
public class NewsFeed
{
    private int rowNum;
    private int id;
    private String title;
    private String message;
    private String imageFilename;
    private String messageType;
    private String createdDate;
    private String username;
    private String distributionListKey;
    private String[] distributionListValues;

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getImageFilename()
    {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename)
    {
        this.imageFilename = imageFilename;
    }

    public String getMessageType()
    {
        return messageType;
    }

    public void setMessageType(String messageType)
    {
        this.messageType = messageType;
    }

    public String getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getDistributionListKey()
    {
        return distributionListKey;
    }

    public void setDistributionListKey(String distributionListKey)
    {
        this.distributionListKey = distributionListKey;
    }

    public String[] getDistributionListValues()
    {
        return distributionListValues;
    }

    public void setDistributionListValues(String[] distributionListValues)
    {
        this.distributionListValues = distributionListValues;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("rowNum", rowNum)
                .add("id", id)
                .add("title", title)
                .add("message", message)
                .add("imageFilename", imageFilename)
                .add("messageType", messageType)
                .add("createdDate", createdDate)
                .add("username", username)
                .add("distributionListKey", distributionListKey)
                .add("distributionListValues", distributionListValues)
                .toString();
    }
}