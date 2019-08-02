package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.Subordinates;
import handa.core.ToMapRowMapper;
import oracle.jdbc.OracleTypes;

public class GetSubordinatesProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";
    private static final String isManager = "P_OUT_IS_MANAGER";

    public GetSubordinatesProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_SUBORDINATES.GET_SUBORDINATES");
        declareParameter(new SqlParameter("P_USERNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_COMPANY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_START_DATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_END_DATE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new ToMapRowMapper()));
        declareParameter(new SqlOutParameter("P_OUT_IS_MANAGER", OracleTypes.NUMBER));
        setFunction(false);
        compile();
    }
    
    @SuppressWarnings("unchecked")
    public Subordinates subordinatesList(String mgrUsername, String company, String startDate, String endDate)
    {
        
    	Object[] params = new Object[] { mgrUsername, company, startDate, endDate };
        Map<String, Object> map = execute(params);
        Subordinates subordinates = new Subordinates();
        BigDecimal ismanager = (BigDecimal) map.get(isManager);
        subordinates.setSubordinates((List<Map<String, Object>>) map.get(RESULT));
        subordinates.setIsManager(ismanager.intValue());
        return subordinates;
    }
    
}