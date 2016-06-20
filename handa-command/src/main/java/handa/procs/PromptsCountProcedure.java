package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static handa.config.HandaCommandConstants.ALL;
import static handa.core.HandaUtils.checkDate;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableMap;

import oracle.jdbc.OracleTypes;

public class PromptsCountProcedure
extends StoredProcedure
{
    private static final String SOS_CNT = "SOS_CNT";
    private static final String SAFE_CNT = "SAFE_CNT";
    private static final String NR_CNT = "NR_CNT";

    public PromptsCountProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_DASHBOARD.GET_PROMPT_COUNT");
        declareParameter(new SqlParameter("CITY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_STARTDATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_ENDDATE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(SOS_CNT, OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(SAFE_CNT, OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(NR_CNT, OracleTypes.NUMBER));
        setFunction(false);
        compile();
    }

    public Map<String, Integer> getPromptCount(String city, String startDate, String endDate)
    {
        checkArgument(checkDate(startDate), "Invalid startDate");
        checkArgument(checkDate(endDate), "Invalid endDate");
        if(ALL.equalsIgnoreCase(city))
        {
            city = null;
        }
        Map<String, Object> map = execute(city, startDate, endDate);
        int sosCount = ((BigDecimal) map.get(SOS_CNT)).intValue(),
            safeCount = ((BigDecimal) map.get(SAFE_CNT)).intValue(),
            noResponseCount = ((BigDecimal) map.get(NR_CNT)).intValue();
        return ImmutableMap.of("sosCount", sosCount, "safeCount", safeCount, "noResponseCount", noResponseCount);
    }
}