package org.example.client;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import org.example.model.ResponseList;
import org.example.model.SearchParam;
import org.glassfish.jersey.jsonb.JsonBindingFeature;

public class ProductClient {
    private static final String BASE_URL = "http://localhost:8181/rest-produkty/api/store";

    public static void main(String[] args) {
        Client client = ClientBuilder.newBuilder()
            .register(JsonBindingFeature.class)
            .build();
        WebTarget target = client.target(BASE_URL);

        ResponseList all = target.path("all")
            .request(MediaType.APPLICATION_JSON)
            .get(ResponseList.class);
        all.getProducts().forEach(p ->
            System.out.println(p.getName() + " " + p.getPrice() + " " + p.getProducer())
        );

        System.out.println("-------SearchParam-------");

        SearchParam param = new SearchParam();
        param.setProducer("Dell");
        param.setPriceLessThan(1800);
        ResponseList found = target.path("find")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(param, MediaType.APPLICATION_JSON), ResponseList.class);
        found.getProducts().forEach(p ->
            System.out.println(p.getName() + " " + p.getPrice() + " " + p.getProducer())
        );

        client.close();
    }
}
