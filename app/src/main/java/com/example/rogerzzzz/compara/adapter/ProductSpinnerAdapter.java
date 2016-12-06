package com.example.rogerzzzz.compara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rogerzzzz.compara.R;
import com.example.rogerzzzz.compara.models.ProductItem;

import java.util.List;

/**
 * Created by rogerzzzz on 2016/12/6.
 */

public class ProductSpinnerAdapter extends BaseAdapter {
    private List<ProductItem> list;
    private Context           mContext;

    public ProductSpinnerAdapter(List<ProductItem> list, Context context){
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.spinner_item_layout, null);
        if(convertView!=null) {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.text);
            _TextView1.setText(list.get(position).getProduct_name());
        }
        return convertView;
    }
}
