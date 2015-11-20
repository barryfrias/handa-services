package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.SmsOutboundQueue;
import handa.mappers.SmsOutboundQueueRowMapper;
import oracle.jdbc.OracleTypes;

public class GetSmsOutboundQueueProcedure
extends StoredProcedure
{
    private static final String OUTPUT = "OUTPUT";

    public GetSmsOutboundQueueProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_SMS_OUTBOUND_QUEUE");
        setFunction(false);
        declareParameter(new SqlOutParameter(OUTPUT, OracleTypes.CURSOR, new SmsOutboundQueueRowMapper()));
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<SmsOutboundQueue> list()
    {
        Map<String, Object> map = execute();
        return (List<SmsOutboundQueue>) map.get(OUTPUT);
    }
}