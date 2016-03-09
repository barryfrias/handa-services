package handa.procs;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.UserRegistration;
import oracle.jdbc.OracleTypes;

public class DomainUserRegistrationProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";
    private static final String REG_ID = "REG_ID";

    public DomainUserRegistrationProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_USER_REGISTRATION.REGISTER_DOMAIN_USER");
        declareParameter(new SqlParameter("P_USERNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_EMPLOYEE_NO", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("P_FIRST_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_MIDDLE_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_LAST_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_POSITION", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_IMMEDIATE_HEAD", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_DEPT", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_COMPANY_CODE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_MOBILE_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_ADDRESS", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_PROVINCE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_CITY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_BARANGAY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(REG_ID, OracleTypes.NUMBER));
        setFunction(false);
        compile();
    }

    public DomainRegistrationRequestResult register(UserRegistration userRegistration)
    {
        checkNotNull(userRegistration, "userRegistration object should not be null");
        checkNotNull(userRegistration.getUsername(), "username should not be null");
        checkNotNull(userRegistration.getFirstName(), "firstName should not be null");
        checkNotNull(userRegistration.getLastName(), "lastName should not be null");
        checkNotNull(userRegistration.getMobileNumber(), "mobileNumber should not be null");
        checkNotNull(userRegistration.getCompanyCode(), "companyCode should not be null");
        Object[] params = new Object[]
        {
                userRegistration.getUsername(),
                userRegistration.getEmployeeNumber(),
                userRegistration.getFirstName(),
                userRegistration.getMiddleName(),
                userRegistration.getLastName(),
                userRegistration.getPosition(),
                userRegistration.getImmediateHead(),
                userRegistration.getDepartment(),
                userRegistration.getCompanyCode(),
                userRegistration.getMobileNumber(),
                userRegistration.getAddress(),
                userRegistration.getProvince(),
                userRegistration.getCity(),
                userRegistration.getBarangay()
        };
        Map<String, Object> map = execute(params);
        DomainRegistrationRequestResult result = new DomainRegistrationRequestResult();
        result.setRegistrationId((BigDecimal)map.get(REG_ID));
        result.setMessage((String) map.get(RESULT));
        return result;
    }

    public static class DomainRegistrationRequestResult
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