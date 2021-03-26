package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.NewsFeed;
import oracle.jdbc.OracleTypes;

public class GetPrivateMobileNewsFeedsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetPrivateMobileNewsFeedsProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("handa_newsfeed.get_private_mobile");
        declareParameter(new SqlParameter("PAGE_NO", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("P_USERNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new PrivateNewsFeedRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<NewsFeed> list(String username, int pageNo)
    {
        checkNotNull(username, "username should not be null");
        Object[] params = new Object[]
        {
            pageNo,
            username
        };
        Map<String, Object> map = execute(params);
        List<NewsFeed> list = (List<NewsFeed>) map.get(RESULT);
        if(list == null || list.isEmpty()) return ImmutableList.of();
        return list;
    }

    private class PrivateNewsFeedRowMapper
    implements RowMapper<NewsFeed>
    {
        @Override
        public NewsFeed mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            NewsFeed newsFeed = new NewsFeed();
            newsFeed.setRowNum(rs.getInt("RNUM"));
            newsFeed.setId(rs.getInt("ID"));
            newsFeed.setTitle(rs.getString("TITLE"));
            newsFeed.setMessage(rs.getString("MESSAGE"));
            newsFeed.setImageFilename(rs.getString("IMAGE_FILENAME"));
            newsFeed.setMessageType(rs.getString("MESSAGE_TYPE"));
            newsFeed.setCreatedDate(rs.getString("CREATED_DTTM"));
            newsFeed.setUsername(rs.getString("USERNAME"));
            newsFeed.setType(rs.getString("TYPE"));
            return newsFeed;
        }
    }
}