package handa.core;

import static com.google.common.base.Preconditions.checkNotNull;
import handa.beans.dto.AppLog;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class InsertLogProcedure
extends StoredProcedure
{
    public InsertLogProcedure(DataSource dataSource)
    {
        setSql("INSERT_LOG");
        setDataSource(checkNotNull(dataSource));
        setFunction(false);
        declareParameter(new SqlParameter("SOURCE", Types.VARCHAR));
        declareParameter(new SqlParameter("USERNAME", Types.VARCHAR));
        declareParameter(new SqlParameter("MOBILE_NUMBER", Types.VARCHAR));
        declareParameter(new SqlParameter("MESSAGE", Types.VARCHAR));
        compile();
    }

    public void insertLog(AppLog appLog)
    {
        checkNotNull(appLog);
        execute(checkNotNull(appLog.getSource().toString()),
                checkNotNull(appLog.getUsername()),
                checkNotNull(appLog.getMobileNumber()),
                checkNotNull(appLog.getMessage()));
    }
}