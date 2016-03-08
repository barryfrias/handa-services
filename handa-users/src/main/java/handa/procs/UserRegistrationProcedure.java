package handa.procs;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;
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
    private static final String REG_ID = "REG_ID";

    public UserRegistrationProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USER_REGISTRATION.REGISTER");
        declareParameter(new SqlParameter("P_USERNAME_OR_EMAIL", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_FIRST_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_LAST_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_COMPANY_CODE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_MOBILE_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(REG_ID, OracleTypes.NUMBER));
        setFunction(false);
        compile();
    }

    public RegistrationRequestResult register(UserRegistration userRegistration)
    {
        checkNotNull(userRegistration, "userRegistration object can't be null");
        checkNotNull(userRegistration.getCompanyCode(), "companyCode can't be null");
        checkNotNull(userRegistration.getFirstName(), "firstName can't be null");
        checkNotNull(userRegistration.getLastName(), "lastName can't be null");
        checkNotNull(userRegistration.getMobileNumber(), "mobileNumber can't be null");
        checkNotNull(userRegistration. getEmail(), "email can't be null");

        Object[] params = new Object[]
        {
            userRegistration.getEmail(),
            userRegistration.getFirstName(),
            userRegistration.getLastName(),
            userRegistration.getCompanyCode(),
            userRegistration.getMobileNumber()
        };
        Map<String, Object> map = execute(params);
        RegistrationRequestResult result = new RegistrationRequestResult();
        result.setRegistrationId((BigDecimal)map.get(REG_ID));
        result.setMessage((String) map.get(RESULT));
        return result;
    }

    public static class RegistrationRequestResult
    {
        private BigDecimal registrationId;
        private String message;
        public BigDecimal getRegistrationId()
        {
            return registrationId;
        }
        private void setRegistrationId(BigDecimal registrationId)
        {
            this.registrationId = registrationId;
        }
        public String getMessage()
        {
            return message;
        }
        private void setMessage(String message)
        {
            this.message = message;
        }
        @Override
        public String toString()
        {
            return toStringHelper(this)
                   .add("registrationId", registrationId)
                   .add("message", message)
                   .toString();
        }
    }
}