package handa.beans.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "subordinates")
public class Subordinates
{
    private int isManager;
    private List<SubDetails> subordinates;

    public int getIsManager()
    {
        return isManager;
    }

    public void setIsManager(int isManager)
    {
        this.isManager = isManager;
    }

    public List<SubDetails> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<SubDetails> subordinates) {
		this.subordinates = subordinates;
	}


	public static class SubDetails
    {
        private int rowNum;
        private String name;
        private String promptType;
        private String status;
        private String date;
        
		public int getRowNum() {
			return rowNum;
		}
		public void setRowNum(int rowNum) {
			this.rowNum = rowNum;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPromptType() {
			return promptType;
		}
		public void setPromptType(String promptType) {
			this.promptType = promptType;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
      
    }

}