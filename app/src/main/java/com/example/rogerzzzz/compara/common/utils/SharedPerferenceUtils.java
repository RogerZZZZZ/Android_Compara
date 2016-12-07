package com.example.rogerzzzz.compara.common.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.rogerzzzz.compara.common.data.CollectionDataProvider;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rogerzzzz on 2016/12/6.
 */

public class SharedPerferenceUtils {

    public static boolean containValue(SharedPreferences sp, String target){
        boolean flag = false;
        int size = sp.getInt("size", 0);
        for(int i = 0; i < size; i++){
            String tmp = sp.getString("Item_"+i, null);
            if(target.equals(tmp)){
                return true;
            }
        }

        return flag;
    }

    public static List<CollectionDataProvider.ConcreteData> toList(SharedPreferences sp){
        int size = sp.getInt("size", 0);
        List<CollectionDataProvider.ConcreteData> list = new LinkedList<>();
        final int viewType = 0;
        final int swipeReaction = RecyclerViewSwipeManager.REACTION_CAN_SWIPE_UP | RecyclerViewSwipeManager.REACTION_CAN_SWIPE_DOWN;
        for(int i = 0; i < size; i++){
            String tmp = sp.getString("Item_"+i, null);
            if(tmp != null){
                list.add(new CollectionDataProvider.ConcreteData(i, viewType, tmp, swipeReaction));
            }
        }
        return list;
    }

    public static void saveItem(SharedPreferences sp, String target){
        int size = sp.getInt("size", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Item_"+size, target);
        size++;
        editor.putInt("size", size);
        editor.commit();
    }

    public static void removeItem(SharedPreferences sp, String target){
        int size = sp.getInt("size", 0);
        SharedPreferences.Editor editor = sp.edit();
        for(int i = 0; i < size; i++){
            String tmp = sp.getString("Item_"+i, null);
            if(target.equals(tmp)){
                editor.putString("Item_"+i, null);
                editor.commit();
                return;
            }
        }
    }
}
