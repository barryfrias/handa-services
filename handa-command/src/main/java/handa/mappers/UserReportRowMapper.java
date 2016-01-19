package handa.mappers;

import handa.beans.dto.UserReport;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserReportRowMapper
implements RowMapper<UserReport>
{
    @Override
    public UserReport mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        UserReport userReport = new UserReport();
        userReport.setRowNum(rs.getInt("RNUM"));
        userReport.setBatteryLevel(rs.getString("BATTERY_LEVEL"));
        userReport.setCreatedDate(rs.getString("CREATED_DTTM"));
        userReport.setEventType(rs.getString("EVENT_TYPE"));
        userReport.setId(rs.getInt("ID"));
        userReport.setImageFilename(rs.getString("IMAGE_FILENAME"));
        userReport.setLatitude(rs.getString("LATITUDE"));
        userReport.setLongitude(rs.getString("LONGITUDE"));
        userReport.setLocationIndicator(rs.getString("LOCATION_INDICATOR"));
        userReport.setMacAddress(rs.getString("MAC_ADDRESS"));
        userReport.setMessage(rs.getString("MESSAGE"));
        userReport.setMobileNumber(rs.getString("MOBILE_NO"));
        userReport.setName(rs.getString("NAME"));
        userReport.setOsVersion(rs.getString("OS_VERSION"));
        userReport.setStatus(rs.getString("STATUS"));
        userReport.setAppVersion(rs.getString("APP_VERSION"));
        return userReport;
    }
}