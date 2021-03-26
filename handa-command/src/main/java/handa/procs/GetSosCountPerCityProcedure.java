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

import handa.beans.dto.PromptCount;
import handa.mappers.PromptCountRowMapper;
import oracle.jdbc.OracleTypes;

public class GetSosCountPerCityProcedure
extends StoredProcedure
{
    private static final String OUT = "out";

    public GetSosCountPerCityProcedure(DataSource dataSource)
    {
        setSql("HANDA_DASHBOARD.GET_SOS_COUNT_PER_CITY");
        setDataSource(checkNotNull(dataSource));
        setFunction(false);
        declareParameter(new SqlParameter("P_STARTDATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_ENDDATE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(OUT, OracleTypes.CURSOR, new PromptCountRowMapper()));
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<PromptCount> listValues(String startDate, String endDate)
    {
        checkArgument(checkDate(startDate), "Invalid startDate");
        checkArgument(checkDate(endDate), "Invalid endDate");
        Map<String, Object> map = execute(new Object[] { startDate, endDate });
        List<PromptCount> list = (List<PromptCount>) map.get(OUT);
        return list;
    }
}