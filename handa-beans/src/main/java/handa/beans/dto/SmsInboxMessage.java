package handa.beans.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "smsInboxMessage")
public class SmsInboxMessage
{
    private int id;
    private String sender;
    private String mobileNumber;
    private String message;
    private String folder;
    private String readFlag;
    private String readBy;
    private String readDate;
    private String deletedFlag;
    private String deletedBy;
    private String deletedDate;
    private String msgDate;
    private String insertedDate;
    private int rowNum;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getFolder()
    {
        return folder;
    }

    public void setFolder(String folder)
    {
        this.folder = folder;
    }

    public String getReadFlag()
    {
        return readFlag;
    }

    public void setReadFlag(String readFlag)
    {
        this.readFlag = readFlag;
    }

    public String getReadBy()
    {
        return readBy;
    }

    public void setReadBy(String readBy)
    {
        this.readBy = readBy;
    }

    public String getReadDate()
    {
        return readDate;
    }

    public void setReadDate(String readDate)
    {
        this.readDate = readDate;
    }

    public String getDeletedFlag()
    {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag)
    {
        this.deletedFlag = deletedFlag;
    }

    public String getDeletedBy()
    {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy)
    {
        this.deletedBy = deletedBy;
    }

    public String getDeletedDate()
    {
        return deletedDate;
    }

    public void setDeletedDate(String deletedDate)
    {
        this.deletedDate = deletedDate;
    }

    public String getMsgDate()
    {
        return msgDate;
    }

    public void setMsgDate(String msgDate)
    {
        this.msgDate = msgDate;
    }

    public String getInsertedDate()
    {
        return insertedDate;
    }

    public void setInsertedDate(String insertedDate)
    {
        this.insertedDate = insertedDate;
    }

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }
}