package handa.sms.mappers;

import handa.beans.dto.PromptCount;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PromptCountRowMapper
implements RowMapper<PromptCount>
{
    @Override
    public PromptCount mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        PromptCount promptCount = new PromptCount();
        promptCount.setCity(rs.getString("CITY"));
        promptCount.setCount(rs.getInt("COUNT"));
        return promptCount;
    }
}