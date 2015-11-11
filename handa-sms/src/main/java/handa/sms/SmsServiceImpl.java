package handa.sms;

import javax.xml.soap.SOAPBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.pldt.itidm.core.utils.SoapWsCaller;
import com.pldt.itidm.core.utils.SoapWsCaller.WSParams;

import handa.beans.dto.AppLog;
import handa.beans.dto.SendSmsInput;
import handa.beans.dto.SendSmsOutput;
import handa.beans.dto.SmsInbound;
import handa.core.DBLoggerDAO;
import handa.core.HandaProperties;

@Component
public class SmsServiceImpl
implements SmsService
{
    private SmsDAO smsDAO;
    private DBLoggerDAO dbLog;
    private SoapWsCaller<SendSmsInput, SendSmsOutput, SOAPBody> sendSmsWsCaller;

    @Autowired
    public SmsServiceImpl(SmsDAO commandDAO, HandaProperties handaProperties, DBLoggerDAO dbLog)
    {
        this.smsDAO = commandDAO;
        this.dbLog = dbLog;
        String smartWsUrl = handaProperties.get("sms.smart.ws.url");
        String smartWsSoapAction = handaProperties.get("sms.smart.ws.soap.action");
        this.sendSmsWsCaller = new SendSmsWsCaller(new WSParams(smartWsUrl, smartWsSoapAction));
    }

    @Override
    public String receive(SmsInbound smsInbound)
    {
        String result = smsDAO.receive(smsInbound);
        dbLog.log(AppLog.server("SmsService", "Received inbound sms from %s", smsInbound.getMobileNumber()));
        return result;
    }

    @Override
    public Optional<SendSmsOutput> send(SendSmsInput sendSmsInput)
    {
        Optional<SendSmsOutput> output = sendSmsWsCaller.call(Optional.of(sendSmsInput));
        return output;
    }
}