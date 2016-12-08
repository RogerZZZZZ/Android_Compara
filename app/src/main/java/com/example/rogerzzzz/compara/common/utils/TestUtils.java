package com.example.rogerzzzz.compara.common.utils;

/**
 * Created by rogerzzzz on 2016/12/8.
 */

public class TestUtils {
    public static boolean isDouble(String str){
        if(str.equals("")) return false;
        try{
            Double.parseDouble(str);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
