package handa.beans.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "promptCount")
public class PromptCount
{
    private String city;
    private int count;

    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public int getCount()
    {
        return count;
    }
    public void setCount(int count)
    {
        this.count = count;
    }
}