package com.example.rogerzzzz.compara;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rogerzzzz.compara.adapter.MarketAdapter;
import com.example.rogerzzzz.compara.common.utils.Parameters;
import com.example.rogerzzzz.compara.models.SupermarketModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rogerzzzz on 2016/12/3.
 */

public class SupermarketActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    private RequestQueue queue;
    private String json;
    private ArrayList<SupermarketModel> supermarketModelArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager          mLayoutManager;
    private MarketAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supermarket_activity);
        list = (ArrayList<String>) getIntent().getSerializableExtra("product_list");
        json = JSON.toJSONString(list);
        queue = Volley.newRequestQueue(this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("product_list", json);
        recyclerView = (RecyclerView) findViewById(R.id.supermarket_list);
        mLayoutManager = new LinearLayoutManager(this);
        supermarketModelArrayList = new ArrayList<>();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Parameters.getServerUrl() + "/products", new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = response.getJSONObject("resObj");
                    Iterator<String> iterator = obj.keys();
                    while(iterator.hasNext()){
                        String key = iterator.next();
                        String value = obj.getString(key);
                        SupermarketModel tmp = new SupermarketModel(key, value);
                        supermarketModelArrayList.add(tmp);
                    }
                    adapter = new MarketAdapter(supermarketModelArrayList, getBaseContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Accept","*/*");
                map.put("Content-Type","application/json");
                return map;
            }
        };
        queue.add(stringRequest);
    }
}
