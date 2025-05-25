package org.example.demo.service;

import org.example.demo.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final Map<Long, Product> products = new HashMap<>();
    private long productId = 1;

    public ProductService() {
        addProduct(new Product("Laptop #4GDS", "Dell", 2000));
        addProduct(new Product("Laptop #3FSD", "Dell", 2500));
        addProduct(new Product("Mysz", "Logitech", 50));
        addProduct(new Product("Monitor", "Samsung", 1500));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProductById(long id) {
        return products.get(id);
    }

    public Product addProduct(Product product) {
        product.setId(++productId);
        products.put(product.getId(), product);
        return product;
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }

    public List<Product> findProductsByCriteria(String name, String manufacturer, int maxPrice) {
        return products.values().stream()
                .filter(product -> name == null || product.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(product -> manufacturer == null || product.getManufacturer().toLowerCase().contains(manufacturer.toLowerCase()))
                .filter(product -> product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
