package handa.sms;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.xml.soap.SOAPBody;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    static Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    private SmsDAO smsDAO;
    private DBLoggerDAO dbLog;
    private SoapWsCaller<SendSmsInput, SendSmsOutput, SOAPBody> sendSmsWsCaller;
    private OnehubSMSRestClient onehubSMSRestClient;
    private boolean isSMSForwardinEnabled;
 
    @Autowired
    public SmsServiceImpl(SmsDAO commandDAO, HandaProperties handaProperties, DBLoggerDAO dbLog, Client proxiedJerseyClient)
    {
        this.smsDAO = commandDAO;
        this.dbLog = dbLog;
        String smartWsUrl = handaProperties.get("sms.smart.ws.url");
        String smartWsSoapAction = handaProperties.get("sms.smart.ws.soap.action");
        this.sendSmsWsCaller = new SendSmsWsCaller(new WSParams(smartWsUrl, smartWsSoapAction));
        this.isSMSForwardinEnabled = Boolean.valueOf(handaProperties.get("onehub.sms.forwarding.enabled"));
        if(isSMSForwardinEnabled)
        {
            log.info("SMS Forwarding to OneHub is enabled");
        }
        else
        {
            log.info("SMS Forwarding to OneHub is disabled");
        }
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.PROXY_URI, handaProperties.get("http.proxy"));
        this.onehubSMSRestClient = new OnehubSMSRestClient(ClientBuilder.newClient(clientConfig), handaProperties.get("onehub.sms.ws.url"));
    }

    @Override
    public String receive(SmsInbound smsInbound)
    {
        String result = smsDAO.receive(smsInbound);
        if(isSMSForwardinEnabled)
        {
            onehubSMSRestClient.forwardSMS(smsInbound);
        }
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