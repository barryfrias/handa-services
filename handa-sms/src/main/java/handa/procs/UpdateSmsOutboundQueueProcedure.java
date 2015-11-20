package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.UpdateSmsOutboundQueue;
import oracle.jdbc.OracleTypes;

public class UpdateSmsOutboundQueueProcedure
extends StoredProcedure
{
    public UpdateSmsOutboundQueueProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("UPDATE_SMS_OUTBOUND_QUEUE");
        setFunction(false);
        declareParameter(new SqlParameter("QUEUE_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("UPDATE_STATUS", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("RESPONSE_MSG", OracleTypes.VARCHAR));
        compile();
    }

    public void update(UpdateSmsOutboundQueue input)
    {
        checkNotNull(input, "updateSmsOutboundQueue object can't be null.");
        Object[] params = new Object[] {
               input.getQueueId(),
               checkNotNull(input.getUpdateStatus(), "updateSmsOutboundQueue.updateStatus can't be null."),
               checkNotNull(input.getResponseMessage(), "updateSmsOutboundQueue.responseMessage can't be null.")
        };
        execute(params);
    }
}