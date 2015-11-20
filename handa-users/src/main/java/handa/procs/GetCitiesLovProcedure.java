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

import handa.beans.dto.City;
import oracle.jdbc.OracleTypes;

public class GetCitiesLovProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetCitiesLovProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_CITIES_LOV");
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new CityRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<City> list()
    {
        Map<String, Object> map = execute((Object)null);
        List<City> list = (List<City>) map.get(RESULT);
        return list;
    }

    private static class CityRowMapper
    implements RowMapper<City>
    {
        @Override
        public City mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            City city = new City();
            city.setRowNum(++rowNum);
            city.setName(rs.getString("NAME"));
            return city;
        }
    }
}