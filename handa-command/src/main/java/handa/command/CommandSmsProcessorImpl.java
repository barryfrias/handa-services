package handa.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itmss.core.utils.AbstractJdbcDAO;

import handa.beans.dto.AppLog;
import handa.command.procs.ProcessSmsOutboxProcedure;
import handa.core.DBLoggerDAO;

@Component
public class CommandSmsProcessorImpl
extends AbstractJdbcDAO
implements CommandSmsProcessor
{
    private ProcessSmsOutboxProcedure processSmsOutboxProcedure;
    private DBLoggerDAO dbLoggerDAO;

    @Autowired
    public CommandSmsProcessorImpl(JdbcTemplate jdbcTemplate,
                                   DBLoggerDAO dbLoggerDAO,
                                   @Value("${handa.command.process.sms.outbox.proc}") String processSmsOutboxProcName)
    {
        super(jdbcTemplate);
        this.dbLoggerDAO = dbLoggerDAO;
        this.processSmsOutboxProcedure = new ProcessSmsOutboxProcedure(dataSource(), processSmsOutboxProcName);
    }

    @Override
    public void processOutbox()
    {
        int processedCount = processSmsOutboxProcedure.call();
        if(processedCount > 0)
        {
            dbLoggerDAO.log(AppLog.server("system", String.format("Processed sms outbox. Count: %s", processedCount)));
        }
    }
}