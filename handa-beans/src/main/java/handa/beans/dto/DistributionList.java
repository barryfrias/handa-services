package handa.beans.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "distributionList")
public class DistributionList
{
    private long id;
    private String code;
    private String name;
    private String[] values;
    private String modifiedBy;
    private String modifiedDate;

    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String[] getValues()
    {
        return values;
    }
    public void setValues(String[] values)
    {
        this.values = values;
    }
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    public String getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(String modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
}