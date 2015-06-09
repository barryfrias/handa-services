package handa.command.procs;

import static handa.config.HandaCommandConstants.ALL;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class NoResponseCountProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public NoResponseCountProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        declareParameter(new SqlParameter("CITY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.NUMBER));
        setFunction(false);
        compile();
    }

    public int getCount(String city)
    {
        if(ALL.equalsIgnoreCase(city))
        {
            city = null;
        }
        Map<String, Object> map = execute(city);
        BigDecimal count = (BigDecimal) map.get(RESULT);
        return count.intValue();
    }
}