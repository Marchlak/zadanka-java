package org.example.resources;


import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/counter")
public class CounterService {
    private int count;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        count++;
        return "Count: " + count;
    }
}
