package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.OracleTypes;

public class ReportsCountProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public ReportsCountProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_USER_REPORTS_COUNT");
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.NUMBER));
        setFunction(false);
        compile();
    }

    public int getReportsCount()
    {
        Map<String, Object> map = execute(new Object[0]);
        BigDecimal count = (BigDecimal) map.get(RESULT);
        return count.intValue();
    }
}