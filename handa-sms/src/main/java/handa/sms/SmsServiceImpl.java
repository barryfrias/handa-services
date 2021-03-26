package handa.sms;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.xml.soap.SOAPBody;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
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
    private boolean isSMSForwardingEnabled;
    private static final SSLContext SSL_CTX;
    private static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() { public boolean verify(String S1, SSLSession S2) { return true; } };

    static
    {
        try
        {
            SSL_CTX = SSLContext.getInstance("TLSv1.2");
            SSL_CTX.init(null, new TrustManager[]{new X509TrustManager()
            {
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
            }}
            , new java.security.SecureRandom());
        }
        catch(Exception e) { throw new RuntimeException(e); }
    }
 
    @Autowired
    public SmsServiceImpl(SmsDAO commandDAO, HandaProperties handaProperties, DBLoggerDAO dbLog)
    {
        this.smsDAO = commandDAO;
        this.dbLog = dbLog;
        String smartWsUrl = handaProperties.get("sms.smart.ws.url");
        String smartWsSoapAction = handaProperties.get("sms.smart.ws.soap.action");
        this.sendSmsWsCaller = new SendSmsWsCaller(new WSParams(smartWsUrl, smartWsSoapAction));
        this.isSMSForwardingEnabled = Boolean.valueOf(handaProperties.get("onehub.sms.forwarding.enabled"));
        log.info("SMS Forwarding to OneHub is " + (isSMSForwardingEnabled? "enabled" : "disabled"));
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.PROXY_URI, "http://" + handaProperties.get("http.proxy"));
        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 30000);
        clientConfig.property(ClientProperties.READ_TIMEOUT, 30000);
        clientConfig.connectorProvider(new ApacheConnectorProvider());
        Client client = ClientBuilder.newBuilder().withConfig(clientConfig).sslContext(SSL_CTX).hostnameVerifier(HOSTNAME_VERIFIER).build();
        this.onehubSMSRestClient = new OnehubSMSRestClient(client, handaProperties.get("onehub.sms.ws.url"));
    }

    @Override
    public String receive(SmsInbound smsInbound)
    {
        String result = smsDAO.receive(smsInbound);
        if(isSMSForwardingEnabled)
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