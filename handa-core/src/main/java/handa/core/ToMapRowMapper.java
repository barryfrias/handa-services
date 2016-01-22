package handa.core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class ToMapRowMapper implements RowMapper<Map<String, Object>>
{
    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Map<String, Object> map = new LinkedHashMap<>();
        ResultSetMetaData metadata = rs.getMetaData();
        int colCount = metadata.getColumnCount();
        for(int i = 1; i <= colCount; i++)
        {
            String colName = metadata.getColumnName(i);
            map.put(colName, rs.getObject(colName));
        }
        map.put("rowNum", rowNum + 1);
        return map;
    }
}