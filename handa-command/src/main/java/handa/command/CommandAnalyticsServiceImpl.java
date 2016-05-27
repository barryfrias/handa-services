package handa.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.beans.dto.EventReport;
import handa.beans.dto.ReportInput;

@Component
public class CommandAnalyticsServiceImpl
implements CommandAnalyticsService
{
    final static Logger log = LoggerFactory.getLogger(CommandAnalyticsServiceImpl.class);

    private CommandAnalyticsDAO commandAnalyticsDAO;

    @Autowired
    public CommandAnalyticsServiceImpl(CommandAnalyticsDAO commandAnalyticsDAO)
    {
        this.commandAnalyticsDAO = commandAnalyticsDAO;
    }

    @Override
    public EventReport byEvent(ReportInput reportInput)
    {
        return commandAnalyticsDAO.byEvent(reportInput);
    }
}