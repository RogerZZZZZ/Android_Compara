package com.example.rogerzzzz.compara;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rogerzzzz.compara.common.utils.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rogerzzzz on 2016/12/3.
 */

public class SupermarketActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    private RequestQueue queue;
    private String json;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supermarket_activity);
        list = (ArrayList<String>) getIntent().getSerializableExtra("product_list");
        json = JSON.toJSONString(list);
        queue = Volley.newRequestQueue(this);
        Log.i("--->", json);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Parameters.getServerUrl() + "/products", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("--->1", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("--->1", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("product_list", json);
                return map;
            }
        };
        queue.add(stringRequest);
    }
}
