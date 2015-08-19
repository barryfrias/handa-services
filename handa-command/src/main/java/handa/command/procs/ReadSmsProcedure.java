package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.ReadSms;
import oracle.jdbc.OracleTypes;

public class ReadSmsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public ReadSmsProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        setFunction(false);
        declareParameter(new SqlParameter("MSG_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("READBY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.NUMBER));
        compile();
    }

    public int markAsRead(int id, ReadSms readSms)
    {
        checkNotNull(readSms, "readSms object can't be null");
        Object[] params =
        {
            id,
            checkNotNull(emptyToNull(readSms.getReadBy()), "readSms.readBy can't be null")
        };
        Map<String, Object> map = execute(params);
        BigDecimal count = (BigDecimal) map.get(RESULT);
        return count.intValue();
    }
}