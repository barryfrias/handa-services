package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reportInput")
public class ReportInput
{
    private String startDate;
    private String endDate;
    private boolean unique;

    
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

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	
    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("startDate", startDate)
               .add("endDate", endDate)
               .add("unique", unique)
               .toString();
    }

}