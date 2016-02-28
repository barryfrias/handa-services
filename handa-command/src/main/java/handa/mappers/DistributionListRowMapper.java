package handa.mappers;

import java.sql.Array;
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
        obj.setId(rs.getLong("ID"));
        obj.setCode(rs.getString("CODE"));
        obj.setName(rs.getString("NAME"));
        Array array = rs.getArray("CUSTOM_LIST_VALUES");
        obj.setValues((array == null? null : (String[]) array.getArray()));
        obj.setModifiedBy(rs.getString("MODIFIED_BY"));
        obj.setModifiedDate(rs.getString("MODIFIED_DTTM"));
        return obj;
    }
}