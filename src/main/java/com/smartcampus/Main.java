package com.smartcampus;

import com.smartcampus.exception.GlobalExceptionHandler;
import com.smartcampus.filter.LoggingFilter;
import com.smartcampus.resource.DiscoveryResource;
import com.smartcampus.resource.RoomResource;
import com.smartcampus.resource.SensorResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://127.0.0.1:9090/api/v1/";

    public static HttpServer startServer() {

        ResourceConfig config = new ResourceConfig()
                .register(DiscoveryResource.class)
                .register(DiscoveryResource.class)
                .register(RoomResource.class)
                .register(SensorResource.class)
                .register(GlobalExceptionHandler.class)
                .register(LoggingFilter.class);

        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI),
                config
        );
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = startServer();
        System.out.println("Server running at: " + BASE_URI);
        System.in.read();
        server.shutdownNow();
    }
}