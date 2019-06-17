package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.NewsFeed;
import handa.beans.dto.NewsFeedSearch;
import handa.mappers.NewsFeedRowMapper;
import oracle.jdbc.OracleTypes;

public class SearchNewsFeedProcedure
extends StoredProcedure
{
    private static final String RESULT = "result";

    public SearchNewsFeedProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("handa_newsfeed.search");
        declareParameter(new SqlParameter("keyword", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new NewsFeedRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<NewsFeed> search(NewsFeedSearch newsFeedSearch)
    {
        checkNotNull(newsFeedSearch, "keyword can't be null");
        checkNotNull(newsFeedSearch.getKeyword(), "keyword can't be null");
        Object[] params = new Object[]
        {
                newsFeedSearch.getKeyword()
        };
        Map<String, Object> map = execute(params);
        List<NewsFeed> list = (List<NewsFeed>) map.get(RESULT);
        if(list == null || list.isEmpty()) return ImmutableList.of();
        return list;
    }
}