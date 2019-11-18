package handa.beans.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "eventReport")
public class EventReport
{
    private int total;
    private List<Details> details;
    private List<StatsByCompany> statsByCompany;
    private List<StatsByDept> statsByDept;
    private List<StatsByProvince> statsByProvince;
    private List<StatsByCity> StatsByCity;
    private List<StatsByBgy> statsByBgy;
    private List<StatsByType> statsByType;

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public List<Details> getDetails()
    {
        return details;
    }

    public void setDetails(List<Details> details)
    {
        this.details = details;
    }

    public List<StatsByCompany> getStatsByCompany()
    {
        return statsByCompany;
    }

    public void setStatsByCompany(List<StatsByCompany> statsByCompany)
    {
        this.statsByCompany = statsByCompany;
    }

    public List<StatsByDept> getStatsByDept()
    {
        return statsByDept;
    }

    public void setStatsByDept(List<StatsByDept> statsByDept)
    {
        this.statsByDept = statsByDept;
    }

    public List<StatsByProvince> getStatsByProvince()
    {
        return statsByProvince;
    }

    public void setStatsByProvince(List<StatsByProvince> statsByProvince)
    {
        this.statsByProvince = statsByProvince;
    }

    public List<StatsByCity> getStatsByCity()
    {
        return StatsByCity;
    }

    public void setStatsByCity(List<StatsByCity> statsByCity)
    {
        StatsByCity = statsByCity;
    }

    public List<StatsByBgy> getStatsByBgy()
    {
        return statsByBgy;
    }

    public void setStatsByBgy(List<StatsByBgy> statsByBgy)
    {
        this.statsByBgy = statsByBgy;
    }

    public List<StatsByType> getStatsByType()
    {
        return statsByType;
    }

    public void setStatsByType(List<StatsByType> statsByType)
    {
        this.statsByType = statsByType;
    }

