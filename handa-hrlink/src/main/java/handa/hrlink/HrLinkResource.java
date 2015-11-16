package handa.hrlink;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import handa.beans.dto.DeviceInfo;
import handa.beans.dto.DtrInput;

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Path("hrlink")
public class HrLinkResource
{
    private static final String MESSAGE = "message";

    private HrLinkService hrLinkService;

    @Autowired
    public HrLinkResource(HrLinkService hrLinkService)
    {
        this.hrLinkService = hrLinkService;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("dtr/in")
    public Response dtrIn(@Context HttpHeaders headers, DtrInput dtrInput)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = hrLinkService.timeIn(deviceInfo, dtrInput);
        return buildResponse(result);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("dtr/out")
    public Response dtrOut(@Context HttpHeaders headers, DtrInput dtrInput)
    {
        DeviceInfo deviceInfo = DeviceInfo.from(headers);
        String result = hrLinkService.timeOut(deviceInfo, dtrInput);
        return buildResponse(result);
    }

    public Response buildResponse(String result)
    {
        switch(result)
        {
            case "Mobile number not registered": return Response.status(Status.UNAUTHORIZED).entity(ImmutableMap.of(MESSAGE, result)).build();
            case "Success": return Response.ok(ImmutableMap.of(MESSAGE, result)).build();
            default: throw new RuntimeException(result);
        }
    }
}