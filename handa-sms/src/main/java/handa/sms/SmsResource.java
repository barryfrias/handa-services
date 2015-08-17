package handa.sms;

import static handa.config.HandaSmsConstants.OK;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.beans.dto.SmsInbound;

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
@Path("sms")
public class SmsResource
{
    static Logger log = LoggerFactory.getLogger(SmsResource.class);

    private SmsService smsService;

    @Autowired
    public SmsResource(SmsService commandService)
    {
        this.smsService = commandService;
    }

    @POST
    @Path("receive")
    public Response receive(SmsInbound smsInbound)
    {
        String result = smsService.receive(smsInbound);
        return buildResponse(result);
    }

    Response buildResponse(String result)
    {
        switch(result)
        {
            case OK : return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
            default : return Response.status(Status.BAD_REQUEST).entity(result).type(MediaType.TEXT_PLAIN).build();
        }
    }
} 