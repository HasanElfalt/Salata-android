package com.ar.salata.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.model.Order;
import com.ar.salata.ui.activities.OrderPreviewActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class OrdersRecyclerAdapter extends RecyclerView.Adapter {
    private boolean upcomingOrder;
    private Context context;
    private ArrayList<Order> orders;

    public OrdersRecyclerAdapter(Context context, ArrayList<Order> orders, boolean upcomingOrder) {
        this.upcomingOrder = upcomingOrder;
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        if (upcomingOrder) {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_rv_upcoming_orders, parent, false);
            viewHolder = new ItemViewHolderUpcoming(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_rv_previous_orders, parent, false);
            viewHolder = new ItemViewHolderPrevious(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolderUpcoming viewHolderUpcoming = (ItemViewHolderUpcoming) holder;

        viewHolderUpcoming.orderId.setText(orders.get(position).getOrderId());
        viewHolderUpcoming.orderDateDay.setText(orders.get(position).getOrderDateDay());
        viewHolderUpcoming.orderDateHour.setText(orders.get(position).getOrderDateHour());
        viewHolderUpcoming.orderPrice.setText(String.valueOf(orders.get(position).getOrderPrice()));

        viewHolderUpcoming.orderImage.setImageResource(orders.get(position).getOrderImage());

        if (!upcomingOrder) {
            ItemViewHolderPrevious viewHolderPrevious;
            viewHolderPrevious = (ItemViewHolderPrevious) holder;
            viewHolderPrevious.orderDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 4/23/2020
                }
            });
        } else {
            viewHolderUpcoming.editOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OrderPreviewActivity.class);
                    context.startActivity(intent);
                }
            });
            viewHolderUpcoming.cancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 4/23/2020
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ItemViewHolderUpcoming extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView orderDateDay;
        TextView orderDateHour;
        TextView orderPrice;

        ImageView orderImage;

        private MaterialButton editOrder;
        private MaterialButton cancelOrder;

        ItemViewHolderUpcoming(@NonNull View itemView, boolean notDefault) {
            super(itemView);
        }

        ItemViewHolderUpcoming(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.tv_order_id_value);
            orderDateDay = itemView.findViewById(R.id.tv_day_value);
            orderDateHour = itemView.findViewById(R.id.tv_hour_value);
            orderPrice = itemView.findViewById(R.id.tv_order_price_value);

            orderImage = itemView.findViewById(R.id.iv_order_image);

            editOrder = itemView.findViewById(R.id.btn_order_edit);
            cancelOrder = itemView.findViewById(R.id.btn_order_cancel);
        }
    }

    public class ItemViewHolderPrevious extends ItemViewHolderUpcoming {
        private MaterialButton orderDetails;

        ItemViewHolderPrevious(@NonNull View itemView) {
            super(itemView, false);

            orderId = itemView.findViewById(R.id.tv_order_id_value_previous);
            orderDateDay = itemView.findViewById(R.id.tv_day_value_previous);
            orderDateHour = itemView.findViewById(R.id.tv_hour_value_previous);
            orderPrice = itemView.findViewById(R.id.tv_order_price_value_previous);

            orderImage = itemView.findViewById(R.id.iv_order_image_previous);

            orderDetails = itemView.findViewById(R.id.btn_order_details);
        }
    }
}
