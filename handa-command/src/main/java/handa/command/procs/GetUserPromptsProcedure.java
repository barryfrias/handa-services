package handa.command.procs;

import static handa.config.HandaCommandConstants.ALL;
import static com.google.common.base.Preconditions.checkNotNull;
import handa.beans.dto.UserPrompt;
import handa.command.mappers.UserPromptRowMapper;
import handa.config.HandaCommandConstants.PromptType;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class GetUserPromptsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetUserPromptsProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        declareParameter(new SqlParameter("PR_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("CITY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new UserPromptRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<UserPrompt> list(PromptType promptType, String city)
    {
        if(ALL.equalsIgnoreCase(city))
        {
            city = null;
        }
        Object[] params = new Object[] { promptType.toString(), city };
        Map<String, Object> map = execute(params);
        List<UserPrompt> list = (List<UserPrompt>) map.get(RESULT);
        return list;
    }
}