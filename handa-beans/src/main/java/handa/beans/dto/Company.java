package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "company")
public class Company
{
    private int rowNum;
    private String code;
    private String name;
    private boolean withLdap;

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

    public boolean isWithLdap()
    {
        return withLdap;
    }

    public void setWithLdap(boolean withLdap)
    {
        this.withLdap = withLdap;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("rowNum", rowNum)
                .add("code", code)
                .add("name", name)
                .add("withLdap", withLdap)
                .toString();
    }
}