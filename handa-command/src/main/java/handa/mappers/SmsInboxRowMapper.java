package handa.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.SmsInboxMessage;

public class SmsInboxRowMapper
implements RowMapper<SmsInboxMessage>
{
    @Override
    public SmsInboxMessage mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        SmsInboxMessage message = new SmsInboxMessage();
        message.setDeletedFlag(rs.getString("DELETED_FLAG"));
        message.setDeletedBy(rs.getString("DELETED_BY"));
        message.setDeletedDate(rs.getString("DELETED_DTTM"));
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