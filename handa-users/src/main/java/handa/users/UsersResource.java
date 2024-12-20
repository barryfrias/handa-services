package handa.users;

import static com.pldt.itidm.core.utils.ResponseUtils.buildResponse;
import static handa.config.HandaUsersConstants.COMPANY;
import static handa.config.HandaUsersConstants.END_DATE;
import static handa.config.HandaUsersConstants.INVALID_CREDENTIALS;
import static handa.config.HandaUsersConstants.MGR_USERNAME;
import static handa.config.HandaUsersConstants.OK;
import static handa.config.HandaUsersConstants.START_DATE;
import static handa.config.HandaUsersConstants.TYPE;
import static handa.config.HandaUsersConstants.USER_NOT_FOUND;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

import handa.beans.dto.ActivityLog;
import handa.beans.dto.AuthInfo;
import handa.beans.dto.Cmp;
import handa.beans.dto.Company;
import handa.beans.dto.DeviceInfo;
import handa.beans.dto.LdapUser;
import handa.beans.dto.LdapUserSearch;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.Subordinates;
import handa.beans.dto.User;
import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPromptInput;
import handa.beans.dto.UserRegistration;
import handa.beans.dto.UserReportInput;
import handa.beans.dto.UserSearch;
import handa.beans.dto.UserVerificationResult;
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
    public Response authenticate(@Context HttpHeaders headers, AuthInfo authInfo)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = usersService.authByMobileNumber(authInfo, deviceInfo);
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

    @DELETE
    @Path("{mobileNumber}")
    public Response deleteUser(@PathParam("mobileNumber") String mobileNumber,
                               @QueryParam("createdDate") String createdDate,
                               @QueryParam("deletedBy") String deletedBy)
    {
        String result = usersService.deleteUser(mobileNumber, createdDate, deletedBy);
        ImmutableMap<String, String> jsonMessage = ImmutableMap.of("message", result);
        if("Successfully deleted".equals(result))
        {
            return Response.ok(jsonMessage).build();
        }
        return Response.status(Status.BAD_REQUEST).entity(jsonMessage).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("authenticate2")
    public Response authenticate2(@Context HttpHeaders headers, AuthInfo authInfo)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = usersService.authByMobileNumberAndUsernamePassword(authInfo, deviceInfo);
        switch(result)
        {
            case OK : return Response.ok().build();
            default : return Response.status(Status.UNAUTHORIZED).entity(result).type(MediaType.TEXT_PLAIN).build();
        }
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("authenticate3")
    public Response authenticate3(@Context HttpHeaders headers, AuthInfo authInfo)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = usersService.loginByPasscode(authInfo, deviceInfo);
        switch(result)
        {
            case OK : return Response.ok().build();
            default : return Response.status(Status.UNAUTHORIZED).entity(result).type(MediaType.TEXT_PLAIN).build();
        }
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("authenticate4")
    public Response authenticate4(@Context HttpHeaders headers, AuthInfo authInfo)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = usersService.authByMobileNumberAndUsername(authInfo, deviceInfo);
        switch(result)
        {
            case OK :
            { 
                Optional<UserInfo> userInfo = usersService.getUserInfo(authInfo.getMobileNumber());
                if(userInfo.isPresent())
                {
                    return Response.ok(userInfo.get()).build();
                }
                throw new NotFoundException(String.format("No user info found for mobile number %s", authInfo.getMobileNumber()));
            }
            default : return Response.status(Status.UNAUTHORIZED).entity(result).type(MediaType.TEXT_PLAIN).build();
        }
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("verify")
    public Response verify(@Context HttpHeaders headers, AuthInfo authInfo)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        UserVerificationResult result = usersService.verify(authInfo, deviceInfo);
        return Response.ok(result).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("sos")
    public Response sos(@Context HttpHeaders headers, UserPromptInput usersPromptInput)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = usersService.prompt(usersPromptInput, HandaUsersConstants.PromptType.SOS, deviceInfo);
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("safe")
    public Response safe(@Context HttpHeaders headers, UserPromptInput usersPromptInput)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = usersService.prompt(usersPromptInput, HandaUsersConstants.PromptType.SAFE, deviceInfo);
        return Response.status(Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("report")
    public Response report(@Context HttpHeaders headers, UserReportInput userReportInput)
    {
        String result = usersService.report(DeviceInfo.from(headers), userReportInput);
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
        throw new NotFoundException("No users");
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
        throw new NotFoundException("Search returned no results.");
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
        throw new NotFoundException("Search returned no results.");
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
    @Path("companies")
    public Response getCompaniesLov()
    {
        List<Company> result = usersService.getCompaniesLov();
        return Response.ok(result).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("registration")
    public Response register(@Context HttpHeaders headers, UserRegistration registration)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = usersService.register(registration, deviceInfo);
        return Response.ok(ImmutableMap.of("message", result)).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("registration/domainUser")
    public Response registerDomainUser(@Context HttpHeaders headers, UserRegistration userRegistration)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = usersService.registerDomainUser(userRegistration, deviceInfo);
        Map<String, String> jsonMessage = ImmutableMap.of("message", result);
        if(INVALID_CREDENTIALS.equals(result))
        {
            return Response.status(Status.UNAUTHORIZED).entity(jsonMessage).build();
        }
        if(USER_NOT_FOUND.equals(result))
        {
            return Response.status(Status.NOT_FOUND).entity(jsonMessage).build();
        }
        return Response.ok(jsonMessage).build();
    }

    @GET
    @Path("newsfeeds/{username}/{pageNo}")
    public Response getPrivateNewsFeeds(@PathParam("username") String username, @PathParam("pageNo") int pageNo)
    {
        List<NewsFeed> result = usersService.getPrivateNewsFeeds(username, pageNo);
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("newsfeeds-mobile/public/{username}/{pageNo}")
    public Response getPublicNewsFeedsMobile(@PathParam("username") String username, @PathParam("pageNo") int pageNo)
    {
        List<NewsFeed> result = usersService.getPublicNewsFeedsMobile(username, pageNo);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("newsfeeds-mobile/search/public/{username}")
    public Response searchPublicNewsFeedsMobile(@PathParam("username") String username, @PathParam("pageNo") int pageNo, Map<String, Object> json)
    {
        List<NewsFeed> result = usersService.searchPublicNewsFeedsMobile(username, json);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("newsfeeds-mobile/search/private/{username}")
    public Response searchPrivateNewsFeedsMobile(@PathParam("username") String username, @PathParam("pageNo") int pageNo, Map<String, Object> json)
    {
        List<NewsFeed> result = usersService.searchPrivateNewsFeedsMobile(username, json);
        return Response.ok().entity(result).build();
    }


    @GET
    @Path("newsfeeds-mobile/private/{username}/{pageNo}")
    public Response getPrivateNewsFeedsMobile(@PathParam("username") String username, @PathParam("pageNo") int pageNo)
    {
        List<NewsFeed> result = usersService.getPrivateNewsFeedsMobile(username, pageNo);
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("newsfeeds/tips/{username}/{pageNo}")
    public Response getPrivateTips(@PathParam("username") String username, @PathParam("pageNo") int pageNo)
    {
        List<NewsFeed> result = usersService.getPrivateTips(username, pageNo);
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("{mgrUsername}/subordinates/prompts")
    public Response getSubordinates(@PathParam(MGR_USERNAME) String mgrUsername,
                                    @QueryParam(COMPANY) String company,
                                    @QueryParam(START_DATE) String startDate,
                                    @QueryParam(END_DATE) String endDate)
    {
        Subordinates result = usersService.getSubordinates(mgrUsername, company, startDate, endDate);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("privacy/tag")
    public Response privacyTag(AuthInfo authInfo)
    {
    	 String result = usersService.privacyTagByMIN(authInfo);
    	 return Response.ok(ImmutableMap.of("message", result)).build();
     }

    @GET
    @Path("activity/logs/{mobileNumber}/{pageNo}")
    public Response getActivityLogs(@PathParam("mobileNumber") String mobileNumber,
                                    @PathParam("pageNo") int pageNo,
                                    @QueryParam(TYPE) String type,
                                    @QueryParam(START_DATE) String startDate,
                                    @QueryParam(END_DATE) String endDate)
    {
        List<ActivityLog> result = usersService.getActivityLogs(mobileNumber, type, startDate, endDate, pageNo);
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("cmp/{username}")
    public Response getCmp(@PathParam("username") String username)
    {
        List<Cmp> result = usersService.getCmp(username);
        return Response.ok().entity(result).build();
    }
}