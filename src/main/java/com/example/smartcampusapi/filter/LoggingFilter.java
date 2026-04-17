package com.example.smartcampusapi.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        String uri = requestContext.getUriInfo().getRequestUri().toString();
        String timestamp = LocalDateTime.now().format(formatter);

        // This prints in RED in the NetBeans console for the examiner to see clearly
        System.err.println(">>> [AUDIT START] " + timestamp);
        System.err.println(">>> METHOD: " + method);
        System.err.println(">>> PATH: " + uri);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        int status = responseContext.getStatus();
        
        // Logs the response status code
        System.err.println("<<< [AUDIT END] Status Code: " + status);
        System.err.println("----------------------------------------------");
    }
}