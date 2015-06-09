package com.pldt.itmss.main;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Bootstrap
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext()
        {{
            setAllowCircularReferences(false);
            register(BootstrapConfig.class);
            refresh();
            registerShutdownHook();
        }};
        final HttpServer server = applicationContext.getBean(HttpServer.class);
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("Shutting down server...");
                server.shutdown();
                applicationContext.close();
            }
        });
        start(server);
    }

    private static void start(HttpServer server) throws IOException, InterruptedException
    {
        server.start();
        Thread.currentThread().join();
    }
}