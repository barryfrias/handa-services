package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

public class SmsOutboundQueue
{
    private int id;
    private String mobileNo;
    private String message;
    private int outboxId;
    private String status;
    private String insertedDate;
    private String wsResponseMsg;
    private int rowNum;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getMobileNo()
    {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getOutboxId()
    {
        return outboxId;
    }

    public void setOutboxId(int outboxId)
    {
        this.outboxId = outboxId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getInsertedDate()
    {
        return insertedDate;
    }

    public void setInsertedDate(String insertedDate)
    {
        this.insertedDate = insertedDate;
    }

    public String getWsResponseMsg()
    {
        return wsResponseMsg;
    }

    public void setWsResponseMsg(String wsResponseMsg)
    {
        this.wsResponseMsg = wsResponseMsg;
    }

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("id", id)
               .add("mobileNo", mobileNo)
               .add("message", message)
               .add("outboxId", outboxId)
               .add("status", status)
               .add("insertedDate", insertedDate)
               .add("wsResponseMsg", wsResponseMsg)
               .add("rowNum", rowNum)
               .toString();
    }
}