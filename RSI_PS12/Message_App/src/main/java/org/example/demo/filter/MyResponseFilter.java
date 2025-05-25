package org.example.demo.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class MyResponseFilter implements ContainerResponseFilter {
    private static final Logger LOGGER = Logger.getLogger(MyResponseFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        LOGGER.info("Response Filter: " + requestContext.getHeaders());
        responseContext.getHeaders().add("mojNaglowek", "rsi test");
    }
}