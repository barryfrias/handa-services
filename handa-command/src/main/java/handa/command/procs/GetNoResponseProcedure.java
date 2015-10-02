package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static handa.config.HandaCommandConstants.ALL;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.UserPrompt;
import handa.command.mappers.UserPromptRowMapper;
import oracle.jdbc.OracleTypes;

public class GetNoResponseProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetNoResponseProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_NO_RESPONSE");
        declareParameter(new SqlParameter("CITY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new UserPromptRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<UserPrompt> list(String city)
    {
        if(ALL.equalsIgnoreCase(city))
        {
            city = null;
        }
        Object[] params = new Object[] { city };
        Map<String, Object> map = execute(params);
        List<UserPrompt> list = (List<UserPrompt>) map.get(RESULT);
        return list == null? ImmutableList.<UserPrompt>of() : list;
    }
}