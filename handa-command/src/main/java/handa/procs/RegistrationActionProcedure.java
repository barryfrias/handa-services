package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.RegistrationAction;
import oracle.jdbc.OracleTypes;

public class RegistrationActionProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public RegistrationActionProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USER_REGISTRATION.REGISTRATION_ACTION");
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("P_ACTION", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_REASON", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_USER", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String call(long registrationId, RegistrationAction action)
    {
        checkNotNull(action, "registrationAction object should not be null");
        checkNotNull(action.getAction(), "action should not be null");
        checkNotNull(action.getUsername(), "username should not be null");
        if("rejected".equalsIgnoreCase(action.getAction()))
        {
            checkNotNull(action.getReason(), "reason should not be null");
        }
        Object[] params = new Object[]
        {
                registrationId,
                action.getAction(),
                action.getReason(),
                action.getUsername()
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}