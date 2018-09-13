package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static handa.core.HandaUtils.checkDate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.ActivityLog;
import oracle.jdbc.OracleTypes;

public class UserActivityLogProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public UserActivityLogProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("get_user_activity_log");
        declareParameter(new SqlParameter("P_MOBILE_NUMBER", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_START_DATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_END_DATE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_PAGE_NO", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new ActivityLogRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<ActivityLog> list(String mobileNumber, String type, String startDate, String endDate, int pageNo)
    {
        checkNotNull(mobileNumber, "mobileNumber should not be null");

        if(startDate != null && ("null".equalsIgnoreCase(startDate) || startDate.trim().isEmpty()))
        {
            startDate = null;
        }
        else if(startDate != null)
        {
            checkArgument(checkDate(startDate), "startDate format should be YYYYMMDD");
        }

        if(endDate != null && ("null".equalsIgnoreCase(endDate) || endDate.trim().isEmpty()))
        {
            endDate = null;
        }
        else if(endDate != null)
        {
            checkArgument(checkDate(endDate), "endDate format should be YYYYMMDD");
        }

        Object[] params = new Object[]
        {
            mobileNumber,
            type,
            startDate,
            endDate,
            pageNo
        };
        Map<String, Object> map = execute(params);
        List<ActivityLog> list = (List<ActivityLog>) map.get(RESULT);
        if(list == null || list.isEmpty()) return ImmutableList.of();
        return list;
    }

    private class ActivityLogRowMapper
    implements RowMapper<ActivityLog>
    {
        @Override
        public ActivityLog mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            ActivityLog activityLog = new ActivityLog();
            activityLog.setRowNum(rs.getInt("ROW_NUM"));
            activityLog.setMobileNumber(rs.getString("MOBILE_NO"));
            activityLog.setType(rs.getString("TYPE"));
            activityLog.setMessage(rs.getString("MESSAGE"));
            activityLog.setActivityDate(rs.getString("ACTIVITY_DTTM"));
            return activityLog;
        }
    }
}