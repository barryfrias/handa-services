package handa.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.beans.dto.EventReport;
import handa.beans.dto.ReportInput;
import handa.procs.ReportsByEventProcedure;

@Component
public class CommandAnalyticsDAOImpl
extends AbstractJdbcDAO
implements CommandAnalyticsDAO
{
    private final ReportsByEventProcedure reportsByEventProcedure;

    @Autowired
    public CommandAnalyticsDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.reportsByEventProcedure = new ReportsByEventProcedure(dataSource());
    }

    @Override
    public EventReport byEvent(ReportInput reportInput)
    {
        return reportsByEventProcedure.get(reportInput);
    }
}