package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import handa.beans.dto.ClosePrompt;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ClosePromptProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public ClosePromptProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        setFunction(false);
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("REF_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("REASON", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("CLOSED_BY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.NUMBER));
        compile();
    }

    public int closePrompt(int id, ClosePrompt closePrompt)
    {
        checkNotNull(closePrompt, "closePrompt object can't be null");
        Object[] params =
        {
            id,
            closePrompt.getReferenceNumber(),
            closePrompt.getReason(),
            checkNotNull(emptyToNull(closePrompt.getUsername()), "closePrompt.username can't be null")
        };
        Map<String, Object> map = execute(params);
        BigDecimal count = (BigDecimal) map.get(RESULT);
        return count.intValue();
    }
}