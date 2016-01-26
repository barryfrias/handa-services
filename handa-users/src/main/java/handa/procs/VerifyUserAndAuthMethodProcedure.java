package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.AuthInfo;
import handa.beans.dto.UserVerificationResult;
import oracle.jdbc.OracleTypes;

public class VerifyUserAndAuthMethodProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";
    private static final String MOBILE_NO = "MOBILE_NO";
    private static final String PASSCODE = "PASSCODE";

    public VerifyUserAndAuthMethodProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USERS_AUTH.VERIFY_USER_AND_AUTH_METHOD");
        declareParameter(new SqlParameter("P_USER_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlInOutParameter("MOBILE_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(PASSCODE, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public UserVerificationResult invoke(AuthInfo authInfo)
    {
        checkNotNull(authInfo, "authInfo object can't be null");
        checkNotNull(authInfo.getMobileNumber(), "mobileNumber can't be null");
        Object[] params =
        {
            authInfo.getUsername(),
            authInfo.getMobileNumber(),
        };
        Map<String, Object> map = execute(params);
        UserVerificationResult result = new UserVerificationResult();
        result.setMobileNumber((String) map.get(MOBILE_NO));
        result.setAuthMethod((String) map.get(RESULT));
        result.setPasscode((String) map.get(PASSCODE));
        return result;
    }
}