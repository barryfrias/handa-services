package handa.beans.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "outputMessage")
public class OutputMessage
{
    private String message;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public static OutputMessage instance(String message)
    {
        OutputMessage om = new OutputMessage();
        om.setMessage(message);
        return om;
    }
}