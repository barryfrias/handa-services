package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.AuthInfo;
import oracle.jdbc.OracleTypes;

public class AuthByMobileAndUsernameProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public AuthByMobileAndUsernameProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USERS_AUTH.LOGIN_BY_MOBILE_AND_USERNAME");
        declareParameter(new SqlParameter("MOB_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("USERNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String authenticate(AuthInfo authInfo)
    {
        checkNotNull(authInfo, "authInfo object can't be null");
        Object[] params =
        {
                checkNotNull(authInfo.getMobileNumber(), "mobileNumber should not be null"),
                checkNotNull(authInfo.getUsername(), "username should not be null"),
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}