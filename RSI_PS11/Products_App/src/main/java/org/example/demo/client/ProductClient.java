package org.example.demo.client;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.demo.model.ResponseList;
import org.example.demo.model.SearchParam;

public class ProductClient {
    private static final String BASE_URL = "http://localhost:8080/rest-products/api/products";

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        testGetAllProducts(client);
        testSearchProducts(client);
        client.close();
    }

    private static void testGetAllProducts(Client client) {
        System.out.println("=== Test pobierania wszystkich produktów ===");
        Response response = client.target(BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .get();

        handleResponse(response);
    }

    private static void testSearchProducts(Client client) {
        System.out.println("\n=== Test wyszukiwania produktów ===");

        SearchParam searchParams = new SearchParam();
        searchParams.setName("Laptop");
        searchParams.setManufacturer("Dell");
        searchParams.setMaxPrice(3000);

        Response response = client.target(BASE_URL)
                .path("findProducts")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(searchParams, MediaType.APPLICATION_JSON));

        handleResponse(response);
    }

    private static void handleResponse(Response response) {
        if (response.getStatus() == 200) {
            ResponseList responseList = response.readEntity(ResponseList.class);
            System.out.println("Produkty:\n" + responseList.getProducts());
        } else {
            System.out.println("Kod statusu: " + response.getStatus());
            System.out.println("Treść błędu: " + response.readEntity(String.class));
        }
    }
}