package handa.mappers;

import handa.beans.dto.DashboardFilter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DashboardFilterRowMapper
implements RowMapper<DashboardFilter>
{
    @Override
    public DashboardFilter mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DashboardFilter dashboardFilter = new DashboardFilter();
        dashboardFilter.setRowNum(++rowNum);
        dashboardFilter.setName(rs.getString("NAME"));
        return dashboardFilter;
    }
}