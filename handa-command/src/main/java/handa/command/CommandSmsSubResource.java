package handa.command;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import handa.beans.dto.DistributionCustomGroup;
import handa.beans.dto.DistributionList;
import handa.beans.dto.LovItem;
import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.SmsInboxMessage;
import handa.beans.dto.SmsOutboxMessage;

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
public class CommandSmsSubResource
{
    private CommandSmsService commandSmsService;

    @Autowired
    public CommandSmsSubResource(CommandSmsService commandSmsService)
    {
        this.commandSmsService = commandSmsService;
    }

    @GET
    @Path("inbox")
    public Response getSmsInbox()
    {
        List<SmsInboxMessage> result = commandSmsService.getSmsInbox();
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("inbox/{id}")
    public Response readSmsInbox(@PathParam("id") int id, ReadSms readSms)
    {
        int result = commandSmsService.readSmsInbox(id, readSms);
        return httpOk(result);
    }

    @DELETE
    @Path("inbox/{id}")
    public Response deleteSmsInbox(@PathParam("id") int id, @QueryParam("deletedBy") String deletedBy)
    {
        int rowsAffected = commandSmsService.deleteSmsInbox(id, deletedBy);
        return httpOk(rowsAffected);
    }

    @GET
    @Path("outbox")
    public Response getSmsOutbox()
    {
        List<SmsOutboxMessage> result = commandSmsService.getSmsOutbox();
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("outbox")
    public Response sendSms(SendSms sendSms)
    {
        String result = commandSmsService.sendSms(sendSms);
        return httpOk(result);
    }

    @DELETE
    @Path("outbox/{id}")
    public Response deleteSmsOutbox(@PathParam("id") int id, @QueryParam("deletedBy") String deletedBy)
    {
        int rowsAffected = commandSmsService.deleteSmsOutbox(id, deletedBy);
        return httpOk(rowsAffected);
    }

    @GET
    @Path("distributionList")
    public Response getSmsDistributionList()
    {
        List<DistributionList> result = commandSmsService.getSmsDistributionList();
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("distributionList/{distributionListCode}")
    public Response getSmsDistributionLov(@PathParam("distributionListCode") String distributionListCode)
    {
        List<LovItem> result = commandSmsService.getSmsDistributionLov(distributionListCode);
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("distributionList/custom")
    public Response getCustomSmsList()
    {
        List<DistributionList> result = commandSmsService.getCustomSmsDistributionList();
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("distributionList/custom")
    public Response addSmsCustomGroup(DistributionCustomGroup customGroup)
    {
        String result = commandSmsService.addSmsCustomGroup(customGroup);
        return Response.ok(ImmutableMap.of("message", result)).build();
    }

    @PUT
    @Path("distributionList/custom")
    public Response editSmsCustomGroup(DistributionCustomGroup customGroup)
    {
        String result = commandSmsService.editSmsCustomGroup(customGroup);
        return Response.ok(ImmutableMap.of("message", result)).build();
    }

    @DELETE
    @Path("distributionList/custom/{id}")
    public Response deleteSmsCustomGroup(@PathParam("id") long id, @QueryParam("deletedBy") String deletedBy)
    {
        String result = commandSmsService.deleteSmsCustomGroup(id, deletedBy);
        return Response.ok(ImmutableMap.of("message", result)).build();
    }

    private Response httpOk(Object result)
    {
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }
}