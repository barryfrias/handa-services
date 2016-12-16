package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static handa.core.HandaUtils.checkDate;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import handa.core.ToMapRowMapper;
import oracle.jdbc.OracleTypes;

public class GetSubordinatesProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetSubordinatesProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_SUBORDINATES.GET_SUBORDINATES");
        declareParameter(new SqlParameter("P_USERNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_START_DATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_END_DATE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new ToMapRowMapper()));
        setFunction(false);
        compile();
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> list(String mgrUsername, String startDate, String endDate)
    {
    	checkArgument(checkDate(startDate), "Invalid startDate");
        checkArgument(checkDate(endDate), "Invalid endDate");
        
        Object[] params = new Object[] { mgrUsername, startDate, endDate };
        Map<String, Object> map = execute(params);
        return (List<Map<String, Object>>) map.get(RESULT);
    }
    
}