package handa.command;

import static handa.config.HandaCommandConstants.ALL;
import static handa.config.HandaCommandConstants.CITY;
import static handa.config.HandaCommandConstants.OK;
import handa.beans.dto.City;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.UserLocation;
import handa.beans.dto.UserLogin;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;

import java.io.InputStream;
import java.util.List;

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

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Path("command")
public class CommandResource
{
    static Logger log = LoggerFactory.getLogger(CommandResource.class);

    private CommandService commandService;
    private CommandUsersService usersService;

    @Autowired
    public CommandResource(CommandService commandService, CommandUsersService usersService)
    {
        this.commandService = commandService;
        this.usersService = usersService;
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
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }

    @POST
    @Path("events/reset")
    public Response resetEvents( @QueryParam("resetBy") String resetBy)
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
        return Response.ok().entity(rowsAffected).build();
    }

    @GET
    @Path("sos/count")
    public Response getSosCountPerCity(@DefaultValue(ALL) @QueryParam(CITY) String city)
    {
        int result = commandService.getSosCount(city);
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
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
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
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
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }

    @GET
    @Path("newsfeeds")
    public Response getNewsFeeds()
    {
        List<NewsFeed> result = commandService.getNewsFeeds();
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
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
        return Response.ok().entity(rowsAffected).build();
    }

    @GET
    @Path("reports")
    public Response getUserReports()
    {
        List<UserReport> result = commandService.getUserReports();
        if(result.isEmpty())
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("reports/count")
    public Response getReportsCount()
    {
        int result = commandService.getReportsCount();
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
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

    Response buildResponse(String result)
    {
        switch(result)
        {
            case OK : return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
            default : return Response.status(Status.BAD_REQUEST).entity(result).type(MediaType.TEXT_PLAIN).build();
        }
    }
}