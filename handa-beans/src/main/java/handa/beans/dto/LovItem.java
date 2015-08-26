package handa.beans.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lovItem")
public class LovItem
{
    private int rowNum;
    private String code;
    private String name;

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
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
}