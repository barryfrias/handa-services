package handa.command.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.SmsMessage;

public class SmsMessageRowMapper
implements RowMapper<SmsMessage>
{
    @Override
    public SmsMessage mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        SmsMessage message = new SmsMessage();
        message.setDeletedFlag(rs.getString("DELETED_FLAG"));
        message.setFolder(rs.getString("FOLDER"));
        message.setId(rs.getInt("ID"));
        message.setInsertedDate(rs.getString("INSERTED_DTTM"));
        message.setMessage(rs.getString("MESSAGE"));
        message.setMobileNumber(rs.getString("MOBILE_NUMBER"));
        message.setMsgDate(rs.getString("MSG_DTTM"));
        message.setReadBy(rs.getString("READ_BY"));
        message.setReadDate(rs.getString("READ_DTTM"));
        message.setReadFlag(rs.getString("READ_FLAG"));
        return message;
    }
}