package org.example.client;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import org.example.model.Message;
import org.glassfish.jersey.jsonb.JsonBindingFeature;
import java.util.Base64;
import java.util.List;

public class MessageClient {
    private static final String BASE_URL = "http://localhost:8181/rest-ws/api/messages";
    private static final String AUTH;

    static {
        String creds = "user:password";
        AUTH = "Basic " + Base64.getEncoder().encodeToString(creds.getBytes());
    }

    public static void main(String[] args) {
        Client client = ClientBuilder.newBuilder()
                .register(JsonBindingFeature.class)
                .build();
        WebTarget target = client.target(BASE_URL);

        List<Message> list = authorized(target.queryParam("author", "Kamil"))
                .get(new GenericType<>() {});
        list.forEach(m -> System.out.println(m.getId() + " " + m.getMessage()));

        Message one = authorized(target.path("1"))
                .get(Message.class);
        System.out.println("Fetched: " + one.getMessage());

        one.setMessage("Nowa treść");
        Message updated = authorized(target.path(Long.toString(one.getId())))
                .put(Entity.json(one), Message.class);
        System.out.println("Updated: " + updated.getMessage());

        List<Message> startsWithPi = authorized(target.queryParam("zaczynasie", "No"))
                .get(new GenericType<>() {});
        if (!startsWithPi.isEmpty()) {
            Message first = startsWithPi.get(0);
            System.out.println("First matching: "
                    + first.getId() + " "
                    + first.getMessage() + " "
                    + first.getAuthor());
        } else {
            System.out.println("Brak wiadomości zaczynających się od No'");
        }

        client.close();
    }

    private static Invocation.Builder authorized(WebTarget t) {
        return t.request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, AUTH);
    }
}
