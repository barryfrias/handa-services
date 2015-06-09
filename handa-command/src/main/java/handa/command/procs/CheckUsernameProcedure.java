package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CheckUsernameProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public CheckUsernameProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        declareParameter(new SqlParameter("USERNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String check(String username)
    {
        checkNotNull(username, "username can't be null");
        Map<String, Object> map = execute(username);
        return (String) map.get(RESULT);
    }
}