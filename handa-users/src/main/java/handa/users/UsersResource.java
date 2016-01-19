package handa.users;

import static com.pldt.itidm.core.utils.ResponseUtils.buildResponse;
import static handa.config.HandaUsersConstants.OK;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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
import com.pldt.itidm.core.exception.NotFoundException;

import handa.beans.dto.AuthInfo;
import handa.beans.dto.City;
import handa.beans.dto.DeviceInfo;
import handa.beans.dto.LdapUser;
import handa.beans.dto.LdapUserSearch;
import handa.beans.dto.Province;
import handa.beans.dto.User;
import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;
import handa.beans.dto.UserSearch;
import handa.config.HandaUsersConstants;

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Path("users")
public class UsersResource
{
    static Logger log = LoggerFactory.getLogger(UsersResource.class);

    private UsersService usersService;

    @Autowired
    public UsersResource(UsersService usersService)
    {
        this.usersService = usersService;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("authenticate")
    public Response authenticate(AuthInfo authInfo)
    {
        String result = usersService.authByMobileNumber(authInfo);
        switch(result)
        {
            case OK : return Response.ok().build();
            default : return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response addUser(User user)
    {
        String result = usersService.addUser(user);
        ImmutableMap<String, String> jsonMessage = ImmutableMap.of("message", result);
        if("Successfully added".equals(result))
        {
            return Response.ok(jsonMessage).build();
        }
        return Response.status(Status.BAD_REQUEST).entity(jsonMessage).build();
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response editUser(User user)
    {
        String result = usersService.editUser(user);
        ImmutableMap<String, String> jsonMessage = ImmutableMap.of("message", result);
        if("Successfully modified".equals(result))
        {
            return Response.ok(jsonMessage).build();
        }
        return Response.status(Status.BAD_REQUEST).entity(jsonMessage).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("authenticate2")
    public Response authenticate2(AuthInfo authInfo)
    {
        String result = usersService.authByMobileNumberAndUsername(authInfo);
        switch(result)
        {
            case OK : return Response.ok().build();
            default : return Response.status(Status.UNAUTHORIZED).entity(result).type(MediaType.TEXT_PLAIN).build();
        }
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("sos")
    public Response sos(UserPrompt usersPrompt)
    {
        String result = usersService.prompt(usersPrompt, HandaUsersConstants.PromptType.SOS);
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("safe")
    public Response safe(UserPrompt usersPrompt)
    {
        String result = usersService.prompt(usersPrompt, HandaUsersConstants.PromptType.SAFE);
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("report")
    public Response report(@Context HttpHeaders headers, UserReport userReport)
    {
        String result = usersService.report(DeviceInfo.from(headers), userReport);
        return buildResponse(result);
    }

    @GET
    public Response getUsers()
    {
        List<UserInfo> result = usersService.getUsers();
        if(!result.isEmpty())
        {
            return Response.ok(result).build();
        }
        throw new NotFoundException(String.format("No users"));
    }

    @GET
    @Path("{mobileNumber}")
    public Response getUserInfo(@PathParam("mobileNumber") String mobileNumber)
    {
        Optional<UserInfo> result = usersService.getUserInfo(mobileNumber);
        if(result.isPresent())
        {
            return Response.ok(result.get()).build();
        }
        throw new NotFoundException(String.format("No user info found for mobile number %s", mobileNumber));
    }

    @POST
    @Path("ldap/search")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response ldapSearchUser(LdapUserSearch userSearch)
    {
        Optional<LdapUser> result = usersService.ldapSearchUser(userSearch);
        if(result.isPresent())
        {
            return Response.ok(result.get()).build();
        }
        throw new NotFoundException(String.format("Search returned no results."));
    }

    @POST
    @Path("searchByName")
    public Response searchByName(UserSearch userSearch)
    {
        List<UserInfo> result = usersService.searchByName(userSearch);
        if(result != null && !result.isEmpty())
        {
            return Response.ok(result).build();
        }
        throw new NotFoundException(String.format("Search returned no results."));
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
        String result = usersService.uploadFile(uploadedInputStream, name);
        return buildResponse(result);
    }

    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("app/versions/{versionString}")
    public Response checkVersion(@PathParam("versionString") String versionString)
    {
        String result = usersService.checkAppVersion(versionString);
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }

    @GET
    @Path("cities")
    public Response getCitiesLov()
    {
        List<City> result = usersService.getCitiesLov();
        return Response.ok(result).build();
    }

    @GET
    @Path("provinces")
    public Response getProvincesLov()
    {
        List<Province> result = usersService.getProvincesLov();
        return Response.ok(result).build();
    }
}