package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static handa.config.HandaCommandConstants.ALL;
import static handa.core.HandaUtils.checkDate;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.UserLocation;
import handa.mappers.UserLocationRowMapper;
import oracle.jdbc.OracleTypes;

public class GetUserLocAndPromptProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetUserLocAndPromptProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_DASHBOARD.GET_USERS_LOC_AND_PROMPT");
        declareParameter(new SqlParameter("CITY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_STARTDATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_ENDDATE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new UserLocationRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<UserLocation> list(String city, String startDate, String endDate)
    {
        checkArgument(checkDate(startDate), "Invalid startDate");
        checkArgument(checkDate(endDate), "Invalid endDate");
        if(ALL.equalsIgnoreCase(city))
        {
            city = null;
        }
        Object[] params = new Object[] { city, startDate, endDate };
        Map<String, Object> map = execute(params);
        List<UserLocation> list = (List<UserLocation>) map.get(RESULT);
        return list;
    }
}