package handa.beans.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "callTree")
public class CallTree
{
    private Long id;
    private String name;
    private String modifiedBy;
    private String modifiedDate;
    private int rowNum;
    private Map<String, Object> jsonData;

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
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
    public int getRowNum()
    {
        return rowNum;
    }
    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }
    public Map<String, Object> getJsonData()
    {
        return jsonData;
    }
    public void setJsonData(Map<String, Object> jsonData)
    {
        this.jsonData = jsonData;
    }
}