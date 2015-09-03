package handa.sms;

import javax.xml.soap.SOAPBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pldt.itmss.core.utils.SoapWsCaller;
import com.pldt.itmss.core.utils.SoapWsCaller.WSParams;

import handa.beans.dto.SmsInbound;
import com.google.common.base.Optional;
import handa.beans.dto.SendSmsInput;
import handa.beans.dto.SendSmsOutput;

@Component
public class SmsServiceImpl
implements SmsService
{
    final static Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    private SmsDAO smsDAO;
    private SoapWsCaller<SendSmsInput, SendSmsOutput, SOAPBody> sendSmsWsCaller;

    @Autowired
    public SmsServiceImpl(SmsDAO commandDAO,
                          @Value("${sms.smart.ws.url}") String smartWsUrl,
                          @Value("${sms.smart.ws.soap.action}") String smartWsSoapAction)
    {
        this.smsDAO = commandDAO;
        this.sendSmsWsCaller = new SendSmsWsCaller(new WSParams(smartWsUrl, smartWsSoapAction));
    }

    @Override
    public String receive(SmsInbound smsInbound)
    {
        return smsDAO.receive(smsInbound);
    }

    @Override
    public Optional<SendSmsOutput> send(SendSmsInput sendSmsInput)
    {
        Optional<SendSmsOutput> output = sendSmsWsCaller.call(Optional.of(sendSmsInput));
        return output;
    }
}