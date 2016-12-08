package com.example.rogerzzzz.compara;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.rogerzzzz.compara.adapter.UnitSpinnerAdapter;
import com.example.rogerzzzz.compara.common.utils.TestUtils;
import com.example.rogerzzzz.compara.common.utils.unit.UnitPriceCompResult;
import com.example.rogerzzzz.compara.common.utils.unit.UnitPriceComparator;
import com.example.rogerzzzz.compara.common.utils.unit.UnitUtils;
import com.rey.material.widget.EditText;

import java.util.List;

import static com.example.rogerzzzz.compara.R.id.price;

/**
 * Created by rogerzzzz on 2016/12/3.
 */

public class UnitPriceActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner spinner, spinner1;
    private EditText price_et, price_et1;
    private EditText sum_et, sum_et1;
    private Button calculateBtn;
    private UnitSpinnerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unitprice_activity);
        List<String> list = UnitUtils.getUnitList();
        adapter = new UnitSpinnerAdapter(list, this);
        initView();
    }

    private void initView(){
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        price_et = (EditText) findViewById(price);
        price_et1 = (EditText) findViewById(R.id.price1);
        sum_et = (EditText) findViewById(R.id.sum);
        sum_et1 = (EditText) findViewById(R.id.sum1);
        calculateBtn = (Button) findViewById(R.id.calculateBtn);

        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter);
        calculateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calculateBtn:
                String priceStr = price_et.getText().toString();
                String price1Str = price_et1.getText().toString();
                String sumStr = sum_et.getText().toString();
                String sum1Str = sum_et1.getText().toString();
                if(!TestUtils.isDouble(priceStr) || !TestUtils.isDouble(price1Str) || !TestUtils.isDouble(sumStr) || !TestUtils.isDouble(sum1Str)){
                    Snackbar.make(getWindow().getDecorView(), "Input is illegal", Snackbar.LENGTH_LONG).show();
                    return;
                }
                double price = Double.parseDouble(priceStr);
                double price1 = Double.parseDouble(price1Str);
                double sum = Double.parseDouble(sumStr);
                double sum1 = Double.parseDouble(sum1Str);
                String unit = (String) spinner.getSelectedItem();
                String unit1 = (String) spinner1.getSelectedItem();
                UnitPriceComparator unitPrice = new UnitPriceComparator();
                UnitPriceCompResult result = unitPrice.UnitPriceCompare("A", price, sum, unit, "B", price1, sum1, unit1);
                String cheaperOne = result.cheaperPrdName;
                Snackbar.make(getWindow().getDecorView(), (cheaperOne.equals("A") ? "First" : "Second") + " is cheaper", Snackbar.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
