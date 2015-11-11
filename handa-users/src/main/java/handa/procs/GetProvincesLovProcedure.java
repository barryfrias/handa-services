package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.Province;
import oracle.jdbc.OracleTypes;

public class GetProvincesLovProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetProvincesLovProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_PROVINCES_LOV");
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new ProvinceRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Province> list()
    {
        Map<String, Object> map = execute((Object)null);
        List<Province> list = (List<Province>) map.get(RESULT);
        return list;
    }

    private static class ProvinceRowMapper
    implements RowMapper<Province>
    {
        @Override
        public Province mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            Province province = new Province();
            province.setRowNum(++rowNum);
            province.setName(rs.getString("NAME"));
            return province;
        }
    }
}