package org.portfolio.giry.oms.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Provider
public class LoggingFilterConfiguration implements ContainerRequestFilter, ContainerResponseFilter {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void filter(ContainerRequestContext requestContext) throws JsonProcessingException {
        String body = new BufferedReader(new InputStreamReader(requestContext.getEntityStream()))
                .lines()
                .collect(Collectors.joining(" "));

        requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));


        Log.infof("Incoming request: %s %s ,body: %s",
                requestContext.getMethod(),
                requestContext.getUriInfo().getRequestUri().toString(),
                body);


        if (requestContext.hasEntity()) {
           Log.debug("Request contains body (JSON/XML/etc)");
        }

    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        String json = mapper.writeValueAsString(containerResponseContext.getEntity());

        Log.infof("Outgoing Response: %s, status:%d",
                json,containerResponseContext.getStatus());
    }
}
