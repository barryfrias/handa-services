package handa.mappers;

import handa.beans.dto.City;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CityRowMapper
implements RowMapper<City>
{
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        City city = new City();
        city.setName(rs.getString("NAME"));
        return city;
    }
}