package handa.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.beans.dto.SmsInbound;

@Component
public class SmsServiceImpl
implements SmsService
{
    final static Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    private SmsDAO smsDAO;

    @Autowired
    public SmsServiceImpl(SmsDAO commandDAO)
    {
        this.smsDAO = commandDAO;
    }

    @Override
    public String receive(SmsInbound smsInbound)
    {
        return smsDAO.receive(smsInbound);
    }
}