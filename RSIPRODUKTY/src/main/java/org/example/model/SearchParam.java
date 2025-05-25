// src/main/java/org/example/model/SearchParam.java
package org.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchParam {
    private String name;
    private String producer;
    private double priceLessThan;

    public SearchParam() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public double getPriceLessThan() {
        return priceLessThan;
    }

    public void setPriceLessThan(double priceLessThan) {
        this.priceLessThan = priceLessThan;
    }
}
