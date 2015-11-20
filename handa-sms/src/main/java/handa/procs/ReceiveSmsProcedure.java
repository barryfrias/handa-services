package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.SmsInbound;
import oracle.jdbc.OracleTypes;

public class ReceiveSmsProcedure
extends StoredProcedure
{
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String RESULT = "RESULT";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);

    public ReceiveSmsProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("RECEIVE_SMS");
        setFunction(false);
        declareParameter(new SqlParameter("MOB_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG_DT", OracleTypes.TIMESTAMP)); // will capture time(hh:mm:ss) component
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        compile();
    }

    public String receive(SmsInbound smsInbound)
    {
        checkNotNull(smsInbound, "smsInbound object can't be null");
        validateMobNum(smsInbound.getMobileNumber());
        checkNotNull(emptyToNull(smsInbound.getMessage()), "message can't be null");
        checkNotNull(smsInbound.getTimestamp(), "timestamp can't be null");
        Object[] params =
        {
            smsInbound.getMobileNumber(),
            smsInbound.getMessage(),
            toDate(smsInbound.getTimestamp())
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }

    private Date toDate(String timestamp)
    {
        try
        {
            return SDF.parse(timestamp);
        }
        catch(ParseException e)
        {
            throw new IllegalArgumentException(String.format("Invalid timestamp [%s], expected format is %s", timestamp, DATE_FORMAT));
        }
    }

    private void validateMobNum(String mobileNumber)
    {
        checkNotNull(emptyToNull(mobileNumber), "mobileNumber can't be null");
        checkArgument(mobileNumber.matches("\\+?\\d+"), "mobileNumber not numeric");
        int len = mobileNumber.trim().length();
        checkArgument(len >= 10 && len <=15, "Invalid mobileNumber length");
    }
}