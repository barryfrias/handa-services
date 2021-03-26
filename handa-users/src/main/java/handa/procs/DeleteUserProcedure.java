package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.OracleTypes;

public class DeleteUserProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public DeleteUserProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USER_MGMT.DELETE_USER");
        declareParameter(new SqlParameter("P_MOB_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_CREATED_DATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_DELETED_BY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String delete(String mobileNumber, String createdDate, String deletedBy)
    {
        checkNotNull(emptyToNull(mobileNumber), "mobileNumber should not be null");
        checkNotNull(emptyToNull(createdDate), "createdDate should not be null");
        checkNotNull(emptyToNull(deletedBy), "deletedBy should not be null");
        Object[] params = { mobileNumber,createdDate,deletedBy };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}