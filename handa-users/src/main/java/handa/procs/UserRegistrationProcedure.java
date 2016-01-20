package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.UserRegistration;
import oracle.jdbc.OracleTypes;

public class UserRegistrationProcedure
extends StoredProcedure
{
    static Logger log = LoggerFactory.getLogger(UserRegistrationProcedure.class);
    private static final String RESULT = "RESULT";

    public UserRegistrationProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USER_REGISTRATION.REGISTER");
        declareParameter(new SqlParameter("P_USERNAME_OR_EMAIL", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_FIRST_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_LAST_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_COMPANY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_MOBILE_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String register(UserRegistration userRegistration)
    {
        checkNotNull(userRegistration, "userRegistration object can't be null");
        checkNotNull(userRegistration.getCompanyCode(), "companyCode object can't be null");
        checkNotNull(userRegistration.getFirstName(), "firstName object can't be null");
        checkNotNull(userRegistration.getLastName(), "lastName object can't be null");
        checkNotNull(userRegistration.getMobileNumber(), "mobileNumber object can't be null");
        checkNotNull(userRegistration. getUsernameOrEmailAdd(), "usernameOrEmailAdd object can't be null");

        Object[] params = new Object[]
        {
            userRegistration.getUsernameOrEmailAdd(),
            userRegistration.getFirstName(),
            userRegistration.getLastName(),
            userRegistration.getCompanyCode(),
            userRegistration.getMobileNumber()
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}