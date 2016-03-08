package handa.core;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.Executor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import handa.beans.dto.SendMailInput;

public class MailerRestClient
{
    private WebTarget wsTarget;
    private Executor executor;
    private String appCode;

    public MailerRestClient(Client jerseyClient, Executor executor, String mailerUrl, String appCode)
    {
        this.wsTarget = jerseyClient.target(mailerUrl);
        this.executor = executor;
        this.appCode = appCode;
    }

    public void sendMail(final String from, final String to, final String[] cc, final String[] bcc, final String subject, final String message, final boolean isHtml)
    {
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                checkNotNull(from, "from email address should not be null");
                checkNotNull(to, "to email address should not be null");
                checkNotNull(subject, "subject should not be null");
                checkNotNull(message, "message should not be null");
                SendMailInput mailPayload = new SendMailInput();
                mailPayload.setAppCode(appCode);
                mailPayload.setFrom(from);
                mailPayload.setTo(new String[] {to});
                if(cc != null && cc.length > 0 ) mailPayload.setCc(cc);
                if(bcc != null && bcc.length > 0 ) mailPayload.setBcc(bcc);
                mailPayload.setSubject(subject);
                mailPayload.setMessage(message);
                mailPayload.setHtml(isHtml);
                Response response = wsTarget.request().header("Content-Type", "application/json")
                                                      .post(Entity.json(mailPayload));
                if(response.getStatus() != 200)
                {
                    final String errMsg = String.format("Mail sending failed! Response from mailer web service was code: %s, response string: %s",
                                                        response.getStatus(), response.readEntity(String.class));
                    throw new RuntimeException(errMsg);
                }
                response.close();
            }
        });
    }
}