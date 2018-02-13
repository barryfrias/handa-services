package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
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

public class SearchMobileNewsFeedsProcedure
extends StoredProcedure
{
    private static final String RESULT = "result";

    public SearchMobileNewsFeedsProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("search_newsfeed_public");
        declareParameter(new SqlParameter("page_no", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("username", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("keyword", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new NewsFeedRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<NewsFeed> search(String username, int pageNo, String keyword)
    {
        checkNotNull(username, "username should not be null");
        checkNotNull(keyword, "keyword should not be null");
        checkArgument(keyword.length() > 3, "keyword should not be less than 4 characters");
        Object[] params = new Object[]
        {
            pageNo,
            username,
            keyword
        };
        Map<String, Object> map = execute(params);
        List<NewsFeed> list = (List<NewsFeed>) map.get(RESULT);
        if(list == null || list.isEmpty()) return ImmutableList.of();
        return list;
    }

    private class NewsFeedRowMapper
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