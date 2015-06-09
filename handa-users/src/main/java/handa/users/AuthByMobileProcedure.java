package handa.users;

import static com.google.common.base.Preconditions.checkNotNull;
import handa.beans.dto.AuthInfo;

import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class AuthByMobileProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public AuthByMobileProcedure(DataSource dataSource, String proc)
    {
        setDataSource(checkNotNull(dataSource));
        setSql(checkNotNull(proc));
        declareParameter(new SqlParameter("MOB_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String authenticate(AuthInfo authInfo)
    {
        checkNotNull(authInfo, "authInfo object can't be null");
        Map<String, Object> map = execute(checkNotNull(authInfo.getMobileNumber(), "authInfo.mobileNumber can't be null"));
        return (String) map.get(RESULT);
    }
}