package handa.mappers;

import handa.beans.dto.UserLocation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserLocationRowMapper
implements RowMapper<UserLocation>
{
    @Override
    public UserLocation mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        UserLocation userLoc = new UserLocation();
        userLoc.setName(rs.getString("NAME"));
        userLoc.setMobileNumber(rs.getString("MOBILE_NO"));
        userLoc.setLatitude(rs.getString("LATITUDE"));
        userLoc.setLongitude(rs.getString("LONGITUDE"));
        userLoc.setPromptType(rs.getString("PROMPT_TYPE"));
        userLoc.setCreatedDate(rs.getString("CREATED_DTTM"));
        return userLoc;
    }
}