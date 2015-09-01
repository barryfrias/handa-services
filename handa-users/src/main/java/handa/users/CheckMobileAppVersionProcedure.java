package handa.users;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.OracleTypes;

public class CheckMobileAppVersionProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public CheckMobileAppVersionProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("CHECK_MOBILE_APP_VERSION");
        declareParameter(new SqlParameter("VERSION", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String check(String versionString)
    {
        checkNotNull(versionString, "versionString object can't be null");
        Map<String, Object> map = execute(versionString);
        return (String) map.get(RESULT);
    }
}