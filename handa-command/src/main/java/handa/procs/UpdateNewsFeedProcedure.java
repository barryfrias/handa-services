package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import handa.beans.dto.NewsFeed;
import handa.mappers.NewsFeedRowMapper;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.Iterables;
import com.pldt.itidm.core.exception.NotFoundException;

public class UpdateNewsFeedProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public UpdateNewsFeedProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("UPDATE_NEWS_FEED");
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("TTL", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("IMG_FNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("UPDATED_BY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new NewsFeedRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public NewsFeed update(NewsFeed newsFeed)
    {
        checkNotNull(newsFeed, "newsFeed can't be null");
        Object[] params = new Object[]
        {
            newsFeed.getId(),
            checkNotNull(newsFeed.getTitle(), "newsFeed.title can't be null"),
            checkNotNull(newsFeed.getMessage(), "newsFeed.message can't be null"),
            checkNotNull(newsFeed.getMessageType(), "newsFeed.messageType can't be null"),
            newsFeed.getImageFilename(),
            checkNotNull(newsFeed.getUsername(), "newsFeed.username can't be null")
        };
        Map<String, Object> map = execute(params);
        List<NewsFeed> list = (List<NewsFeed>) map.get(RESULT);
        if(list.isEmpty())
        {
            throw new NotFoundException(String.format("News feed id %s not found", newsFeed.getId()));
        }
        return Iterables.getOnlyElement(list);
    }
}