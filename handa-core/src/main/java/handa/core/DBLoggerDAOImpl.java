package handa.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import handa.beans.dto.AppLog;

import org.springframework.jdbc.core.JdbcTemplate;

import com.pldt.itmss.core.utils.AbstractJdbcDAO;

public class DBLoggerDAOImpl
extends AbstractJdbcDAO
implements DBLoggerDAO
{
    private InsertLogProcedure insertLogProcedure;
    private final Executor executor;

    public DBLoggerDAOImpl(JdbcTemplate jdbcTemplate, String insertLogProcName)
    {
        super(jdbcTemplate);
        this.insertLogProcedure = new InsertLogProcedure(dataSource(), insertLogProcName);
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void insertLog(final AppLog applog)
    {
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                insertLogProcedure.insertLog(applog);
            }
        });
    }
}