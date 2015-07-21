package handa.sms;

import static handa.config.HandaSmsConstants.NA;
import handa.beans.dto.AppLog;
import handa.beans.dto.AppLog.Source;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.PromptCount;
import handa.core.DBLoggerDAO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsServiceImpl
implements SmsService
{
    final static Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    private SmsDAO commandDAO;
    private DBLoggerDAO dbLoggerDAO;

    @Autowired
    public SmsServiceImpl(SmsDAO commandDAO, DBLoggerDAO dbLoggerDAO)
    {
        this.commandDAO = commandDAO;
        this.dbLoggerDAO = dbLoggerDAO;
    }

    @Override
    public List<PromptCount> getSosCountPerCity()
    {
        return commandDAO.getSosCountPerCity();
    }

    @Override
    public int closePrompt(int id, ClosePrompt closePrompt)
    {
        int result = commandDAO.closePrompt(id, closePrompt);
        dbLoggerDAO.insertLog(new AppLog(Source.SERVER, closePrompt.getUsername(), NA,
                              String.format("Closed prompt id %s and result was %s", id, result)));
        return result;
    }
}