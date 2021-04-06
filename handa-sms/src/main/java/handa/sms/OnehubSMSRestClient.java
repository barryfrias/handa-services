package handa.sms;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import handa.beans.dto.OnehubToken;
import handa.beans.dto.SmsInbound;

public class OnehubSMSRestClient
{
    static Logger log = LoggerFactory.getLogger(OnehubSMSRestClient.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static String token;

    private WebTarget wsTarget;
    private WebTarget oauthTarget;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;

    public OnehubSMSRestClient(Client jerseyClient, String smsSenderUrl, String oauthUrl, String clientId, String clientSecret, String username, String password)
    {
        this.wsTarget = jerseyClient.target(smsSenderUrl);
        this.oauthTarget = jerseyClient.target(oauthUrl);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.username = username;
        this.password = password;
    }

    private String getToken() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        Form form = new Form();
        form.param("grant_type", "password");
        form.param("client_id", this.clientId);
        form.param("client_secret", this.clientSecret);
        form.param("username", this.username);
        form.param("password", this.password);
        Response response = oauthTarget.request().header("Content-Type", "application/x-www-form-urlencoded").post(Entity.form(form));
        String body = response.readEntity(String.class);
        if(response.getStatus() == 200)
        {
            final OnehubToken resultResponse = MAPPER.readValue(body, OnehubToken.class);
            return resultResponse.getAccessToken();
        }
        response.close();
        return null;
    }

    public void forwardSMS(SmsInbound smsInbound)
    {
        checkNotNull(smsInbound, "smsInbound should not be null.");
        try
        {
            if(null == token)
            {
                token = getToken();
            }
            Form form = new Form();
            form.param("from", smsInbound.getMobileNumber());
            form.param("text", smsInbound.getMessage());
            // async call
            Response response = wsTarget.request().header("Content-Type", "application/x-www-form-urlencoded")
                              .header("Authorization", "Bearer " + token)
                              .async().post(Entity.form(form)).get();
            if(response.getStatus() == 401)
            {
                response.close();
                token = getToken(); //get new token
                //retry call
                wsTarget.request().header("Content-Type", "application/x-www-form-urlencoded").header("Authorization", "Bearer " + token)
                        .async().post(Entity.form(form)).get().close();;
            }
            response.close();
        }
        catch(Exception e)
        {
            log.error("SMS FORWARDING TO ONEHUB EXCEPTION", e);
        }
    }
}