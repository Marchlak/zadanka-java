package org.example.demo.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class MyRequestFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger(MyRequestFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) {
        LOGGER.info("Request Filter: " + requestContext.getUriInfo().getPath());
    }
}
