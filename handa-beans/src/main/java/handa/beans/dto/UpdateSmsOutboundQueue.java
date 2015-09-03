package handa.beans.dto;

public class UpdateSmsOutboundQueue
{
    private int queueId;
    private String updateStatus;
    private String responseMessage;

    public int getQueueId()
    {
        return queueId;
    }
    public void setQueueId(int queueId)
    {
        this.queueId = queueId;
    }
    public String getUpdateStatus()
    {
        return updateStatus;
    }
    public void setUpdateStatus(String updateStatus)
    {
        this.updateStatus = updateStatus;
    }
    public String getResponseMessage()
    {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage)
    {
        this.responseMessage = responseMessage;
    }
}