package org.portfolio.giry.oms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class LoggingFilterConfiguration implements ContainerRequestFilter, ContainerResponseFilter {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void filter(ContainerRequestContext requestContext) {
        Log.infof("Incoming request: %s %s",
                requestContext.getMethod(),
                requestContext.getUriInfo().getRequestUri().toString());

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
