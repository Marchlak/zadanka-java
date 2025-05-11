package org.example.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.model.Message;

import java.util.List;

@Path("")
public class MessageService {

    private final MessageResource messageResource = new MessageResource();

    @GET
    @Path("/messagesxml")
    @Produces({MediaType.APPLICATION_XML})
    public List<Message> getAllMessages() {
        return messageResource.getAllMessages();
    }

    @GET
    @Path("/messagesrest")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Message> getAllMessages2() {
        return messageResource.getAllMessages();
    }
}
