package handa.command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.beans.dto.AppLog;
import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.SmsInboxMessage;
import handa.beans.dto.SmsOutboxMessage;
import handa.core.DBLoggerDAO;

@Component
public class CommandSmsServiceImpl
implements CommandSmsService
{
    final static Logger log = LoggerFactory.getLogger(CommandSmsServiceImpl.class);

    private CommandDAO commandDAO;
    private DBLoggerDAO dbLoggerDAO;

    @Autowired
    public CommandSmsServiceImpl(CommandDAO commandDAO, DBLoggerDAO dbLoggerDAO)
    {
        this.commandDAO = commandDAO;
        this.dbLoggerDAO = dbLoggerDAO;
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
        dbLoggerDAO.log(AppLog.server(readSms.getReadBy(), String.format("Marked sms id %s as read and result was %s", id, result)));
        return result;
    }

    @Override
    public int deleteSmsInbox(int id, String deletedBy)
    {
        int result = commandDAO.deleteSms(id, deletedBy);
        dbLoggerDAO.log(AppLog.server(deletedBy, String.format("Deleted sms id %s and result was %s", id, result)));
        return result;
    }

    @Override
    public String sendSms(SendSms sendSms)
    {
        String result = commandDAO.sendSms(sendSms);
        dbLoggerDAO.log(AppLog.server(sendSms.getCreatedBy(), String.format("Tried to send sms to [%s] and result was %s", sendSms.getRecipients(), result)));
        return result;
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
        dbLoggerDAO.log(AppLog.server(deletedBy, String.format("Deleted sms outbox id %s and result was %s", id, result)));
        return result;
    }
}