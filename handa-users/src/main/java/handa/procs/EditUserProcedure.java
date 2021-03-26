package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.User;
import oracle.jdbc.OracleTypes;

public class EditUserProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public EditUserProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USER_MGMT.EDIT_USER");
        declareParameter(new SqlParameter("P_USER", OracleTypes.STRUCT));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String edit(User user)
    {
        checkNotNull(user, "user object should not be null");
        checkNotNull(user.getKey(), "key should not be null");
        checkNotNull(user.getFirstName(), "firstName should not be null");
        checkNotNull(user.getLastName(), "lastName should not be null");
        checkNotNull(user.getAdUsername(), "adUsername should not be null");
        checkNotNull(user.getMobileNumber(), "mobileNumber should not be null");
        checkNotNull(user.getCurrentAddress(), "current address should not be null");
        checkNotNull(user.getProvince(), "province should not be null");
        checkNotNull(user.getCity(), "city should not be null");
        checkNotNull(user.getBarangay(), "barangay should not be null");
        if(null != user.getPermanentAddress())
        {
            checkNotNull(user.getPermAddProvince(), "permanent address province should not be null");
            checkNotNull(user.getPermAddCity(), "permanent address city should not be null");
            checkNotNull(user.getPermAddBarangay(), "permanent address barangay should not be null");
        }
        checkNotNull(user.getModifiedBy(), "modifiedBy should not be null");
        checkNotNull(user.getCreatedDate(), "createdDate should not be null");
        Object[] params = { user };
        try
        {
            Map<String, Object> map = execute(params);
            return (String) map.get(RESULT);
        }
        catch(DataIntegrityViolationException dive)
        {
            return dive.getMostSpecificCause().getMessage();
        }
    }
}