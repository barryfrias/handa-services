package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.SendSms;
import oracle.jdbc.OracleTypes;

public class SendSmsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public SendSmsProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        declareParameter(new SqlParameter("RCPNTS", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("CREATEDBY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String send(SendSms sendSms)
    {
        checkNotNull(sendSms, "sendSms can't be null");
        Object[] params = new Object[]
        {
            checkNotNull(emptyToNull(sendSms.getRecipients()), "sendSms.recipients can't be null"),
            checkNotNull(emptyToNull(sendSms.getMessage()), "sendSms.message can't be null"),
            checkNotNull(emptyToNull(sendSms.getCreatedBy()), "sendSms.createdBy can't be null"),
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}