package handa.command;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.beans.dto.EventReport;
import handa.beans.dto.ReportInput;

@Component
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
public class CommandAnalyticsSubResource
{
    private CommandAnalyticsService commandAnalyticsService;

    @Autowired
    public CommandAnalyticsSubResource(CommandAnalyticsService commandAnalyticsService)
    {
        this.commandAnalyticsService = commandAnalyticsService;
    }

    @POST
    @Path("byEvent")
    public Response byEvent(ReportInput reportInput)
    {
        EventReport result = commandAnalyticsService.byEvent(reportInput);
        return Response.ok().entity(result).build();
    }
}