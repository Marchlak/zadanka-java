package org.example.resources;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.model.Message;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Path("/messages")
public class MessageService {
    private MessageResource messageResource = new MessageResource();

    @GET
    @Path("/messageallxml")
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> messageAllXml() {
        return new ArrayList<>(messageResource.getMessages().values());
    }

    @GET
    @Path("/messagesalljson")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> messagesAllJson(
            @QueryParam("author") String author,
            @HeaderParam("X-Client-Name") String client
    ) {
        List<Message> all = new ArrayList<>(messageResource.getMessages().values());
        if (author != null) {
            all.removeIf(m -> !m.getAuthor().equalsIgnoreCase(author));
        } else if (client != null) {
            all.removeIf(m -> !m.getAuthor().equalsIgnoreCase(client));
        }
        return all;
    }

    @GET
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message getMessage(@PathParam("messageId") Long id, @Context UriInfo uriInfo) {
        Message m = messageResource.getMessage(id);
        String self = uriInfo.getAbsolutePathBuilder().build().toString();
        m.addLink(self, "self");
        String comments = uriInfo.getAbsolutePathBuilder().path("comments").build().toString();
        m.addLink(comments, "comments");
        return m;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMessage(Message message, @Context UriInfo uriInfo) {
        Message newMessage = messageResource.createMessage(message);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newMessage.getId()))
                .build();
        return Response.created(uri)
                .entity(newMessage)
                .build();
    }

    @PUT
    @Path("/{messageId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message updateMessage(
            @PathParam("messageId") Long id,
            Message message
    ) {
        message.setId(id);
        return messageResource.updateMessage(message);
    }

        @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getMessages(
            @QueryParam("zaczynasie") String zaczynasie
    ) {
        if (zaczynasie != null) {
            return messageResource.getAllMessagesStartingWith(zaczynasie);
        }
        return new ArrayList<>(messageResource.getMessages().values());
    }

    @DELETE
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message deleteMessage(
            @PathParam("messageId") Long id,
            @MatrixParam("confirm") String confirm
    ) {
        if (!"yes".equalsIgnoreCase(confirm)) {
            throw new WebApplicationException(400);
        }
        return messageResource.deleteMessage(id);
    }


    @GET
    @Path("/test/uriinfo")
    @Produces(MediaType.TEXT_PLAIN)
    public String testUri(@Context UriInfo uriInfo) {
        return uriInfo.getAbsolutePath().toString();
    }

    @GET
    @Path("/test/headers")
    @Produces(MediaType.TEXT_PLAIN)
    public String testHeaders(@Context HttpHeaders headers) {
        return headers.getRequestHeaders().toString();
    }


    @Path("/{messageId}/comments")
    public CommentsResource getCommentsResource(@PathParam("messageId") long messageId) {
        return new CommentsResource(messageId);
    }



}

