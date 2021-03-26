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

public class UpdateSosProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public UpdateSosProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("update_sos");
        setFunction(false);
        declareParameter(new SqlParameter("p_id", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("p_reason", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_updated_by", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        compile();
    }

    public String update(int id, ClosePrompt closePrompt)
    {
        checkNotNull(closePrompt, "closePrompt object can't be null");
        Object[] params =
        {
            id,
            checkNotNull(emptyToNull(closePrompt.getReason()), "reason can't be null"),
            checkNotNull(emptyToNull(closePrompt.getUsername()), "username can't be null")
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}