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

public class DomainUserRegistrationProcedure
extends StoredProcedure
{
    static Logger log = LoggerFactory.getLogger(DomainUserRegistrationProcedure.class);
    private static final String RESULT = "RESULT";

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
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String register(UserRegistration userRegistration)
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
                userRegistration.getMobileNumber()
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}