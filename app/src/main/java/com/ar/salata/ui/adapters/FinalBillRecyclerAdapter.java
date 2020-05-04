package com.ar.salata.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.model.Product;

import java.util.ArrayList;

public class FinalBillRecyclerAdapter extends RecyclerView.Adapter {
    private final static int FOOTER_VIEW = 2;
    private final static int HEADER_VIEW = 1;
    private final static int NORMAL_VIEW = 0;
	
	private ArrayList<Product> data;

    private Context context;

    public FinalBillRecyclerAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
	
		for (int i = 0; i < 20; i++) {
			data.add(new Product("طماطم حمرا و جامدة للسلطة", 5.0, "كجم", ""));
        }
    }

    @NonNull
    @Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
		RecyclerView.ViewHolder viewHolder;
		switch (viewType) {
			case FOOTER_VIEW:
				view = LayoutInflater.from(context).inflate(R.layout.footer_rv_final_bill, parent, false);
				viewHolder = new FooterItemViewHolder(view);
				break;
			case HEADER_VIEW:
				view = LayoutInflater.from(context).inflate(R.layout.header_rv_bill, parent, false);
				viewHolder = new HeaderItemViewHolder(view);
				break;
			default:
				view = LayoutInflater.from(context).inflate(R.layout.itemview_rv_bill, parent, false);
				viewHolder = new NormalItemViewHolder(view);
				break;
		}
		return viewHolder;
    }

    @Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		HeaderItemViewHolder headerItemViewHolder;
		FooterItemViewHolder footerItemViewHolder;
		NormalItemViewHolder normalItemViewHolder;
	
		switch (getItemViewType(position)) {
			case HEADER_VIEW:
				headerItemViewHolder = (HeaderItemViewHolder) holder;
				headerItemViewHolder.deliveryDate.setText(R.string.demo_bill_delivery_date);
				break;
			case FOOTER_VIEW:
				footerItemViewHolder = (FooterItemViewHolder) holder;
				footerItemViewHolder.billPrice.setText(R.string.demo_bill_price);
				break;
			case NORMAL_VIEW:
				normalItemViewHolder = (NormalItemViewHolder) holder;
				Product product = data.get(position - 1);
				normalItemViewHolder.itemName.setText(product.getProductName());
				normalItemViewHolder.itemPrice.setText(String.valueOf(product.getMaxPrice()) + "جنيه/" + product.getUnit());
				break;
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
	
	class NormalItemViewHolder extends RecyclerView.ViewHolder {
		
		TextView itemName;
        TextView itemPrice;
		TextView itemTotalWeight;
		TextView itemTotalPrice;
		
		public NormalItemViewHolder(@NonNull View itemView) {
            super(itemView);
			itemName = itemView.findViewById(R.id.tv_item_name);
			itemPrice = itemView.findViewById(R.id.tv_item_price);
			itemTotalWeight = itemView.findViewById(R.id.tv_item_total_weight);
			itemTotalPrice = itemView.findViewById(R.id.tv_item_total_price);
		}
		
	}
	
	class FooterItemViewHolder extends RecyclerView.ViewHolder {
		
		TextView billPrice;
		
		public FooterItemViewHolder(@NonNull View itemView) {
			super(itemView);
			billPrice = itemView.findViewById(R.id.tv_bill_price);
		}
	}
	
	class HeaderItemViewHolder extends RecyclerView.ViewHolder {
		
		TextView deliveryDate;
		
		public HeaderItemViewHolder(@NonNull View itemView) {
			super(itemView);
			deliveryDate = itemView.findViewById(R.id.tv_delivery_date);
		}
	}
}
