package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.UserPromptInput;
import handa.config.HandaUsersConstants;
import oracle.jdbc.OracleTypes;

public class UserPromptProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public UserPromptProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("USER_PROMPT");
        declareParameter(new SqlParameter("MOB_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("PR_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("DEVICE_INFO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("LONGI", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("LATI", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("PROV", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("CTY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("BGY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("ADRS", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("BAT_LVL", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String prompt(UserPromptInput userPromptInput, HandaUsersConstants.PromptType promptType)
    {
        checkNotNull(userPromptInput, "userPromptInput object can't be null");
        checkNotNull(promptType);
        Map<String, Object> map = execute(checkNotNull(userPromptInput.getMobileNumber()),
                                          promptType.toString(),
                                          userPromptInput.getDeviceInfo(),
                                          checkNotNull(userPromptInput.getLongitude()),
                                          checkNotNull(userPromptInput.getLatitude()),
                                          userPromptInput.getProvince(),
                                          userPromptInput.getCity(),
                                          userPromptInput.getBarangay(),
                                          userPromptInput.getAddress(),
                                          checkNotNull(userPromptInput.getBatteryLevel()));
        return (String) map.get(RESULT);
    }
}