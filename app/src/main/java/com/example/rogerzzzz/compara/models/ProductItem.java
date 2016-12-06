package com.example.rogerzzzz.compara.models;

import java.io.Serializable;

/**
 * Created by rogerzzzz on 2016/12/6.
 */

public class ProductItem implements Serializable {
    private String product_name;
    private String _id;

    public ProductItem(String _id, String product_name){
        super();
        this.product_name = product_name;
        this._id = _id;
    }

    public ProductItem(){
        super();
    }

    public String get_id() {
        return _id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProduct_name() {
        return product_name;
    }
}
