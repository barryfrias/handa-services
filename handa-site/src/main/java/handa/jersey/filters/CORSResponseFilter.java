package handa.jersey.filters;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

import com.google.common.base.Splitter;

public class CORSResponseFilter
implements ContainerResponseFilter
{
    private Iterable<String> allowedHosts;

    public CORSResponseFilter()
    {
        ResourceBundle props = ResourceBundle.getBundle("handa");
        String value = props.getString("cors.allowed.hosts");
        allowedHosts = Splitter.on(',').split(value);
    }

    // see - > http://www.codingpedia.org/ama/how-to-add-cors-support-on-the-server-side-in-java-with-jersey/
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
    throws IOException
    {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        for(String host: allowedHosts)
        {
            headers.add("Access-Control-Allow-Origin", host);
        }
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
    }
}