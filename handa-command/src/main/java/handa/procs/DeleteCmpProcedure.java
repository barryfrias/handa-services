package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.OracleTypes;

public class DeleteCmpProcedure
extends StoredProcedure
{
    private static final String RESULT = "result";

    public DeleteCmpProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("handa_cmp.delete_cmp");
        setFunction(false);
        declareParameter(new SqlParameter("p_file_id", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("p_deleted_by", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        compile();
    }

    public String delete(long fileId, String deletedBy)
    {
        checkNotNull(emptyToNull(deletedBy), "deletedBy can't be null");
        Object[] params =
        {
            fileId,
            deletedBy
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}