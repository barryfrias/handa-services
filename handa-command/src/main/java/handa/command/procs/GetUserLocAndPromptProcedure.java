package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static handa.config.HandaCommandConstants.ALL;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.UserLocation;
import handa.command.mappers.UserLocationRowMapper;
import oracle.jdbc.OracleTypes;

public class GetUserLocAndPromptProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetUserLocAndPromptProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_USERS_LOC_AND_PROMPT");
        declareParameter(new SqlParameter("CITY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new UserLocationRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<UserLocation> list(String city)
    {
        if(ALL.equalsIgnoreCase(city))
        {
            city = null;
        }
        Object[] params = new Object[] { city };
        Map<String, Object> map = execute(params);
        List<UserLocation> list = (List<UserLocation>) map.get(RESULT);
        return list;
    }
}