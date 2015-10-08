package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.UserReport;
import handa.command.mappers.UserReportRowMapper;
import oracle.jdbc.OracleTypes;

public class GetUserReportsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetUserReportsProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_USER_REPORTS");
        declareParameter(new SqlParameter("PAGINATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("PAGE_NO", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new UserReportRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<UserReport> list(boolean paginate, int pageNo)
    {
        Object[] params = new Object[]
        {
                String.valueOf(paginate),
                pageNo
        };
        Map<String, Object> map = execute(params);
        List<UserReport> list = (List<UserReport>) map.get(RESULT);
        if(list == null || list.isEmpty()) return ImmutableList.of();
        return list;
    }
}