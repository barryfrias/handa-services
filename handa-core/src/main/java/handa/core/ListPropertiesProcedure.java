package handa.core;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import oracle.jdbc.OracleTypes;

public class ListPropertiesProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public ListPropertiesProcedure(DataSource dataSource)
    {
        setSql("LIST_PROPERTIES");
        setDataSource(checkNotNull(dataSource));
        setFunction(false);
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new PropertyMapper()));
        compile();
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> propertiesMap()
    {
        Object[] params = new Object[] {};
        Map<String, Object> map = execute(params);
        List<String[]> properties = (List<String[]>) map.get(RESULT);
        Builder<String, String> builder = ImmutableMap.builder();
        for(String[] row : properties)
        {
            builder.put(row[0], row[1]);
        }
        return builder.build();
    }

    private static class PropertyMapper implements RowMapper<String[]>
    {
        @Override
        public String[] mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            String[] row = new String[2];
            row[0] = rs.getString("KEY");
            row[1] = rs.getString("VALUE");
            return row;
        }
    }
}