package org.example.demo.service;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.demo.model.Comment;
import org.example.demo.resources.MessageService;
import java.util.List;

@Path("/")
public class CommentResource {
    private long messageId;
    private final MessageService messageService = MessageService.getInstance();

    public CommentResource() {
    }

    public CommentResource(long messageId) {
        this.messageId = messageId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getComments() {
        return messageService.getComments(messageId);
    }
}
