// src/main/java/org/example/model/Product.java
package org.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {
    private String name;
    private double price;
    private String producer;

    public Product() {}

    public Product(String name, double price, String producer) {
        this.name = name;
        this.price = price;
        this.producer = producer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}
