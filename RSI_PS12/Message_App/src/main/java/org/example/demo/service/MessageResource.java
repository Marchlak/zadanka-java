package org.example.demo.service;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.example.demo.model.Message;
import org.example.demo.resources.MessageService;

@Path("/messages")
public class MessageResource {
    private final MessageService messageService = MessageService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getMessages(@QueryParam("zaczynasie") String prefix) {
        if (prefix != null && !prefix.isEmpty())
            return messageService.getAllMessagesStartingWith(prefix);

        return messageService.getList();
    }

    @GET
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message getTextJson(@PathParam("messageId") Long id, @Context UriInfo uriInfo) {
        Message message = messageService.getMessage(id);
        if (message == null) {
            throw new NotFoundException("Message not found");
        }

        String selfUri = uriInfo.getAbsolutePath().toString();
        message.addLink(selfUri, "self");

        String commentsUri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class, "getCommentResource")
                .resolveTemplate("messageId", id)
                .build()
                .toString();
        message.addLink(commentsUri, "comments");

        return message;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMessage(Message message, @Context UriInfo uriInfo) {
        Message newMessage = messageService.createMessage(message);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newMessage.getId()))
                .build();

        return Response.created(uri).entity(newMessage).build();
    }

    @PUT
    @Path("/{messageId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message updateMessage(@PathParam("messageId") Long id, Message message) {
        message.setId(id);
        return messageService.updateMessage(message);
    }

    @DELETE
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMessage(@PathParam("messageId") Long id) {
        Message deleted = messageService.deleteMessage(id);
        if (deleted != null) {
            return Response.ok(deleted).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/filtered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredMessages(@QueryParam("author") String authorFilter,
                                        @HeaderParam("X-Custom-Header") String customHeader,
                                        @MatrixParam("sort") String sortOrder) {

        System.out.println("Custom header value: " + customHeader);
        System.out.println("Sort order: " + sortOrder);

        List<Message> allMessages = messageService.getList();

        StringBuilder filterInfo = new StringBuilder();
        if (authorFilter != null && !authorFilter.isEmpty()) {
            filterInfo.append("Filtered by author: ").append(authorFilter).append("; ");
            allMessages = allMessages.stream()
                    .filter(m -> m.getAuthor().equalsIgnoreCase(authorFilter))
                    .collect(Collectors.toList());
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            filterInfo.append("Sorted by message (descending); ");
            allMessages.sort(Comparator.comparing(Message::getMessage).reversed());
        } else if ("asc".equalsIgnoreCase(sortOrder)) {
            filterInfo.append("Sorted by message (ascending); ");
            allMessages.sort(Comparator.comparing(Message::getMessage));
        }

        if (customHeader != null && !customHeader.isEmpty()) {
            filterInfo.append("Custom header: ").append(customHeader).append("; ");
        }

        JsonObject response = Json.createObjectBuilder()
                .add("filter_info", filterInfo.toString())
                .add("message_count", allMessages.size())
                .add("messages", Json.createArrayBuilder(allMessages.stream()
                        .map(m -> Json.createObjectBuilder()
                                .add("id", m.getId())
                                .add("message", m.getMessage())
                                .add("author", m.getAuthor())
                                .build())
                        .collect(Collectors.toList())))
                .build();

        return Response.ok(response).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequestInfo(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

        jsonBuilder.add("absolute_path", uriInfo.getAbsolutePath().toString());
        jsonBuilder.add("base_uri", uriInfo.getBaseUri().toString());
        jsonBuilder.add("request_uri", uriInfo.getRequestUri().toString());

        JsonObjectBuilder pathParamsBuilder = Json.createObjectBuilder();
        uriInfo.getPathParameters().forEach((k, v) -> pathParamsBuilder.add(k, v.toString()));
        jsonBuilder.add("path_parameters", pathParamsBuilder);

        JsonObjectBuilder queryParamsBuilder = Json.createObjectBuilder();
        uriInfo.getQueryParameters().forEach((k, v) -> queryParamsBuilder.add(k, v.toString()));
        jsonBuilder.add("query_parameters", queryParamsBuilder);

        JsonObjectBuilder headersBuilder = Json.createObjectBuilder();
        headers.getRequestHeaders().forEach((k, v) -> headersBuilder.add(k, v.toString()));
        jsonBuilder.add("headers", headersBuilder);

        return Response.ok(jsonBuilder.build()).build();
    }

    @GET
    @Path("/{messageId}/comments")
    public CommentResource getCommentResource(@PathParam("messageId") long messageId) {
        return new CommentResource(messageId);
    }

    @GET
    @Path("/secured")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getSecuredMessages() {
        return messageService.getSecuredMessages();
    }
}
