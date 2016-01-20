package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.core.ToMapRowMapper;
import oracle.jdbc.OracleTypes;

public class GetCompaniesLovProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetCompaniesLovProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("LIST_COMPANIES");
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new ToMapRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, String>> list()
    {
        Map<String, Object> map = execute((Object)null);
        List<Map<String, String>> list = (List<Map<String, String>>) map.get(RESULT);
        return list;
    }
}