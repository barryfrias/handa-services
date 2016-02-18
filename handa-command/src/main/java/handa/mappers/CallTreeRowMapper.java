package handa.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.CallTree;
import handa.core.JsonUtils;

public class CallTreeRowMapper
implements RowMapper<CallTree>
{
    @SuppressWarnings("unchecked")
    @Override
    public CallTree mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        CallTree callTree = new CallTree();
        callTree.setRowNum(rowNum + 1);
        callTree.setId(rs.getInt("ID"));
        callTree.setName(rs.getString("NAME"));
        callTree.setJsonData(JsonUtils.objectify(rs.getClob("JSON_DATA").getCharacterStream(), Map.class));
        callTree.setModifiedBy(rs.getString("MODIFIED_BY"));
        callTree.setModifiedDate(rs.getString("MODIFIED_DTTM"));
        return callTree;
    }
}