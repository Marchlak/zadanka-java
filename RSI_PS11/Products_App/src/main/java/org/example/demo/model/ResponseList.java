package org.example.demo.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class ResponseList {
    private List<Product> products;

    public ResponseList() {
    }

    public ResponseList(List<Product> products) {
        this.products = products;
    }

    @XmlElement(name = "products")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
