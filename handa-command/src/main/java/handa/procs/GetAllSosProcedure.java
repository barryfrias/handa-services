package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static handa.core.HandaUtils.checkDate;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
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
        setSql("GET_ALL_SOS");
        declareParameter(new SqlParameter("p_cty", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_head", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_dept", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_comp", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_startdate", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_enddate", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new SosRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<SosPrompt> get(String cty, String head, String dept, String comp, String startDate, String endDate)
    {
        checkArgument(checkDate(startDate), "Invalid startDate");
        checkArgument(checkDate(endDate), "Invalid endDate");

        Object[] params = new Object[] { cty, head, dept, comp, startDate, endDate };
        Map<String, Object> map = execute(params);
        List<SosPrompt> list = (List<SosPrompt>) map.get(RESULT);
        return list;
    }
}