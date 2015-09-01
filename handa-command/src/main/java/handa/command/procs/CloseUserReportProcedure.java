package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.CloseUserReport;
import oracle.jdbc.OracleTypes;

public class CloseUserReportProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public CloseUserReportProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("CLOSE_USER_REPORT");
        setFunction(false);
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("REF_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("REASON", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("CLOSED_BY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.NUMBER));
        compile();
    }

    public int closeUserReport(int id, CloseUserReport closeUserReport)
    {
        checkNotNull(closeUserReport, "closeUserReport object can't be null");
        Object[] params =
        {
            id,
            closeUserReport.getReferenceNumber(),
            closeUserReport.getReason(),
            checkNotNull(emptyToNull(closeUserReport.getUsername()), "closeUserReport.username can't be null")
        };
        Map<String, Object> map = execute(params);
        BigDecimal count = (BigDecimal) map.get(RESULT);
        return count.intValue();
    }
}