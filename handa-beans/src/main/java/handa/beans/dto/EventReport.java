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
        private String immediateHead;
        private String company;
        private String currentAddress;
        private String province;
        private String city;
        private String barangay;
        private String latitude;
        private String longitude;
        private String locationIndicator;
        private String type;
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
        public String getImmediateHead()
        {
            return immediateHead;
        }
        public void setImmediateHead(String immediateHead)
        {
            this.immediateHead = immediateHead;
        }
        public String getCompany()
        {
            return company;
        }
        public void setCompany(String company)
        {
            this.company = company;
        }
        public String getCurrentAddress()
        {
            return currentAddress;
        }
        public void setCurrentAddress(String currentAddress)
        {
            this.currentAddress = currentAddress;
        }
        public String getProvince()
        {
            return province;
        }
        public void setProvince(String province)
        {
            this.province = province;
        }
        public String getCity()
        {
            return city;
        }
        public void setCity(String city)
        {
            this.city = city;
        }
        public String getBarangay()
        {
            return barangay;
        }
        public void setBarangay(String barangay)
        {
            this.barangay = barangay;
        }
        public String getLatitude()
        {
            return latitude;
        }
        public void setLatitude(String latitude)
        {
            this.latitude = latitude;
        }
        public String getLongitude()
        {
            return longitude;
        }
        public void setLongitude(String longitude)
        {
            this.longitude = longitude;
        }
        public String getLocationIndicator()
        {
            return locationIndicator;
        }
        public void setLocationIndicator(String locationIndicator)
        {
            this.locationIndicator = locationIndicator;
        }
        public String getType()
        {
            return type;
        }
        public void setType(String type)
        {
            this.type = type;
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