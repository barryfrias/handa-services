package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.core.ToMapRowMapper;
import oracle.jdbc.OracleTypes;

public class ListRegistrationsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public ListRegistrationsProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USER_REGISTRATION.LIST_REGISTRATIONS");
        declareParameter(new SqlParameter("P_REGISTRATION_ID", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_APPROVAL_STATUS", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new ToMapRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> list(Long registrationId, String approvalStatus)
    {
        Object[] params = new Object[]
        {
                registrationId,
                approvalStatus
        };
        Map<String, Object> map = execute(params);
        return (List<Map<String, Object>>) map.get(RESULT);
    }
}