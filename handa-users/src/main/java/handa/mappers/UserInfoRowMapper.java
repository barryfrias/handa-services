package handa.mappers;

import static handa.core.ResultSetHelper.getString;
import handa.beans.dto.UserInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserInfoRowMapper
implements RowMapper<UserInfo>
{
    @Override
    public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setBloodType(getString(rs, "BLOOD_TYPE"));
        userInfo.setCurrentAddress(getString(rs, "CURRENT_ADDRESS"));
        userInfo.setDepartment(getString(rs, "DEPT"));
        userInfo.setImmediateHead(getString(rs, "IMMEDIATE_HEAD"));
        userInfo.setCompany(getString(rs, "COMPANY"));
        userInfo.setEmployeeNumber(getString(rs, "EMP_NO"));
        userInfo.setFirstName(getString(rs, "FIRST_NAME"));
        userInfo.setIceContactPerson(getString(rs, "ICE_CONTACT_PERSON"));
        userInfo.setIceLandlineNumber(getString(rs, "ICE_LANDLINE_NO"));
        userInfo.setIceMobileNumber(getString(rs, "ICE_MOBILE_NO"));
        userInfo.setLandlineNo(getString(rs, "LANDLINE_NO"));
        userInfo.setLastName(getString(rs, "LAST_NAME"));
        userInfo.setLatitude(getString(rs, "LATITUDE"));
        userInfo.setLongitude(getString(rs, "LONGITUDE"));
        userInfo.setMiddleName(getString(rs, "MIDDLE_NAME"));
        userInfo.setMobileNumber(getString(rs, "MOBILE_NO"));
        userInfo.setPermanentAddress(getString(rs, "PERMANENT_ADDRESS"));
        userInfo.setPosition(getString(rs, "POSITION"));
        userInfo.setRemarks(getString(rs, "REMARKS"));
        return userInfo;
    }
}