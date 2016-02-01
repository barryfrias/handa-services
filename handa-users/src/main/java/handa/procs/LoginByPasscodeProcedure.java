package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.AuthInfo;
import oracle.jdbc.OracleTypes;

public class LoginByPasscodeProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public LoginByPasscodeProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USERS_AUTH.LOGIN_BY_PASSCODE");
        declareParameter(new SqlParameter("P_MOB_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_PASSCODE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String login(AuthInfo authInfo)
    {
        checkNotNull(authInfo, "authInfo object should not be null");
        checkNotNull(authInfo.getMobileNumber(), "mobileNumber should not be null");
        checkNotNull(authInfo.getPassword(), "password should not be null");
        Object[] params =
        {
            authInfo.getMobileNumber(),
            authInfo.getPassword()
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}