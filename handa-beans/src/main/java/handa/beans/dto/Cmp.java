package handa.beans.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cmps")
public class Cmp
{
    private int rowNum;
    private Long fileId;
    private String filename;
    private String name;
    private String username;
    private String company;
    private String description;
    private String uploadedBy;
    private String uploadedDate;

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    public Long getFileId()
    {
    	return fileId;
    }

    public void setFileId(Long fileId)
    {
    	this.fileId = fileId;
    }

    public String getFilename()
    {
    	return filename;
    }

    public void setFilename(String filename)
    {
    	this.filename = filename;
    }

    public String getName()
    {
    	return name;
    }

    public void setName(String name)
    {
    	this.name = name;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getDescription()
    {
    	return description;
    }

    public void setDescription(String description)
    {
    	this.description = description;
    }

    public String getUploadedBy()
    {
    	return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy)
    {
    	this.uploadedBy = uploadedBy;
    }

    public String getUploadedDate()
    {
    	return uploadedDate;
    }

    public void setUploadedDate(String uploadedDate)
    {
    	this.uploadedDate = uploadedDate;
    }
}