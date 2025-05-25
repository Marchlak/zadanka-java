// src/main/java/org/example/resources/StoreResource.java
package org.example.resources;

import org.example.model.Product;
import org.example.model.ResponseList;
import org.example.model.SearchParam;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/store")
public class StoreResource {
    private static final List<Product> DATA = Arrays.asList(
            new Product("Laptop", 2000, "Dell"),
            new Product("Mouse",   50,   "Logitech"),
            new Product("Monitor", 1500, "Dell"),
            new Product("Bag",     100,  "Dell"),
            new Product("Tablet",  170,  "IBM")
    );

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList getAll() {
        return new ResponseList(DATA);
    }

    @POST
    @Path("/find")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList find(SearchParam param) {
        List<Product> result = DATA.stream()
                .filter(p -> param.getName() == null
                        || param.getName().isEmpty()
                        || p.getName().equalsIgnoreCase(param.getName()))
                .filter(p -> param.getProducer() == null
                        || param.getProducer().isEmpty()
                        || p.getProducer().equalsIgnoreCase(param.getProducer()))
                .filter(p -> p.getPrice() <= param.getPriceLessThan())
                .collect(Collectors.toList());
        return new ResponseList(result);
    }
}
