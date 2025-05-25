package org.example.resources;

import org.example.model.Product;
import org.example.model.ResponseList;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/products")
public class ProductResource {

    private static final List<Product> DATA = Arrays.asList(
            new Product("Laptop", 2000.0, "Dell"),
            new Product("Myszka", 50.0, "Logitech"),
            new Product("Monitor", 1500.0, "Dell"),
            new Product("Torba", 100.0, "Dell"),
            new Product("Tablet", 170.0, "IBM")
    );

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList getAllProducts() {
        return new ResponseList(DATA);
    }
}
