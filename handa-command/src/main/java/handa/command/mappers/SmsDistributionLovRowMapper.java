package handa.command.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.LovItem;

public class SmsDistributionLovRowMapper
implements RowMapper<LovItem>
{
    @Override
    public LovItem mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        LovItem item = new LovItem();
        item.setRowNum(rowNum + 1);
        item.setCode(rs.getString("CODE"));
        item.setName(rs.getString("NAME"));
        return item;
    }
}