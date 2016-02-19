package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.CallTree;
import handa.mappers.CallTreeRowMapper;
import oracle.jdbc.OracleTypes;

public class ListCallTreesProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public ListCallTreesProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_CALL_TREES.GET");
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new CallTreeRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<CallTree> list(Long id)
    {
        Object[] params = new Object[] { id };
        Map<String, Object> map = execute(params);
        List<CallTree> list = (List<CallTree>) map.get(RESULT);
        if(list == null) return ImmutableList.of();
        return list;
    }
}