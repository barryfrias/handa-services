package handa.sms;

import static handa.config.HandaSmsConstants.OK;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.PromptCount;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Path("sms")
public class SmsResource
{
    static Logger log = LoggerFactory.getLogger(SmsResource.class);

    private SmsService commandService;

    @Autowired
    public SmsResource(SmsService commandService)
    {
        this.commandService = commandService;
    }

    @GET
    @Path("sos/countPerCity")
    public Response getSosCountPerCity()
    {
        List<PromptCount> promptCounts = commandService.getSosCountPerCity();
        if(promptCounts.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(promptCounts).build();
    }

    @POST
    @Path("sos/{id}")
    public Response closePrompt(@PathParam("id") int id, ClosePrompt closePrompt)
    {
        int rowsAffected = commandService.closePrompt(id, closePrompt);
        return Response.ok().entity(rowsAffected).build();
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