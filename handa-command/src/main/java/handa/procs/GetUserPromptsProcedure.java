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

import handa.beans.dto.UserPrompt;
import handa.config.HandaCommandConstants.PromptType;
import handa.mappers.UserPromptRowMapper;
import oracle.jdbc.OracleTypes;

public class GetUserPromptsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public GetUserPromptsProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_DASHBOARD.GET_USER_PROMPTS");
        declareParameter(new SqlParameter("PR_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_cty", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_head", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_dept", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_comp", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_STARTDATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_ENDDATE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new UserPromptRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<UserPrompt> list(PromptType promptType, String cty, String head, String dept, String comp, String startDate, String endDate)
    {
        checkArgument(checkDate(startDate), "Invalid startDate");
        checkArgument(checkDate(endDate), "Invalid endDate");

        Object[] params = new Object[] { promptType.toString(), cty, head, dept, comp, startDate, endDate};
        Map<String, Object> map = execute(params);
        List<UserPrompt> list = (List<UserPrompt>) map.get(RESULT);
        return list;
    }
}