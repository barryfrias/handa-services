package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.OracleTypes;

public class DeleteNewsFeedProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public DeleteNewsFeedProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("DELETE_NEWS_FEED");
        setFunction(false);
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("DELETED_BY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.NUMBER));
        compile();
    }

    public int delete(int id, String deletedBy)
    {
        checkNotNull(emptyToNull(deletedBy), "deletedBy can't be null");
        Object[] params =
        {
            id,
            deletedBy
        };
        Map<String, Object> map = execute(params);
        BigDecimal count = (BigDecimal) map.get(RESULT);
        return count.intValue();
    }
}
