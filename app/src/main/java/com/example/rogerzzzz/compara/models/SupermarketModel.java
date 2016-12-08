package com.example.rogerzzzz.compara.models;

/**
 * Created by rogerzzzz on 2016/12/8.
 */

public class SupermarketModel {
    private String name;
    private String price;

    public SupermarketModel(String name, String price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
