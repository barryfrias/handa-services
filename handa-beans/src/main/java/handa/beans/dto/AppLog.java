package handa.beans.dto;

public class AppLog
{
   public static enum Source { SERVER, CLIENT }

    private Source source;
    private String username;
    private String mobileNumber;
    private String message;

    public static AppLog server(String username, String message)
    {
        return new AppLog(Source.SERVER, username, null, message);
    }

    public static AppLog client(String username, String mobileNumber, String message)
    {
        return new AppLog(Source.CLIENT, username, mobileNumber, message);
    }

    private AppLog(Source source, String username, String mobileNumber, String message)
    {
        this.source = source;
        this.username = (username == null? "N/A" : username);
        this.mobileNumber = (mobileNumber == null? "N/A" : mobileNumber);
        this.message = message;
    }

    public Source getSource()
    {
        return source;
    }
    public void setSource(Source source)
    {
        this.source = source;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
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
}