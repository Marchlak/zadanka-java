package org.example.demo.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.demo.model.Product;
import org.example.demo.model.ResponseList;
import org.example.demo.model.SearchParam;
import org.example.demo.service.ProductService;

import java.util.List;

@Path("/products")
public class ProductResource {
    private final ProductService productService = ProductService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList getAllProducts() {
        return new ResponseList(productService.getAllProducts());
    }

    @POST
    @Path("/findProducts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList searchProducts(SearchParam searchParam) {
        List<Product> results = productService.findProductsByCriteria(
                searchParam.getName(),
                searchParam.getManufacturer(),
                searchParam.getMaxPrice()
        );
        return new ResponseList(results);
    }

}