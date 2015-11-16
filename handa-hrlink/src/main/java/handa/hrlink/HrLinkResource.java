package handa.hrlink;

import static com.pldt.itidm.core.utils.ResponseUtils.buildResponse;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import handa.beans.dto.User;

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Path("hrlink")
public class HrLinkResource
{
    static Logger log = LoggerFactory.getLogger(HrLinkResource.class);

    private HrLinkService hrLinkService;

    @Autowired
    public HrLinkResource(HrLinkService hrLinkService)
    {
        this.hrLinkService = hrLinkService;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response addUser(User user)
    {
        String result = hrLinkService.addUser(user);
        ImmutableMap<String, String> jsonMessage = ImmutableMap.of("message", result);
        if("Successfully added".equals(result))
        {
            return Response.ok(jsonMessage).build();
        }
        return Response.status(Status.BAD_REQUEST).entity(jsonMessage).build();
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
        return buildResponse((String)null);
    }
}