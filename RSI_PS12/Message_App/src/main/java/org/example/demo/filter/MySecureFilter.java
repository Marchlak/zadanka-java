package org.example.demo.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

@Provider
public class MySecureFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger(MySecureFilter.class.getName());
    private static final String AUTH_HEADER = "Authorization";
    private static final String BASIC_PREFIX = "Basic ";
    private static final String REALM = "example";
    private static final String VALID_USERNAME = "user";
    private static final String VALID_PASSWORD = "password";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (!requestContext.getUriInfo().getPath().contains("secured")) {
            return;
        }

        String authHeader = requestContext.getHeaderString(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(BASIC_PREFIX)) {
            abortWithUnauthorized(requestContext, "Missing credentials");
            return;
        }

        try {
            String encodedCredentials = authHeader.substring(BASIC_PREFIX.length());
            byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
            String credentials = new String(decodedBytes, StandardCharsets.UTF_8);
            String[] parts = credentials.split(":", 2);

            if (parts.length != 2) {
                abortWithUnauthorized(requestContext, "Invalid credential format");
                return;
            }

            String username = parts[0];
            String password = parts[1];

            if (!VALID_USERNAME.equals(username) || !VALID_PASSWORD.equals(password)) {
                abortWithUnauthorized(requestContext, "Invalid username/password");
            }

            LOGGER.info("Authenticated user: " + username);

        } catch (IllegalArgumentException e) {
            abortWithUnauthorized(requestContext, "Invalid Base64 encoding");
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext context, String message) {
        LOGGER.warning("Authentication failed: " + message);
        context.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", BASIC_PREFIX + "realm=\"" + REALM + "\"")
                .entity(message)
                .build());
    }
}