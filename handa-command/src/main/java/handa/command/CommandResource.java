package handa.command;

import static handa.config.HandaCommandConstants.ALL;
import static handa.config.HandaCommandConstants.CITY;
import static handa.config.HandaCommandConstants.OK;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import handa.beans.dto.CallTree;
import handa.beans.dto.City;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
import handa.beans.dto.DistributionCustomGroup;
import handa.beans.dto.DistributionList;
import handa.beans.dto.LovItem;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.RegistrationAction;
import handa.beans.dto.RegistrationActionResult;
import handa.beans.dto.UserLocation;
import handa.beans.dto.UserLogin;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Path("command")
public class CommandResource
{
    static Logger log = LoggerFactory.getLogger(CommandResource.class);

    private CommandSmsSubResource commandSmsSubResource;
    private CommandAnalyticsSubResource commandAnalyticsSubResource;
    private CommandService commandService;
    private CommandUsersService usersService;

    @Autowired
    public CommandResource(CommandSmsSubResource commandSmsSubResource, CommandAnalyticsSubResource commandAnalyticsSubResource, CommandService commandService, CommandUsersService usersService)
    {
        this.commandSmsSubResource = commandSmsSubResource;
        this.commandAnalyticsSubResource = commandAnalyticsSubResource;
        this.commandService = commandService;
        this.usersService = usersService;
    }

    @Path("sms")
    public CommandSmsSubResource getCommandSmsResource()
    {
        return this.commandSmsSubResource;
    }

    @Path("analytics")
    public CommandAnalyticsSubResource getCommandAnalyticsResource()
    {
        return this.commandAnalyticsSubResource;
    }

