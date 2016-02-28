package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "distributionCustomGroup")
public class DistributionCustomGroup
{
    private Long id;
    private String name;
    private String[] values;
    private String modifiedBy;

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

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("id", id)
               .add("name", name)
               .add("values", values)
               .add("modifiedBy", modifiedBy)
               .toString();
    }
}