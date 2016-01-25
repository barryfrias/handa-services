package handa.core;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import handa.beans.dto.SendSmsInput2;

public class TexterRestClient
{
    static Logger log = LoggerFactory.getLogger(TexterRestClient.class);

    private WebTarget wsTarget;
    private String appCode;
    private String keyword;

    public TexterRestClient(Client jerseyClient, String smsSenderUrl, String smsKeyword, String appCode)
    {
        this.wsTarget = jerseyClient.target(smsSenderUrl);
        this.appCode = appCode;
        this.keyword = smsKeyword;
    }

    public void sendSms(String mobileNumber, String message)
    {
        checkNotNull(mobileNumber, "mobileNumber should not be null.");
        checkNotNull(message, "message should not be null.");
        SendSmsInput2 smsPayload = new SendSmsInput2();
        smsPayload.setAppCode(this.appCode);
        smsPayload.setKeyword(this.keyword);
        smsPayload.setMobile(mobileNumber);
        smsPayload.setMessage(message);
        try
        {
            // async call
            wsTarget.request().header("Content-Type", "application/json").async().post(Entity.json(smsPayload)).get().close();
        }
        catch(Exception e)
        {
            log.error("SMS SENDING EXCEPTION", e);
        }
    }
}