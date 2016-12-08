package com.example.rogerzzzz.compara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rogerzzzz.compara.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rogerzzzz on 2016/12/8.
 */

public class UnitSpinnerAdapter extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private Context mContext;

    public UnitSpinnerAdapter(List<String> list, Context context){
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.spinner_item_layout, null);
        if(convertView!=null) {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.text);
            _TextView1.setText(list.get(position));
        }
        return convertView;
    }
}
