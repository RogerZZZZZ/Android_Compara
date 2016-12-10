package com.example.rogerzzzz.compara;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rogerzzzz.compara.adapter.ProductSpinnerAdapter;
import com.example.rogerzzzz.compara.adapter.ScanListAdapter;
import com.example.rogerzzzz.compara.common.utils.ArrayListUtils;
import com.example.rogerzzzz.compara.common.utils.Parameters;
import com.example.rogerzzzz.compara.models.Goods;
import com.example.rogerzzzz.compara.models.ProductItem;
import com.rey.material.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.rogerzzzz.compara.R.id.sum;

/**
 * Created by rogerzzzz on 2016/12/3.
 */

public class ItemEditActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText                   et_sum;
    private Button                     btn_send;
    private Spinner                    spinner;
    private List<ProductItem>          list;
    private RequestQueue               queue;
    private ProductSpinnerAdapter      mAdapter;
    private LinearLayout               top_wrap;
    private LinearLayout               bottom_wrap;
    private RecyclerView               scan_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private ScanListAdapter            scanAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_edit_activity);
        et_sum = findEditText(sum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Item");
        setSupportActionBar(toolbar);
        btn_send = (Button) findViewById(R.id.send_btn);
        btn_send.setOnClickListener(this);

        scan_view = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        scan_view.setLayoutManager(mLayoutManager);
        top_wrap  = (LinearLayout) findViewById(R.id.top_wrap);
        bottom_wrap = (LinearLayout) findViewById(R.id.bottom_wrap);

        spinner = (Spinner) findViewById(R.id.spinner);

        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Parameters.getServerUrl() + "/products", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                list = JSON.parseArray(response, ProductItem.class);
                list = ArrayListUtils.decreaseLength2(list);
                mAdapter = new ProductSpinnerAdapter(list, getApplicationContext());
                spinner.setAdapter(mAdapter);
                spinner.setSelection(0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("--->err", error.getMessage());
            }
        });

        mQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1 && data != null){
            String detail = data.getStringExtra("code");
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("product", detail);
            queue = Volley.newRequestQueue(this);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Parameters.getServerUrl() + "/specific", new JSONObject(hashMap), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray tmp = response.getJSONArray("docs");
                        String jsonStr = tmp.toString();
                        list = JSON.parseArray(jsonStr, ProductItem.class);
                        list = ArrayListUtils.decreaseLength2(list);
                        if(list.size() > 0){
                            scanAdapter = new ScanListAdapter(list);
                            scanAdapter.setClickListener(new ScanListAdapter.onItemClickInterface() {
                                @Override
                                public void onItemClickListener(int position) {
                                    Goods goods = new Goods(list.get(position).getProduct_name(), 1);
                                    Intent intent = new Intent(ItemEditActivity.this, ProductListActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("goods", (Serializable) goods);
                                    intent.putExtras(bundle);
                                    setResult(0, intent);
                                    finish();
                                }
                            });
                            scan_view.setAdapter(scanAdapter);
                            top_wrap.setVisibility(View.GONE);
                            bottom_wrap.setVisibility(View.VISIBLE);
                        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_scan:
                Intent intent = new Intent(ItemEditActivity.this, SimpleCaptureActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    private EditText findEditText(int id){
        return (EditText) findViewById(id);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_btn:
                if(!et_sum.getText().toString().matches("[0-9]+")){
                    Snackbar.make(view, "Input is illegal", Snackbar.LENGTH_LONG).show();
                    et_sum.setText("");
                    return;
                }
                int sum = Integer.parseInt(et_sum.getText().toString());
                String name = ((ProductItem) spinner.getSelectedItem()).getProduct_name();
                Goods goods = new Goods(name, sum);
                Intent intent = new Intent(ItemEditActivity.this, ProductListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", (Serializable) goods);
                intent.putExtras(bundle);
                this.setResult(0, intent);
                finish();
                break;
        }
    }
}
