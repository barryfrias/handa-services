package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.OracleTypes;

public class DeleteCallTreeProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public DeleteCallTreeProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_CALL_TREES.DELETE_DATA");
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String delete(long id)
    {
        checkNotNull(id, "id should not be null");
        Object[] params = new Object[] { id };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}