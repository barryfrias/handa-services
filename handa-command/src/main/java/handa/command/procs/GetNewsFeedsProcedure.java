package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import handa.beans.dto.NewsFeed;
import handa.command.mappers.NewsFeedRowMapper;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class GetNewsFeedsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetNewsFeedsProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new NewsFeedRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<NewsFeed> list()
    {
        Object[] params = new Object[] {};
        Map<String, Object> map = execute(params);
        List<NewsFeed> list = (List<NewsFeed>) map.get(RESULT);
        return list;
    }
}