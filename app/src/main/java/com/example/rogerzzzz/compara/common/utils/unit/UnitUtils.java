package com.example.rogerzzzz.compara.common.utils.unit;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rogerzzzz on 2016/12/8.
 */

public class UnitUtils {
    private static final String[] unitArr = {"g", "kg", "L", "ml", "cm3", "lb", "dozen", "unit", "m", "cm", "mm"};
    private static final List<String> list = Arrays.asList(unitArr);

    public static List<String> getUnitList(){
        return list;
    }
}
