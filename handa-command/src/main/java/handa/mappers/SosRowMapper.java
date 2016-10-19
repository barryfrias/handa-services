package handa.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.SosPrompt;

public class SosRowMapper
implements RowMapper<SosPrompt>
{
    @Override
    public SosPrompt mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        SosPrompt sos = new SosPrompt();
        sos.setRowNum(++rowNum);
        sos.setName(rs.getString("NAME"));
        sos.setCompany(rs.getString("COMPANY"));
        sos.setBatteryLevel(rs.getString("BATTERY_LEVEL"));
        sos.setCreatedDate(rs.getString("CREATED_DTTM"));
        sos.setId(rs.getInt("ID"));
        sos.setLatitude(rs.getString("LATITUDE"));
        sos.setLongitude(rs.getString("LONGITUDE"));
        sos.setLocationIndicator(rs.getString("LOCATION_INDICATOR"));
        sos.setMobileNumber(rs.getString("MOBILE_NO"));
        sos.setDeviceInfo(rs.getString("DEVICE_INFO"));
        sos.setPromptType(rs.getString("PROMPT_TYPE"));
        sos.setStatus(rs.getString("STATUS"));
        sos.setReferenceNumber(rs.getString("REF_NO"));
        sos.setRemarks(rs.getString("REASON"));
        return sos;
    }
}