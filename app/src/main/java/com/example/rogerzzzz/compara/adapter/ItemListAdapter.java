package com.example.rogerzzzz.compara.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rogerzzzz.compara.R;
import com.example.rogerzzzz.compara.models.Goods;

import java.util.ArrayList;

/**
 * Created by rogerzzzz on 2016/12/3.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {
    private ArrayList<Goods> list;

    public ItemListAdapter(ArrayList<Goods> list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_list_adapter, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_unit.setText(list.get(position).getUnit());
        holder.tv_price.setText(list.get(position).getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_price;
        TextView tv_unit;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = bindTextView(R.id.name, itemView);
            tv_price = bindTextView(R.id.price, itemView);
            tv_unit = bindTextView(R.id.unit, itemView);
        }

        private TextView bindTextView(int id, View view){
            return (TextView) view.findViewById(id);
        }
    }
}
