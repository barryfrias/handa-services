package handa.sms.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class GenericProcedure<ReturnType>
extends StoredProcedure
{
    private static final String OUT = "out";

    public GenericProcedure(DataSource dataSource, String procName, RowMapper<ReturnType> rowMapper)
    {
        setSql(checkNotNull(procName));
        setDataSource(checkNotNull(dataSource));
        setFunction(false);
        declareParameter(new SqlOutParameter(OUT, OracleTypes.CURSOR, checkNotNull(rowMapper)));
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<ReturnType> listValues()
    {
        Map<String, Object> map = execute();
        List<ReturnType> list = (List<ReturnType>) map.get(OUT);
        return list;
    }
}