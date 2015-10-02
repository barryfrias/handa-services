package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.Iterator;
import java.util.List;
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

    public SendSmsProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("SEND_SMS");
        declareParameter(new SqlParameter("RCPNTS", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("DISTRIBUTION_LIST_KEY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("DISTRIBUTION_LIST_VALUES", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("ANONYMOUS_NUMBERS", OracleTypes.VARCHAR));
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
            emptyToNull(sendSms.getDistributionListKey()),
            stringify(sendSms.getDistributionListValues(), "'"),
            stringify(sendSms.getAnonymousNumbers(), null),
            checkNotNull(emptyToNull(sendSms.getCreatedBy()), "sendSms.createdBy can't be null")
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }

    private static String stringify(List<String> input, String encloser)
    {
        if(input == null || input.isEmpty()) return null;
        StringBuilder buf = new StringBuilder();
        Iterator<String> it = input.iterator();
        while(it.hasNext())
        {
            String value = emptyToNull(it.next());
            if(value == null) continue;
            value = value.trim();
            if(encloser != null)
            {
                buf.append(encloser).append(value).append(encloser);
            }
            else
            {
                buf.append(value);
            }
            if(it.hasNext()) buf.append(',');
        }
        return buf.toString();
    }
}