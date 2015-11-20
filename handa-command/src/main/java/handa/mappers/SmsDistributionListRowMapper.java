package handa.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.SmsDistributionList;

public class SmsDistributionListRowMapper
implements RowMapper<SmsDistributionList>
{
    @Override
    public SmsDistributionList mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        SmsDistributionList obj = new SmsDistributionList();
        obj.setCode(rs.getString("CODE"));
        obj.setName(rs.getString("NAME"));
        return obj;
    }
}