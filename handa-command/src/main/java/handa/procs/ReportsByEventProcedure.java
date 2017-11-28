package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static handa.core.HandaUtils.checkDate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.EventReport;
import handa.beans.dto.EventReport.Details;
import handa.beans.dto.EventReport.StatsByBgy;
import handa.beans.dto.EventReport.StatsByCity;
import handa.beans.dto.EventReport.StatsByCompany;
import handa.beans.dto.EventReport.StatsByDept;
import handa.beans.dto.EventReport.StatsByProvince;
import handa.beans.dto.EventReport.StatsByType;
import handa.beans.dto.ReportInput;
import oracle.jdbc.OracleTypes;

public class ReportsByEventProcedure
extends StoredProcedure
{
    private static final String p_out = "P_OUT";
    private static final String p_out_comp = "P_OUT_COMP";
    private static final String p_out_dept = "P_OUT_DEPT";
    private static final String p_out_prov = "P_OUT_PROV";
    private static final String p_out_city = "P_OUT_CITY";
    private static final String p_out_bgy = "P_OUT_BGY";
    private static final String p_out_type = "P_OUT_TYPE";

    public ReportsByEventProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_REPORTS.BY_EVENT");
        declareParameter(new SqlParameter("p_startdate", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_enddate", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_unique", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(p_out, OracleTypes.CURSOR, new EventDetailsRowMapper()));
        declareParameter(new SqlOutParameter(p_out_comp, OracleTypes.CURSOR, new EventStatsByCompanyRowMapper()));
        declareParameter(new SqlOutParameter(p_out_dept, OracleTypes.CURSOR, new EventStatsByDeptRowMapper()));
        declareParameter(new SqlOutParameter(p_out_prov, OracleTypes.CURSOR, new EventStatsByProvinceRowMapper()));
        declareParameter(new SqlOutParameter(p_out_city, OracleTypes.CURSOR, new EventStatsByCityRowMapper()));
        declareParameter(new SqlOutParameter(p_out_bgy, OracleTypes.CURSOR, new EventStatsByBgyRowMapper()));
        declareParameter(new SqlOutParameter(p_out_type, OracleTypes.CURSOR, new EventStatsByTypeRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public EventReport get(ReportInput reportInput)
    {
        checkNotNull(reportInput, "reportInput should not be null");
        checkArgument(checkDate(reportInput.getStartDate()), "Invalid startDate");
        checkArgument(checkDate(reportInput.getEndDate()), "Invalid endDate");
        Object[] params = new Object[]
        {
            reportInput.getStartDate(),
            reportInput.getEndDate(),
            reportInput.isUnique()? 1 : 0
        };

        Map<String, Object> map = execute(params);
        EventReport report = new EventReport();
        List<Details> details = (List<Details>) map.get(p_out);
        report.setDetails((List<Details>) map.get(p_out));
        report.setTotal(details.size());
        report.setStatsByCompany((List<StatsByCompany>) map.get(p_out_comp));
        report.setStatsByDept((List<StatsByDept>) map.get(p_out_dept));
        report.setStatsByProvince((List<StatsByProvince>) map.get(p_out_prov));
        report.setStatsByCity((List<StatsByCity>) map.get(p_out_city));
        report.setStatsByBgy((List<StatsByBgy>) map.get(p_out_bgy));
        report.setStatsByType((List<StatsByType>) map.get(p_out_type));
        return report;
    }

    private class EventDetailsRowMapper implements RowMapper<EventReport.Details>
    {
        @Override
        public Details mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            Details details = new Details();
            details.setRowNum(++rowNum);
            details.setName(rs.getString("NAME"));
            details.setMobileNumber(rs.getString("MOBILE_NO"));
            details.setLandlineNumber(rs.getString("LANDLINE_NO"));
            details.setPosition(rs.getString("POSITION"));
            details.setDept(rs.getString("DEPT"));
            details.setImmediateHead(rs.getString("IMMEDIATE_HEAD"));
            details.setCompany(rs.getString("COMPANY"));
            details.setType(rs.getString("TYPE"));
            details.setLocationIndicator(rs.getString("LOCATION_INDICATOR"));
            details.setActualLatitude(rs.getString("ACT_LATITUDE"));
            details.setActualLongitude(rs.getString("ACT_LONGITUDE"));
            details.setActualProvince(rs.getString("ACT_PROVINCE"));
            details.setActualCity(rs.getString("ACT_CITY"));
            details.setActualBarangay(rs.getString("ACT_BARANGAY"));
            details.setActualAddress(rs.getString("ACT_ADDRESS"));
            details.setRegisteredLatitude(rs.getString("REG_LATITUDE"));
            details.setRegisteredLongitude(rs.getString("REG_LONGITUDE"));
            details.setRegisteredProvince(rs.getString("REG_PROVINCE"));
            details.setRegisteredCity(rs.getString("REG_CITY"));
            details.setRegisteredBarangay(rs.getString("REG_BARANGAY"));
            details.setRegisteredAddress(rs.getString("REG_ADDRESS"));
            details.setIceContactPerson(rs.getString("ICE_CONTACT_PERSON"));
            details.setIceLandlineNumber(rs.getString("ICE_LANDLINE_NO"));
            details.setIceMobileNumber(rs.getString("ICE_MOBILE_NO"));
            details.setBloodType(rs.getString("BLOOD_TYPE"));
            details.setDate(rs.getString("DATE"));
            return details;
        }
    }

    private class EventStatsByCompanyRowMapper implements RowMapper<StatsByCompany>
    {
        @Override
        public StatsByCompany mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            StatsByCompany statsByCompany = new StatsByCompany();
            statsByCompany.setName(rs.getString("COMPANY"));
            statsByCompany.setCount(rs.getInt("COUNT"));
            return statsByCompany;
        }
    }

    private class EventStatsByDeptRowMapper implements RowMapper<StatsByDept>
    {
        @Override
        public StatsByDept mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            StatsByDept statsByDept = new StatsByDept();
            statsByDept.setName(rs.getString("DEPT"));
            statsByDept.setCount(rs.getInt("COUNT"));
            return statsByDept;
        }
    }

    private class EventStatsByProvinceRowMapper implements RowMapper<StatsByProvince>
    {
        @Override
        public StatsByProvince mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            StatsByProvince statsByProvince = new StatsByProvince();
            statsByProvince.setName(rs.getString("PROVINCE"));
            statsByProvince.setCount(rs.getInt("COUNT"));
            return statsByProvince;
        }
    }

    private class EventStatsByCityRowMapper implements RowMapper<StatsByCity>
    {
        @Override
        public StatsByCity mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            StatsByCity statsByCity = new StatsByCity();
            statsByCity.setName(rs.getString("CITY"));
            statsByCity.setCount(rs.getInt("COUNT"));
            return statsByCity;
        }
    }

    private class EventStatsByBgyRowMapper implements RowMapper<StatsByBgy>
    {
        @Override
        public StatsByBgy mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            StatsByBgy statsByBgy = new StatsByBgy();
            statsByBgy.setName(rs.getString("BGY"));
            statsByBgy.setCount(rs.getInt("COUNT"));
            return statsByBgy;
        }
    }

    private class EventStatsByTypeRowMapper implements RowMapper<StatsByType>
    {
        @Override
        public StatsByType mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            StatsByType statsByType = new StatsByType();
            statsByType.setName(rs.getString("TYPE"));
            statsByType.setCount(rs.getInt("COUNT"));
            return statsByType;
        }
    }
}