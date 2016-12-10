package com.example.rogerzzzz.compara.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rogerzzzz.compara.R;
import com.example.rogerzzzz.compara.models.ProductItem;

import java.util.List;

/**
 * Created by rogerzzzz on 2016/12/10.
 */

public class ScanListAdapter extends RecyclerView.Adapter<ScanListAdapter.ScanViewHolder> {
    private List<ProductItem> list;
    private onItemClickInterface mOnItemClickInterface;

    public ScanListAdapter(List<ProductItem> list){
        this.list = list;
    }

    public static interface onItemClickInterface{
        void onItemClickListener(int position);
    }

    public void setClickListener(onItemClickInterface mOnItemClickInterface){
        this.mOnItemClickInterface = mOnItemClickInterface;
    }


    @Override
    public ScanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_scan_layout, parent, false);
        ScanViewHolder scanViewHolder = new ScanViewHolder(v) {
            @Override
            void addProduct(int position) {
                if(mOnItemClickInterface != null){
                    mOnItemClickInterface.onItemClickListener(position);
                }
            }
        };
        return scanViewHolder;
    }

    @Override
    public void onBindViewHolder(ScanViewHolder holder, int position) {
        holder.item_tv.setText(list.get(position).getProduct_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    abstract class ScanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView item_tv;


        public ScanViewHolder(View itemView) {
            super(itemView);
            item_tv = (TextView) itemView.findViewById(R.id.text);
            item_tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.text:
                    addProduct(getAdapterPosition());
                    break;
                default:
                    break;
            }
        }

        abstract void addProduct(int position);
    }
}
