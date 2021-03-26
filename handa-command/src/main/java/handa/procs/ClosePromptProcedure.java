package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.ClosePrompt;
import oracle.jdbc.OracleTypes;

public class ClosePromptProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public ClosePromptProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("close_prompt");
        setFunction(false);
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("REASON", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("CLOSED_BY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        compile();
    }

    public String closePrompt(int id, ClosePrompt closePrompt)
    {
        checkNotNull(closePrompt, "closePrompt object can't be null");
        Object[] params =
        {
            id,
            closePrompt.getReason(),
            checkNotNull(emptyToNull(closePrompt.getUsername()), "closePrompt.username can't be null")
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}