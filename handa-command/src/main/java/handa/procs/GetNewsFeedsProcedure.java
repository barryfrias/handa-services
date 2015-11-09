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
import handa.mappers.NewsFeedRowMapper;
import oracle.jdbc.OracleTypes;

public class GetNewsFeedsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetNewsFeedsProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_NEWS_FEEDS");
        declareParameter(new SqlParameter("PAGINATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("PAGE_NO", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new NewsFeedRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<NewsFeed> list(boolean paginate, int pageNo)
    {
        Object[] params = new Object[]
        {
                String.valueOf(paginate),
                pageNo
        };
        Map<String, Object> map = execute(params);
        List<NewsFeed> list = (List<NewsFeed>) map.get(RESULT);
        if(list == null || list.isEmpty()) return ImmutableList.of();
        return list;
    }
}