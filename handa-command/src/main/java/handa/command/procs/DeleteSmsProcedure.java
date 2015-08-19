package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class DeleteSmsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public DeleteSmsProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        setFunction(false);
        declareParameter(new SqlParameter("MSG_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("DELETEDBY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.NUMBER));
        compile();
    }

    public int delete(int id, String deletedBy)
    {
        checkNotNull(emptyToNull(deletedBy), "deletedBy can't be null");
        Object[] params =
        {
            id,
            deletedBy
        };
        Map<String, Object> map = execute(params);
        BigDecimal count = (BigDecimal) map.get(RESULT);
        return count.intValue();
    }
}