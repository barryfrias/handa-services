package handa.procs;

import static handa.config.HandaCommandConstants.ALL;
import static com.google.common.base.Preconditions.checkNotNull;
import handa.config.HandaCommandConstants.PromptType;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class PromptsCountProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public PromptsCountProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_PROMPT_COUNT");
        declareParameter(new SqlParameter("PR_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("CITY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.NUMBER));
        setFunction(false);
        compile();
    }

    public int getPromptCount(PromptType promptType, String city)
    {
        if(ALL.equalsIgnoreCase(city))
        {
            city = null;
        }
        Map<String, Object> map = execute(promptType.toString(), city);
        BigDecimal count = (BigDecimal) map.get(RESULT);
        return count.intValue();
    }
}