package com.example.rogerzzzz.compara.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rogerzzzz.compara.MapActivity;
import com.example.rogerzzzz.compara.R;
import com.example.rogerzzzz.compara.models.SupermarketModel;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by rogerzzzz on 2016/12/7.
 */

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketViewHolder> implements View.OnClickListener{
    private ArrayList<SupermarketModel> list;
    private Context context;

    public MarketAdapter(ArrayList<SupermarketModel> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public MarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.market_list_adapter, parent, false);
        TextView tv = (TextView) v.findViewById(R.id.market_name);
        tv.setOnClickListener(this);
        return new MarketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MarketViewHolder holder, int position) {
        holder.marketName_tv.setText(list.get(position).getName());
        if(list.get(position).getPrice().equals("-1")){
            holder.price_tv.setText("Can't buy in this supermarket");
        }else{
            holder.price_tv.setText(list.get(position).getPrice() + "HKD");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.market_name:
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("supermarket_name", ((TextView) v).getText());
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            default:
                break;
        }
    }

    public static class MarketViewHolder extends RecyclerView.ViewHolder{
        public TextView marketName_tv;
        public TextView price_tv;

        public MarketViewHolder(View itemView) {
            super(itemView);
            marketName_tv = (TextView) itemView.findViewById(R.id.market_name);
            price_tv = (TextView) itemView.findViewById(R.id.price);
        }
    }
}
