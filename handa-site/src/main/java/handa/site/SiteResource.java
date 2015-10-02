package handa.site;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.core.HandaProperties;

@Component
@Produces({ MediaType.TEXT_PLAIN })
@Path("site")
public class SiteResource
{
    static Logger log = LoggerFactory.getLogger(SiteResource.class);

    private HandaProperties handaProperties;

    @Autowired
    public SiteResource(HandaProperties handaProperties)
    {
        this.handaProperties = handaProperties;
    }

    @GET
    @Path("reload")
    public Response reloadProperties()
    {
        this.handaProperties.reload();
        return Response.ok("Reload successful!").type(MediaType.TEXT_PLAIN).build();
    }
}