package handa.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.DistributionList;

public class DistributionListRowMapper
implements RowMapper<DistributionList>
{
    @Override
    public DistributionList mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DistributionList obj = new DistributionList();
        obj.setCode(rs.getString("CODE"));
        obj.setName(rs.getString("NAME"));
        return obj;
    }
}