package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.pldt.itidm.core.utils.JdbcHelper.toOracleArray;

import handa.beans.dto.NewsFeed;
import handa.mappers.NewsFeedRowMapper;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.Iterables;

public class InsertNewsFeedProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    private DataSource dataSource;

    public InsertNewsFeedProcedure(DataSource dataSource)
    {
        this.dataSource = checkNotNull(dataSource);
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_NEWSFEED.POST");
        declareParameter(new SqlParameter("TTL", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("DISTRIBUTION_LIST_KEY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("DISTRIBUTION_LIST_VALUES", OracleTypes.ARRAY));
        declareParameter(new SqlParameter("IMG_FNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("CREATED_BY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new NewsFeedRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public NewsFeed insert(NewsFeed newsFeed)
    {
        checkNotNull(newsFeed, "newsFeed can't be null");
        checkNotNull(newsFeed.getDistributionListKey(),"distributionListKey should not be null");
        ARRAY distListVals = toOracleArray(newsFeed.getDistributionListValues(), "DIST_LIST_VALS_TYPE", dataSource);
        Object[] params = new Object[]
        {
            checkNotNull(newsFeed.getTitle(), "newsFeed.title can't be null"),
            checkNotNull(newsFeed.getMessage(), "newsFeed.message can't be null"),
            checkNotNull(newsFeed.getMessageType(), "newsFeed.messageType can't be null"),
            newsFeed.getDistributionListKey(),
            distListVals,
            newsFeed.getImageFilename(),
            checkNotNull(newsFeed.getUsername(), "newsFeed.username can't be null")
        };
        Map<String, Object> map = execute(params);
        List<NewsFeed> list = (List<NewsFeed>) map.get(RESULT);
        return Iterables.getOnlyElement(list);
    }
}