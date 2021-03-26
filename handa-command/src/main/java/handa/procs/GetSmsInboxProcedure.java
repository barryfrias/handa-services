package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.SmsInboxMessage;
import handa.mappers.SmsInboxRowMapper;
import oracle.jdbc.OracleTypes;

public class GetSmsInboxProcedure
extends StoredProcedure
{
    private static final String RESULT = "out";

    public GetSmsInboxProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_SMS_INBOX");
        declareParameter(new SqlParameter("P_CLUTTER", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new SmsInboxRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<SmsInboxMessage> list(boolean isClutter)
    {
        Object[] params = new Object[]
        {
                isClutter? 1 : 0
        };
        Map<String, Object> map = execute(params);
        List<SmsInboxMessage> list = (List<SmsInboxMessage>) map.get(RESULT);
        if(list == null) return ImmutableList.of();
        return list;
    }
}