package handa.mappers;

import java.sql.Array;
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
        message.setDeletedBy(rs.getString("DELETED_BY"));
        message.setDeletedDate(rs.getString("DELETED_DTTM"));
        message.setDeletedFlag(rs.getString("DELETED_FLAG"));
        Array array = rs.getArray("ANONYMOUS_NUMBERS");
        message.setAnonymousNumbers((array == null? new String[0] : (String[]) array.getArray()));
        message.setDistributionListKey(rs.getString("DISTRIBUTION_LIST_KEY"));
        array = rs.getArray("DISTRIBUTION_LIST_VALUES");
        message.setDistributionListValues((array == null? new String[0] : (String[]) array.getArray()));
        return message;
    }
}