package com.example.rogerzzzz.compara.models;

import java.io.Serializable;

/**
 * Created by rogerzzzz on 2016/12/3.
 */

public class Goods implements Serializable{
    private double price;
    private String unit;
    private String name;
    private int sum;

    public Goods(String name, double price, String unit){
        this.price = price;
        this.unit = unit;
        this.name = name;
    }

    public Goods(String name, int sum){
        this.name = name;
        this.sum = sum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
