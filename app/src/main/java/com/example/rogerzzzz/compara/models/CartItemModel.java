package com.example.rogerzzzz.compara.models;

import java.io.Serializable;

/**
 * Created by rogerzzzz on 2016/12/10.
 */

public class CartItemModel implements Serializable {
    private String product_name;
    private int sum;

    public CartItemModel(String product_name, int sum){
        this.product_name = product_name;
        this.sum = sum;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
