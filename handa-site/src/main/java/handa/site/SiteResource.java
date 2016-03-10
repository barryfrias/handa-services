package handa.site;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.core.HandaProperties;

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
@Path("site")
public class SiteResource
{
    static Logger log = LoggerFactory.getLogger(SiteResource.class);

    private final HandaProperties handaProperties;
    private final CacheService cacheService;
    private final SiteService siteService;

    @Autowired
    public SiteResource(HandaProperties handaProperties, CacheService cacheService, SiteService siteService)
    {
        this.handaProperties = handaProperties;
        this.cacheService = cacheService;
        this.siteService = siteService;
    }

    @GET
    @Path("reload")
    public Response reloadProperties()
    {
        this.handaProperties.reload();
        return Response.ok("Reload successful!").type(MediaType.TEXT_PLAIN).build();
    }

    @GET
    @Path("cache")
    public Response listCache()
    {
        return Response.ok(cacheService.list()).build();
    }

    @GET
    @Path("cache/{key}")
    public Response getCache(@PathParam("key") String key)
    {
        Object value = cacheService.get(key);
        if(value == null)
        {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(cacheService.get(key)).build();
    }

    @POST
    @Path("cache/{key}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response putCache(@PathParam("key") String key, Map<String, Object> jsonPayload)
    {
        cacheService.put(key, jsonPayload);
        return Response.ok().build();
    }

    @GET
    @Path("addresses/provinces")
    public Response getProvinces()
    {
        return Response.ok(siteService.getProvinces()).build();
    }

    @GET
    @Path("addresses/cities")
    public Response getCities(@QueryParam("province") String province)
    {
        return Response.ok(siteService.getCities(province)).build();
    }

    @GET
    @Path("addresses/barangays")
    public Response getBarangays(@QueryParam("province") String province, @QueryParam("city") String city)
    {
        return Response.ok(siteService.getBarangays(province, city)).build();
    }
}