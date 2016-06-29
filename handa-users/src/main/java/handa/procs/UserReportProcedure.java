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

import handa.beans.dto.DeviceInfo;
import handa.beans.dto.UserReportInput;
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
        declareParameter(new SqlParameter("APP_VER", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("BAT_LVL", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("LONGI", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("LATI", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("PROV", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("CTY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("BGY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("ADRS", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("EVT_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("IMG_FNAME", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String report(DeviceInfo deviceInfo, UserReportInput userReportInput)
    {
        checkNotNull(userReportInput, "userReportInput object can't be null");
        String uniqueId = getUniqueId(deviceInfo, userReportInput);
        String osVersion = getOsVersion(deviceInfo, userReportInput);
        String batteryLevel = getBatteryLevel(deviceInfo, userReportInput);
        Object[] params = new Object[]
        {
                checkNotNull(userReportInput.getMobileNumber()),
                uniqueId,
                osVersion,
                deviceInfo != null? deviceInfo.getAppVersion() : null,
                batteryLevel,
                userReportInput.getLongitude(),
                userReportInput.getLatitude(),
                userReportInput.getProvince(),
                userReportInput.getCity(),
                userReportInput.getBarangay(),
                userReportInput.getAddress(),
                userReportInput.getMessage(),
                checkNotNull(userReportInput.getEventType()),
                userReportInput.getImageFilename()
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

    private String getUniqueId(DeviceInfo info, UserReportInput userReport)
    {
        return (info == null || info.getDeviceId() == null)? userReport.getMacAddress() : info.getDeviceId();
    }

    private String getOsVersion(DeviceInfo info, UserReportInput userReport)
    {
        return (info == null || info.getDeviceType() == null)? userReport.getOsVersion() : info.getDeviceType();
    }

    private String getBatteryLevel(DeviceInfo info, UserReportInput userReport)
    {
        return (info == null || info.getBatteryLevel() == null)? userReport.getBatteryLevel() : info.getBatteryLevel();
    }
}