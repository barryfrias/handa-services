package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reportInput")
public class ReportInput
{
    private String startDate;
    private String endDate;

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("startDate", startDate)
               .add("endDate", endDate)
               .toString();
    }
}