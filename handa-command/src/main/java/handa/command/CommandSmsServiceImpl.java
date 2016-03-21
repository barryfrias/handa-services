package handa.command;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.beans.dto.AppLog;
import handa.beans.dto.LovItem;
import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.DistributionList;
import handa.beans.dto.SmsInboxMessage;
import handa.beans.dto.SmsOutboxMessage;
import handa.core.DBLoggerDAO;

@Component
public class CommandSmsServiceImpl
implements CommandSmsService
{
    final static Logger log = LoggerFactory.getLogger(CommandSmsServiceImpl.class);

    private CommandDAO commandDAO;
    private CommandSmsProcessor commandSmsProcessor;
    private DBLoggerDAO dbLoggerDAO;
    private ExecutorService executor;

    @Autowired
    public CommandSmsServiceImpl(CommandDAO commandDAO, DBLoggerDAO dbLoggerDAO, CommandSmsProcessor commandSmsProcessor)
    {
        this.commandDAO = commandDAO;
        this.dbLoggerDAO = dbLoggerDAO;
        this.commandSmsProcessor = commandSmsProcessor;
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public List<SmsInboxMessage> getSmsInbox()
    {
        return commandDAO.getSmsInbox();
    }

    @Override
    public int readSmsInbox(int id, ReadSms readSms)
    {
        int result = commandDAO.readSms(id, readSms);
        dbLoggerDAO.log(AppLog.server(readSms.getReadBy(), "Marked sms id %s as read and result was %s", id, result));
        return result;
    }

    @Override
    public int deleteSmsInbox(int id, String deletedBy)
    {
        int result = commandDAO.deleteSms(id, deletedBy);
        dbLoggerDAO.log(AppLog.server(deletedBy, "Deleted sms id %s and result was %s", id, result));
        return result;
    }

    @Override
    public String sendSms(SendSms sendSms)
    {
        String result = commandDAO.sendSms(sendSms);
        dbLoggerDAO.log(AppLog.server(sendSms.getCreatedBy(), "Tried to send sms to [%s] and result was %s", sendSms.getRecipients(), result));
        runOutboxProcessor();
        return result;
    }

    private void runOutboxProcessor()
    {
        executor.execute(new Runnable()
        {
            @Override
            public void run() { commandSmsProcessor.processOutbox(); }
        });
    }

    @Override
    public List<SmsOutboxMessage> getSmsOutbox()
    {
        return commandDAO.getSmsOutbox();
    }

    @Override
    public int deleteSmsOutbox(int id, String deletedBy)
    {
        int result = commandDAO.deleteSmsOutbox(id, deletedBy);
        dbLoggerDAO.log(AppLog.server(deletedBy, "Deleted sms outbox id %s and result was %s", id, result));
        return result;
    }

    @Override
    public List<DistributionList> getSmsDistributionList()
    {
        return commandDAO.getSmsDistributionList("default");
    }

    @Override
    public List<DistributionList> getCustomSmsDistributionList()
    {
        return commandDAO.getSmsDistributionList("custom");
    }

    @Override
    public List<LovItem> getSmsDistributionLov(String distributionListCode)
    {
        return commandDAO.getSmsDistributionLov(distributionListCode);
    }
}