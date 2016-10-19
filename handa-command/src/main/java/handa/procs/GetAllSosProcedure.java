package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.SosPrompt;
import handa.mappers.SosRowMapper;
import oracle.jdbc.OracleTypes;

public class GetAllSosProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetAllSosProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("get_all_sos");
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new SosRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<SosPrompt> get()
    {
        Object[] params = new Object[0];
        Map<String, Object> map = execute(params);
        List<SosPrompt> list = (List<SosPrompt>) map.get(RESULT);
        return list;
    }
}