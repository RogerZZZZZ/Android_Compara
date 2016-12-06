package com.example.rogerzzzz.compara.common.utils;

import com.example.rogerzzzz.compara.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rogerzzzz on 2016/12/6.
 */

public class ArrayListUtils {

    public static String[] decreaseLength(List<ProductItem> list){
        String[] resList = new String[list.size()/5];
        for(int i = 0, index = 0; i < list.size(); index++){
            resList[index] = list.get(i).getProduct_name();
            i += 5;
        }
        return resList;
    }

    public static List<ProductItem> decreaseLength2(List<ProductItem> list){
        List<ProductItem> resList = new ArrayList<>();
        for(int i = 0; i < list.size(); ){
            resList.add(list.get(i));
            i += 5;
        }
        return resList;
    }
}
