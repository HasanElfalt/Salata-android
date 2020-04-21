package com.ar.salata.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.model.Product;

import java.util.ArrayList;

public class CartRecyclerAdapter extends RecyclerView.Adapter {
    private final static int HEADER_VIEW = 1;
    private final static int NORMAL_VIEW = 0;

    private ArrayList<Product> products;
    private Context context;

    public CartRecyclerAdapter(Context context, ArrayList<Product> products) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == HEADER_VIEW) {
            view = LayoutInflater.from(context).inflate(R.layout.header_rv_add_to_cart, parent, false);
            viewHolder = new ItemViewHolderHeader(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_rv_add_to_cart, parent, false);
            viewHolder = new ItemViewHolderNormal(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEADER_VIEW) return;

        final ItemViewHolderNormal itemViewHolderNormal = (ItemViewHolderNormal) holder;
        final Double itemPrice = products.get(position - 1).getMaxPrice();

        itemViewHolderNormal.itemNameTextView.setText(products.get(position - 1).getProductName());
        itemViewHolderNormal.itemPriceTextView.setText(itemPrice.toString());

        itemViewHolderNormal.totalWeightTextView.setText(String.valueOf(itemViewHolderNormal.weight));
        itemViewHolderNormal.totalPriceTextView.setText(String.valueOf(itemViewHolderNormal.weight * itemPrice));

        itemViewHolderNormal.decrementImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemViewHolderNormal.weight > 0) {
                    itemViewHolderNormal.weight--;
                    itemViewHolderNormal.totalWeightTextView.setText(String.valueOf(itemViewHolderNormal.weight));
                    itemViewHolderNormal.totalPriceTextView.setText(String.valueOf(itemViewHolderNormal.weight * itemPrice));
                }
            }
        });

        itemViewHolderNormal.incrementImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemViewHolderNormal.weight++;
                itemViewHolderNormal.totalWeightTextView.setText(String.valueOf(itemViewHolderNormal.weight));
                itemViewHolderNormal.totalPriceTextView.setText(String.valueOf(itemViewHolderNormal.weight * itemPrice));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER_VIEW;
        else
            return NORMAL_VIEW;
    }

    class ItemViewHolderNormal extends RecyclerView.ViewHolder {
        int weight = 0;
        ImageButton incrementImageButton;
        ImageButton decrementImageButton;

        TextView totalWeightTextView;
        TextView totalPriceTextView;
        TextView itemPriceTextView;
        TextView itemNameTextView;

        public ItemViewHolderNormal(@NonNull View itemView) {
            super(itemView);

            incrementImageButton = itemView.findViewById(R.id.btn_increment_weight);
            decrementImageButton = itemView.findViewById(R.id.btn_decrement_weight);

            totalWeightTextView = itemView.findViewById(R.id.tv_total_weight);
            totalPriceTextView = itemView.findViewById(R.id.tv_total_price);
            itemPriceTextView = itemView.findViewById(R.id.tv_price);
            itemNameTextView = itemView.findViewById(R.id.tv_name);
        }
    }

    class ItemViewHolderHeader extends RecyclerView.ViewHolder {

        public ItemViewHolderHeader(@NonNull View itemView) {
            super(itemView);
        }
    }
}
