package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.UserReport;
import handa.config.HandaUsersConstants;
import oracle.jdbc.OracleTypes;

public class UserReportProcedure
extends StoredProcedure
{
    static Logger log = LoggerFactory.getLogger(UserReportProcedure.class);
    private static final String RESULT = "RESULT";

    public UserReportProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("USER_REPORT");
        declareParameter(new SqlParameter("MOB_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MAC_ADD", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("OS_VER", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("BAT_LVL", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("LONGI", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("LATI", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("EVT_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("IMG_FNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String report(UserReport userReport)
    {
        checkNotNull(userReport, "userReport object can't be null");
        Object[] params = new Object[]
        {
                checkNotNull(userReport.getMobileNumber()),
                userReport.getMacAddress(),
                userReport.getOsVersion(),
                userReport.getBatteryLevel(),
                userReport.getLongitude(),
                userReport.getLatitude(),
                userReport.getMessage(),
                checkNotNull(userReport.getEventType()),
                userReport.getImageFilename()
        };
        try
        {
            Map<String, Object> map = execute(params);
            return (String) map.get(RESULT);
        }
        catch(DataAccessException dae)
        {
            log.error(HandaUsersConstants.DATABASE_ERROR, dae);
            return HandaUsersConstants.DATABASE_ERROR;
        }
    }
}