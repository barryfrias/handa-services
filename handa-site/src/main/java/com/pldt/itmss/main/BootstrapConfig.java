package com.pldt.itmss.main;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
public class BootstrapConfig
extends ResourceConfig
{
    public BootstrapConfig()
    {
        this.register(LoggingFilter.class);
        this.register(RequestContextFilter.class);
        this.register(JacksonFeature.class);
        this.register(MultiPartFeature.class);
    }

    @Bean
    public HttpServer httpServer(@Value("#{systemProperties.grizzlyPort}") final int port,
                                 @Value("#{systemProperties.grizzlyHost}") final String host,
                                 @Value("#{systemProperties.resourceRoot}") final String resourceRoot,
                                 @Value("#{systemProperties.grizzlyCorePoolSize}") final int corePoolSize,
                                 @Value("#{systemProperties.grizzlyMaxPoolSize}") final int maxPoolSize)
    throws IOException
    {
        HttpServer server = new HttpServer();
        NetworkListener networkListener = new NetworkListener("grizzly2", host, port);
        ThreadPoolConfig workerConfig = ThreadPoolConfig.defaultConfig()
                                        .setCorePoolSize(corePoolSize)
                                        .setMaxPoolSize(maxPoolSize)
                                        .setQueueLimit(-1);
        networkListener.getTransport().setWorkerThreadPoolConfig(workerConfig);
        server.addListener(networkListener);
        WebappContext webappContext = new WebappContext(resourceRoot, resourceRoot)
        {{
            addContextInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
            addContextInitParameter("contextConfigLocation", MainSpringConfig.class.getName());
            addListener(ContextLoaderListener.class);
        }};
        registerJerseyRestServlet(webappContext);
        webappContext.deploy(server);
        return server;
    }

    private void registerJerseyRestServlet(WebappContext webappContext)
    {
        // wire up Spring managed collaborators to Jersey resources.
        ServletRegistration servletRegistration = webappContext.addServlet("jersey-servlet", ServletContainer.class);
        servletRegistration.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, this.getClass().getName());
        servletRegistration.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "com.pldt.itmss.core.exception.mappers,handa");
        servletRegistration.setInitParameter(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
        servletRegistration.addMapping("/*");
        servletRegistration.setLoadOnStartup(1);
    }
}