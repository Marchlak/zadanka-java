package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloWorldService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPlain() {
        return "Hello World";
    }
    @GET
    @Path("/echo")
    @Produces(MediaType.TEXT_PLAIN)
    public String echo() {
        return "Witaj Echo";
    }

    @GET
    @Path("/echo2/{parametr}")
    @Produces(MediaType.TEXT_PLAIN)
    public String echo2(@PathParam("parametr") String parametr) {
        return "Witaj " + parametr;
    }
}
