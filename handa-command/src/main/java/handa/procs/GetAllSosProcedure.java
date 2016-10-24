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

import handa.beans.dto.SosPrompt;
import handa.mappers.SosRowMapper;
import oracle.jdbc.OracleTypes;

public class GetAllSosProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetAllSosProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("get_all_sos");
        declareParameter(new SqlParameter("p_city", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_startdate", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_enddate", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new SosRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<SosPrompt> get(String city, String startDate, String endDate)
    {
        checkArgument(checkDate(startDate), "Invalid startDate");
        checkArgument(checkDate(endDate), "Invalid endDate");
        if(ALL.equalsIgnoreCase(city))
        {
            city = null;
        }
        Object[] params = new Object[] { city, startDate, endDate };
        Map<String, Object> map = execute(params);
        List<SosPrompt> list = (List<SosPrompt>) map.get(RESULT);
        return list;
    }
}