    @POST
    @Path("login")
    public Response login(UserLogin userLogin)
    {
        boolean isAuthenticated = usersService.authenticate(userLogin);

        if(!isAuthenticated)
        {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("cities")
    public Response getCities()
    {
        List<City> result = commandService.getCities();
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("users/locations")
    public Response getUsersLocation(@DefaultValue(ALL) @QueryParam(CITY) String city)
    {
        List<UserLocation> result = commandService.getUsersLocations(city);
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("users/count")
    public Response getUsersCount(@DefaultValue(ALL) @QueryParam(CITY) String city)
    {
        int result = commandService.getUsersCount(city);
        return httpOk(result);
    }

    @GET
    @Path("users/registrations")
    public Response registrations(@QueryParam("approvalStatus") String approvalStatus)
    {
        List<Map<String, Object>> list = usersService.registrations(approvalStatus);
        return Response.ok().entity(list).build();
    }

    @GET
    @Path("users/registrations/{registrationId}")
    public Response registrations(@PathParam("registrationId") long registrationId)
    {
        List<Map<String, Object>> list = usersService.registrationsById(registrationId);
        return Response.ok().entity(list).build();
    }

    @POST
    @Path("users/registrations/{registrationId}")
    public Response registrationsAction(@PathParam("registrationId") long registrationId, RegistrationAction action)
    {
        RegistrationActionResult result = usersService.registrationsAction(registrationId, action);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("events/reset")
    public Response resetEvents(@QueryParam("resetBy") String resetBy)
    {
        commandService.resetEvents(resetBy);
        return Response.ok().build();
    }

    @GET
    @Path("sos")
    public Response getSos(@DefaultValue(ALL) @QueryParam(CITY) String city)
    {
        List<UserPrompt> result = commandService.getSos(city);
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
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
        return httpOk(rowsAffected);
    }

    @GET
    @Path("sos/count")
    public Response getSosCountPerCity(@DefaultValue(ALL) @QueryParam(CITY) String city)
    {
        int result = commandService.getSosCount(city);
        return httpOk(result);
    }

    @GET
    @Path("safe")
    public Response getSafe(@DefaultValue(ALL) @QueryParam(CITY) String city)
    {
        List<UserPrompt> result = commandService.getSafe(city);
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("safe/count")
    public Response getSafeCount(@DefaultValue(ALL) @QueryParam(CITY) String city)
    {
        int result = commandService.getSafeCount(city);
        return httpOk(result);
    }

    @GET
    @Path("noResponse")
    public Response getNoResponse(@DefaultValue(ALL) @QueryParam(CITY) String city)
    {
        List<UserPrompt> result = commandService.getNoResponse(city);
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("noResponse/count")
    public Response getNoResponseCount(@DefaultValue(ALL) @QueryParam(CITY) String city)
    {
        int result = commandService.getNoResponseCount(city);
        return httpOk(result);
    }

    @GET
    @Path("newsfeeds/{pageNo}")
    public Response getNewsFeeds(@PathParam("pageNo") int pageNo)
    {
        List<NewsFeed> result = commandService.getNewsFeeds(pageNo);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("newsfeeds")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response postNewsFeed(NewsFeed newsFeed)
    {
        NewsFeed result = commandService.postNewsFeed(newsFeed);
        return Response.ok(result).build();
    }

    @PUT
    @Path("newsfeeds")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response updateNewsFeed(NewsFeed newsFeed)
    {
        NewsFeed result = commandService.updateNewsFeed(newsFeed);
        return Response.ok(result).build();
    }

    @DELETE
    @Path("newsfeeds/{id}")
    public Response deleteNewsFeed(@PathParam("id") int id, @QueryParam("deletedBy") String deletedBy)
    {
        int rowsAffected = commandService.deleteNewsFeed(id, deletedBy);
        return httpOk(rowsAffected);
    }

    @GET
    @Path("newsfeeds/distributionList")
    public Response getNewsFeedDistributionList()
    {
        List<DistributionList> result = commandService.getNewsFeedsDistributionList();
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("newsfeeds/distributionList/custom")
    public Response addNewsFeedsCustomGroup(DistributionCustomGroup customGroup)
    {
        String result = commandService.addNewsFeedsCustomGroup(customGroup);
        return Response.ok(ImmutableMap.of("message", result)).build();
    }

    @PUT
    @Path("newsfeeds/distributionList/custom")
    public Response editNewsFeedsCustomGroup(DistributionCustomGroup customGroup)
    {
        String result = commandService.editNewsFeedsCustomGroup(customGroup);
        return Response.ok(ImmutableMap.of("message", result)).build();
    }

    @DELETE
    @Path("newsfeeds/distributionList/custom/{id}")
    public Response deleteNewsFeedsCustomGroup(@PathParam("id") long id, @QueryParam("deletedBy") String deletedBy)
    {
        String result = commandService.deleteNewsFeedsCustomGroup(id, deletedBy);
        return Response.ok(ImmutableMap.of("message", result)).build();
    }

    @GET
    @Path("newsfeeds/distributionList/custom")
    public Response getCustomNewsFeedDistributionList()
    {
        List<DistributionList> result = commandService.getCustomNewsFeedsDistributionList();
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("newsfeeds/distributionList/{distributionListCode}")
    public Response getNewsFeedsDistributionLov(@PathParam("distributionListCode") String distributionListCode)
    {
        List<LovItem> result = commandService.getNewsFeedsDistributionLov(distributionListCode);
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("reports")
    public Response getUserReports()
    {
        List<UserReport> result = commandService.getUserReports();
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("reports/{pageNo}")
    public Response getUserReports(@PathParam("pageNo") int pageNo)
    {
        List<UserReport> result = commandService.getUserReports(pageNo);
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("reports/count")
    public Response getReportsCount()
    {
        int result = commandService.getReportsCount();
        return httpOk(result);
    }

    @POST
    @Path("reports/{id}")
    public Response closeUserReport(@PathParam("id") int id, CloseUserReport closeUserReport)
    {
        int rowsAffected = commandService.closeUserReport(id, closeUserReport);
        return httpOk(rowsAffected);
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail,
                               @FormDataParam("filename") String name)
    {
        if(name == null)
        {
            name = fileDetail.getFileName();
        }
        String result = commandService.uploadFile(uploadedInputStream, name);
        return buildResponse(result);
    }

    @GET
    @Path("calltrees")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response listCallTree()
    {
        List<CallTree> result = commandService.listCallTree();
        return Response.ok(result).build();
    }

    @GET
    @Path("calltrees/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response getCallTreeById(@PathParam("id") long id)
    {
        Optional<CallTree> result = commandService.getCallTreeById(id);
        if(result.isPresent())
        {
            return Response.ok(result.get()).build();
        }
        return Response.status(Status.NOT_FOUND)
                       .entity(ImmutableMap.of("message", String.format("Calltree id [%s] not found", id)))
                       .build();
    }

    @POST
    @Path("calltrees")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response insertCallTree(CallTree callTree)
    {
        long result = commandService.insertCallTree(callTree);
        return Response.ok(ImmutableMap.of("id", result)).build();
    }

    @PUT
    @Path("calltrees")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response updateCallTree(CallTree callTree)
    {
        String result = commandService.updateCallTree(callTree);
        return Response.ok(ImmutableMap.of("message", result)).build();
    }

    @DELETE
    @Path("calltrees/{id}")
    public Response deleteCallTree(@PathParam("id") long id, @QueryParam("deletedBy") String deletedBy)
    {
        String result = commandService.deleteCallTree(id, deletedBy);
        return Response.ok(ImmutableMap.of("message", result)).build();
    }

    Response httpOk(Object result)
    {
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }

    Response buildResponse(String result)
    {
        switch(result)
        {
            case OK : return httpOk(result);
            default : return Response.status(Status.BAD_REQUEST).entity(result).type(MediaType.TEXT_PLAIN).build();
        }
    }
}