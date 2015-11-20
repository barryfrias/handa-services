package handa.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import handa.beans.dto.AppLog;

import org.springframework.jdbc.core.JdbcTemplate;

import com.pldt.itidm.core.utils.AbstractJdbcDAO;

public class DBLoggerDAOImpl
extends AbstractJdbcDAO
implements DBLoggerDAO
{
    private InsertLogProcedure insertLogProcedure;
    private final Executor executor;

    public DBLoggerDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.insertLogProcedure = new InsertLogProcedure(dataSource());
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void log(final AppLog applog)
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