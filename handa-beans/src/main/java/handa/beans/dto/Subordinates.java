package handa.beans.dto;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "subordinates")
public class Subordinates
{
    private int isManager;
    private List<Map<String, Object>> subordinates;

    public int getIsManager()
    {
        return isManager;
    }

    public void setIsManager(int isManager)
    {
        this.isManager = isManager;
    }

    public List<Map<String, Object>> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<Map<String, Object>> subordinates) {
		this.subordinates = subordinates;
	}
}