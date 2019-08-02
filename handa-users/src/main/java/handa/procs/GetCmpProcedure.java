package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.Cmp;
import handa.mappers.CmpRowMapper;
import oracle.jdbc.OracleTypes;

public class GetCmpProcedure
extends StoredProcedure
{
    private static final String RESULT = "result";

    public GetCmpProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("handa_cmp.get_cmp");
        declareParameter(new SqlParameter("p_viewer", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new CmpRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Cmp> list(String username)
    {
        checkNotNull(username, "username should not be null");
        Object[] params = new Object[]{username};
        Map<String, Object> map = execute(params);
        List<Cmp> list = (List<Cmp>) map.get(RESULT);
        if(list == null || list.isEmpty()) return ImmutableList.of();
        return list;
    }
}