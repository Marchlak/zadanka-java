package org.example.filter;


import java.io.IOException;
import java.util.Base64;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final String REALM = "example";
    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        String auth = ctx.getHeaderString("Authorization");
        if (auth == null || !auth.startsWith("Basic ")) {
            abort(ctx);
            return;
        }
        String base64 = auth.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(base64));
        String[] parts = decoded.split(":", 2);
        if (parts.length != 2 || !valid(parts[0], parts[1])) {
            abort(ctx);
        }
    }
    private boolean valid(String user, String pass) {
        return "user".equals(user) && "password".equals(pass);
    }
    private void abort(ContainerRequestContext ctx) {
        ctx.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", "Basic realm=\"" + REALM + "\"")
                .build()
        );
    }
}
