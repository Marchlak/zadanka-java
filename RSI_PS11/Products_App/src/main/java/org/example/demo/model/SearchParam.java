package org.example.demo.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchParam {
    private String name;
    private String manufacturer;
    private int maxPrice;

    public SearchParam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
}