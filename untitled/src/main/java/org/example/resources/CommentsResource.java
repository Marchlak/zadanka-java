// src/main/java/org/example/resources/CommentsResource.java
package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.model.Comment;
import java.util.List;

public class CommentsResource {
    private long messageId;
    private CommentResource commentResource = new CommentResource();

    public CommentsResource(long messageId) {
        this.messageId = messageId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getComments() {
        return commentResource.getCommentsForMessage(messageId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment(Comment comment) {
        return commentResource.addComment(messageId, comment);
    }
}
