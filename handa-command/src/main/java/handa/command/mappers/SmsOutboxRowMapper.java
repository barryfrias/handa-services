package handa.command.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.SmsOutboxMessage;

public class SmsOutboxRowMapper
implements RowMapper<SmsOutboxMessage>
{
    @Override
    public SmsOutboxMessage mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        SmsOutboxMessage message = new SmsOutboxMessage();
        message.setCreatedBy(rs.getString("CREATED_BY"));
        message.setCreatedDate(rs.getString("CREATED_DTTM"));
        message.setId(rs.getInt("ID"));
        message.setMessage(rs.getString("MESSAGE"));
        message.setRecipients(rs.getString("RECIPIENTS"));
        message.setStatus(rs.getString("STATUS"));
        return message;
    }
}