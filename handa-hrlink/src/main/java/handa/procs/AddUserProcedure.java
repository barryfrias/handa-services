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

public class AddUserProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public AddUserProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("ADD_USER");
        declareParameter(new SqlParameter("P_USER", OracleTypes.STRUCT));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String add(User user)
    {
        checkNotNull(user, "user object should not be null");
        checkNotNull(user.getFirstName(), "firstName should not be null");
        checkNotNull(user.getLastName(), "lastName should not be null");
        checkNotNull(user.getAdUsername(), "adUsername should not be null");
        checkNotNull(user.getMobileNumber(), "mobileNumber should not be null");
        checkNotNull(user.getCity(), "city should not be null");
        checkNotNull(user.getProvince(), "province should not be null");
        checkNotNull(user.getCurrentAddress(), "current address should not be null");
        checkNotNull(user.getPermanentAddress(), "permanent address should not be null");
        checkNotNull(user.getModifiedBy(), "modifiedBy should not be null");
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