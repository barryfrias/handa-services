package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.OracleTypes;

public class ProcessSmsOutboxProcedure
extends StoredProcedure
{
    private static final String PROCESSED_CNT = "PROCESSED_CNT";

    public ProcessSmsOutboxProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        setFunction(false);
        declareParameter(new SqlOutParameter(PROCESSED_CNT, OracleTypes.NUMBER));
        compile();
    }

    public int call()
    {
        Map<String, Object> map = execute((Object)null);
        BigDecimal count = (BigDecimal) map.get(PROCESSED_CNT);
        return count.intValue();
    }
}