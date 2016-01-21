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

import handa.beans.dto.Company;
import oracle.jdbc.OracleTypes;

public class GetCompaniesLovProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetCompaniesLovProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("LIST_COMPANIES");
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new CompanyRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Company> list()
    {
        Map<String, Object> map = execute((Object)null);
        List<Company> list = (List<Company>) map.get(RESULT);
        return list;
    }

    private static class CompanyRowMapper
    implements RowMapper<Company>
    {
        @Override
        public Company mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            Company company = new Company();
            company.setRowNum(++rowNum);
            company.setCode(rs.getString("CODE"));
            company.setName(rs.getString("NAME"));
            company.setWithLdap(rs.getInt("WITH_LDAP") == 1);
            return company;
        }
    }
}