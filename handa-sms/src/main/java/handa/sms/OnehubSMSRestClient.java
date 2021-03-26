package handa.sms;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import handa.beans.dto.SmsInbound;

public class OnehubSMSRestClient
{
    static Logger log = LoggerFactory.getLogger(OnehubSMSRestClient.class);

    private WebTarget wsTarget;

    public OnehubSMSRestClient(Client jerseyClient, String smsSenderUrl)
    {
        this.wsTarget = jerseyClient.target(smsSenderUrl);
    }

    public void forwardSMS(SmsInbound smsInbound)
    {
        checkNotNull(smsInbound, "smsInbound should not be null.");
        try
        {
            Form form = new Form();
            form.param("from", smsInbound.getMobileNumber());
            form.param("text", smsInbound.getMessage());
            // async call
            wsTarget.request().header("Content-Type", "application/x-www-form-urlencoded").async().post(Entity.form(form)).get().close();
            //wsTarget.request().header("Content-Type", "application/x-www-form-urlencoded").post(Entity.form(form)).close();
        }
        catch(Exception e)
        {
            log.error("SMS FORWARDING TO ONEHUB EXCEPTION", e);
        }
    }
}