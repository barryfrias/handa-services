package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
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
    private static final String SOS_CNT = "sos_cnt";
    private static final String SAFE_CNT = "safe_cnt";
    private static final String NR_CNT = "nr_cnt";
    private static final String SOS_ALARM_CNT = "sos_alarm_cnt";

    public PromptsCountProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("handa_dashboard.get_prompt_count");
        declareParameter(new SqlParameter("p_cty", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_head", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_dept", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_comp", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_startdate", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_enddate", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(SOS_CNT, OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(SAFE_CNT, OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(NR_CNT, OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(SOS_ALARM_CNT, OracleTypes.NUMBER));
        setFunction(false);
        compile();
    }

    public Map<String, Integer> getPromptCount(String cty, String head, String dept, String comp, String startDate, String endDate)
    {
        checkArgument(checkDate(startDate), "Invalid startDate");
        checkArgument(checkDate(endDate), "Invalid endDate");
        
        Map<String, Object> map = execute(cty, head, dept, comp, startDate, endDate);
        int sosCount = ((BigDecimal) map.get(SOS_CNT)).intValue(),
            safeCount = ((BigDecimal) map.get(SAFE_CNT)).intValue(),
            noResponseCount = ((BigDecimal) map.get(NR_CNT)).intValue(),
            sosAlarmCount = ((BigDecimal) map.get(SOS_ALARM_CNT)).intValue();
        return ImmutableMap.of("sosCount", sosCount,
                               "safeCount", safeCount,
                               "noResponseCount", noResponseCount,
                               "sosAlarmCount", sosAlarmCount);
    }
}