    public static class Details
    {
        private int rowNum;
        private String name;
        private String mobileNumber;
        private String landlineNumber;
        private String position;
        private String dept;
        private String group;
        private String immediateHead;
        private String officeLocation;
        private String company;
        private String type;
        private String locationIndicator;
        private String actualLatitude;
        private String actualLongitude;
        private String actualProvince;
        private String actualCity;
        private String actualBarangay;
        private String actualAddress;
        private String registeredLatitude;
        private String registeredLongitude;
        private String registeredProvince;
        private String registeredCity;
        private String registeredBarangay;
        private String registeredAddress;
        private String iceContactPerson;
        private String iceLandlineNumber;
        private String iceMobileNumber;
        private String bloodType;
        private String date;
        public int getRowNum()
        {
            return rowNum;
        }
        public void setRowNum(int rowNum)
        {
            this.rowNum = rowNum;
        }
        public String getName()
        {
            return name;
        }
        public void setName(String name)
        {
            this.name = name;
        }
        public String getMobileNumber()
        {
            return mobileNumber;
        }
        public void setMobileNumber(String mobileNumber)
        {
            this.mobileNumber = mobileNumber;
        }
        public String getLandlineNumber()
        {
            return landlineNumber;
        }
        public void setLandlineNumber(String landlineNumber)
        {
            this.landlineNumber = landlineNumber;
        }
        public String getPosition()
        {
            return position;
        }
        public void setPosition(String position)
        {
            this.position = position;
        }
        public String getDept()
        {
            return dept;
        }
        public void setDept(String dept)
        {
            this.dept = dept;
        }
        public String getGroup()
        {
            return group;
        }
        public void setGroup(String group)
        {
            this.group = group;
        }
        public String getImmediateHead()
        {
            return immediateHead;
        }
        public void setImmediateHead(String immediateHead)
        {
            this.immediateHead = immediateHead;
        }
        public String getOfficeLocation()
        {
            return officeLocation;
        }
        public void setOfficeLocation(String officeLocation)
        {
            this.officeLocation = officeLocation;
        }
        public String getCompany()
        {
            return company;
        }
        public void setCompany(String company)
        {
            this.company = company;
        }
        public String getType()
        {
            return type;
        }
        public void setType(String type)
        {
            this.type = type;
        }
        public String getLocationIndicator()
        {
            return locationIndicator;
        }
        public void setLocationIndicator(String locationIndicator)
        {
            this.locationIndicator = locationIndicator;
        }
        public String getActualLatitude()
        {
            return actualLatitude;
        }
        public void setActualLatitude(String actualLatitude)
        {
            this.actualLatitude = actualLatitude;
        }
        public String getActualLongitude()
        {
            return actualLongitude;
        }
        public void setActualLongitude(String actualLongitude)
        {
            this.actualLongitude = actualLongitude;
        }
        public String getActualProvince()
        {
            return actualProvince;
        }
        public void setActualProvince(String actualProvince)
        {
            this.actualProvince = actualProvince;
        }
        public String getActualCity()
        {
            return actualCity;
        }
        public void setActualCity(String actualCity)
        {
            this.actualCity = actualCity;
        }
        public String getActualBarangay()
        {
            return actualBarangay;
        }
        public void setActualBarangay(String actualBarangay)
        {
            this.actualBarangay = actualBarangay;
        }
        public String getActualAddress()
        {
            return actualAddress;
        }
        public void setActualAddress(String actualAddress)
        {
            this.actualAddress = actualAddress;
        }
        public String getRegisteredLatitude()
        {
            return registeredLatitude;
        }
        public void setRegisteredLatitude(String registeredLatitude)
        {
            this.registeredLatitude = registeredLatitude;
        }
        public String getRegisteredLongitude()
        {
            return registeredLongitude;
        }
        public void setRegisteredLongitude(String registeredLongitude)
        {
            this.registeredLongitude = registeredLongitude;
        }
        public String getRegisteredProvince()
        {
            return registeredProvince;
        }
        public void setRegisteredProvince(String registeredProvince)
        {
            this.registeredProvince = registeredProvince;
        }
        public String getRegisteredCity()
        {
            return registeredCity;
        }
        public void setRegisteredCity(String registeredCity)
        {
            this.registeredCity = registeredCity;
        }
        public String getRegisteredBarangay()
        {
            return registeredBarangay;
        }
        public void setRegisteredBarangay(String registeredBarangay)
        {
            this.registeredBarangay = registeredBarangay;
        }
        public String getRegisteredAddress()
        {
            return registeredAddress;
        }
        public void setRegisteredAddress(String registeredAddress)
        {
            this.registeredAddress = registeredAddress;
        }
        public String getIceContactPerson()
        {
            return iceContactPerson;
        }
        public void setIceContactPerson(String iceContactPerson)
        {
            this.iceContactPerson = iceContactPerson;
        }
        public String getIceLandlineNumber()
        {
            return iceLandlineNumber;
        }
        public void setIceLandlineNumber(String iceLandlineNumber)
        {
            this.iceLandlineNumber = iceLandlineNumber;
        }
        public String getIceMobileNumber()
        {
            return iceMobileNumber;
        }
        public void setIceMobileNumber(String iceMobileNumber)
        {
            this.iceMobileNumber = iceMobileNumber;
        }
        public String getBloodType()
        {
            return bloodType;
        }
        public void setBloodType(String bloodType)
        {
            this.bloodType = bloodType;
        }
        public String getDate()
        {
            return date;
        }
        public void setDate(String date)
        {
            this.date = date;
        }
    }

    private static abstract class EventStats
    {
        private String name;
        private int count;
        protected String getName()
        {
            return name;
        }
        public void setName(String name)
        {
            this.name = name;
        }
        public int getCount()
        {
            return count;
        }
        public void setCount(int count)
        {
            this.count = count;
        }
    }

    public static class StatsByCompany extends EventStats
    {
        @JsonProperty("company")
        public String getName() { return super.getName(); }
    }

    public static class StatsByDept extends EventStats
    {
        @JsonProperty("dept")
        public String getName() { return super.getName(); }
    }

    public static class StatsByProvince extends EventStats
    {
        @JsonProperty("province")
        public String getName() { return super.getName(); }
    }

    public static class StatsByCity extends EventStats
    {
        @JsonProperty("city")
        public String getName() { return super.getName(); }
    }

    public static class StatsByBgy extends EventStats
    {
        @JsonProperty("barangay")
        public String getName() { return super.getName(); }
    }

    public static class StatsByType extends EventStats
    {
        @JsonProperty("type")
        public String getName() { return super.getName(); }
    }
}