package com.ar.salata.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;

import java.util.ArrayList;

public class FinalBillRecyclerAdapter extends RecyclerView.Adapter<FinalBillRecyclerAdapter.ItemViewHolder> {
    private final static int FOOTER_VIEW = 2;
    private final static int HEADER_VIEW = 1;
    private final static int NORMAL_VIEW = 0;

    private ArrayList<Integer> data;

    private Context context;

    public FinalBillRecyclerAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            data.add(i);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == FOOTER_VIEW)
            view = LayoutInflater.from(context).inflate(R.layout.footer_rv_final_bill, parent, false);
        else if (viewType == HEADER_VIEW)
            view = LayoutInflater.from(context).inflate(R.layout.header_rv_bill, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.itemview_rv_bill, parent, false);

        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (getItemViewType(position) == NORMAL_VIEW) {
/*
            holder.itemWeight.setText(position);
            holder.itemWeight.setText(position * 2);
*/
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER_VIEW;
        else if (position == data.size() + 1)
            return FOOTER_VIEW;
        else
            return NORMAL_VIEW;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemWeight;
        TextView itemPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemWeight = itemView.findViewById(R.id.tv_item_total_weight);
            itemPrice = itemView.findViewById(R.id.tv_item_total_price);
        }

    }
}
