package handa.sms.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.SmsOutboundQueue;

public class SmsOutboundQueueRowMapper
implements RowMapper<SmsOutboundQueue>
{
    @Override
    public SmsOutboundQueue mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        SmsOutboundQueue queue = new SmsOutboundQueue();
        queue.setId(rs.getInt("ID"));
        queue.setInsertedDate(rs.getString("INSERTED_DTTM"));
        queue.setMessage(rs.getString("MESSAGE"));
        queue.setMobileNo(rs.getString("MOBILE_NO"));
        queue.setOutboxId(rs.getInt("OUTBOX_ID"));
        queue.setRowNum(rowNum + 1);
        queue.setStatus(rs.getString("STATUS"));
        queue.setWsResponseMsg(rs.getString("WS_RESPONSE_MSG"));
        return queue;
    }
}