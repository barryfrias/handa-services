package handa.command.mappers;

import handa.beans.dto.NewsFeed;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NewsFeedRowMapper
implements RowMapper<NewsFeed>
{
    @Override
    public NewsFeed mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        NewsFeed newsFeed = new NewsFeed();
        newsFeed.setId(rs.getInt("ID"));
        newsFeed.setTitle(rs.getString("TITLE"));
        newsFeed.setMessage(rs.getString("MESSAGE"));
        newsFeed.setImageFilename(rs.getString("IMAGE_FILENAME"));
        newsFeed.setMessageType(rs.getString("MESSAGE_TYPE"));
        newsFeed.setCreatedDate(rs.getString("CREATED_DTTM"));
        newsFeed.setUsername(rs.getString("MODIFIED_BY"));
        return newsFeed;
    }
}