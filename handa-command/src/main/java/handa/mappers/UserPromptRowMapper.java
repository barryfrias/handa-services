package handa.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import handa.beans.dto.UserPrompt;

import org.springframework.jdbc.core.RowMapper;

public class UserPromptRowMapper
implements RowMapper<UserPrompt>
{
    @Override
    public UserPrompt mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        UserPrompt userPrompt = new UserPrompt();
        userPrompt.setRowNum(++rowNum);
        userPrompt.setName(rs.getString("NAME"));
        userPrompt.setCompany(rs.getString("COMPANY"));
        userPrompt.setBatteryLevel(rs.getString("BATTERY_LEVEL"));
        userPrompt.setCreatedDate(rs.getString("CREATED_DTTM"));
        userPrompt.setId(rs.getInt("ID"));
        userPrompt.setLatitude(rs.getString("LATITUDE"));
        userPrompt.setLongitude(rs.getString("LONGITUDE"));
        userPrompt.setLocationIndicator(rs.getString("LOCATION_INDICATOR"));
        userPrompt.setMobileNumber(rs.getString("MOBILE_NO"));
        userPrompt.setDeviceInfo(rs.getString("DEVICE_INFO"));
        userPrompt.setPromptType(rs.getString("PROMPT_TYPE"));
        userPrompt.setStatus(rs.getString("STATUS"));
        userPrompt.setModifiedBy(rs.getString("MODIFIED_BY"));
        userPrompt.setRemarks(rs.getString("REMARKS"));
        return userPrompt;
    }
}