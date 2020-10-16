package com.ar.salata.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.repositories.model.OrderUnit;
import com.ar.salata.repositories.model.StockProduct;
import com.ar.salata.ui.utils.ArabicString;
import com.ar.salata.viewmodels.OrderViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

public class CartRecyclerAdapter extends RecyclerView.Adapter {
    private final static int HEADER_VIEW = 1;
    private final static int NORMAL_VIEW = 0;

    private ArrayList<StockProduct> products;
    private Context context;
    private OrderViewModel orderViewModel;
    private Order order;
    private List<String> phones;
    private String mode;

    public CartRecyclerAdapter(Context context, ArrayList<StockProduct> products, OrderViewModel orderViewModel, List<String> phones, String mode) {
        this.products = products;
        this.context = context;
        this.orderViewModel = orderViewModel;
        order = this.orderViewModel.getOrderMutableLiveData().getValue();
        this.phones = phones;
        this.mode = mode;
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
        switch (getItemViewType(position)) {
            case HEADER_VIEW: {
                final ItemViewHolderHeader itemViewHolderHeader = (ItemViewHolderHeader) holder;
                itemViewHolderHeader.deliveryDate.setText("موعد التسليم: " + order.getOrderDateDay() + " الساعة: " + order.getOrderDateHour(false));
                itemViewHolderHeader.phoneNumber1.setText(ArabicString.toArabic("ت/ " + phones.get(0)));
                itemViewHolderHeader.phoneNumber2.setText(ArabicString.toArabic("ت/ " + phones.get(1)));
                break;
            }
            case NORMAL_VIEW: {
                final ItemViewHolderNormal itemViewHolderNormal = (ItemViewHolderNormal) holder;
                final Double itemPrice = products.get(position - 1).getPrice();
                final String itemImageURL = products.get(position - 1).getInvoiceImage();
                final String itemUnit = products.get(position - 1).getUnitName();

                double orderUnitRemain = 0;
                for (OrderUnit tempUnit : order.getUnits()) {
                    if (products.get(position - 1).getId() == tempUnit.getProductId()) {
                        itemViewHolderNormal.weight = tempUnit.getCount();
                        orderUnitRemain = tempUnit.getCount() + products.get(position - 1).getRemain();
                        break;
                    }
                }

                itemViewHolderNormal.itemNameTextView.setText(ArabicString.toArabic(products.get(position - 1).getProductName()));
                itemViewHolderNormal.itemPriceTextView.setText(ArabicString.toArabic(itemPrice.toString() + " جنيه/" + itemUnit));

                Glide.with(holder.itemView)
                        .load(itemImageURL)
                        .fitCenter()
                        .into(itemViewHolderNormal.itemImage);


                itemViewHolderNormal.totalWeightTextView.setText(ArabicString.toArabic(String.valueOf(itemViewHolderNormal.weight)));
                itemViewHolderNormal.totalPriceTextView.setText(ArabicString.toArabic(String.valueOf(round(itemViewHolderNormal.weight * itemPrice * 100) / 100.0)));

                itemViewHolderNormal.decrementImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemViewHolderNormal.weight > 0) {
                            itemViewHolderNormal.weight -= products.get(position - 1).getStep();
                            itemViewHolderNormal.totalWeightTextView.setText(ArabicString.toArabic(String.valueOf(itemViewHolderNormal.weight)));
                            itemViewHolderNormal.totalPriceTextView.setText(ArabicString.toArabic(String.valueOf(round(itemViewHolderNormal.weight * itemPrice * 100) / 100.0)));
                            order.addUnit(new OrderUnit(products.get(position - 1), itemViewHolderNormal.weight));
                            orderViewModel.setOrderValue(order);
                        }
                    }
                });

                double finalOrderUnitRemain = orderUnitRemain;
                itemViewHolderNormal.incrementImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mode == "create") {
                            if (!(itemViewHolderNormal.weight + products.get(position - 1).getStep() > products.get(position - 1).getRemain())) {
                                itemViewHolderNormal.weight += products.get(position - 1).getStep();
                                itemViewHolderNormal.totalWeightTextView.setText(ArabicString.toArabic(String.valueOf(itemViewHolderNormal.weight)));
                                itemViewHolderNormal.totalPriceTextView.setText(ArabicString.toArabic(String.valueOf(round(itemViewHolderNormal.weight * itemPrice * 100) / 100.0)));
                                order.addUnit(new OrderUnit(products.get(position - 1), itemViewHolderNormal.weight));
                                orderViewModel.setOrderValue(order);
                            }
                        } else if (mode == "edit") {
                            if (!(itemViewHolderNormal.weight + products.get(position - 1).getStep() > finalOrderUnitRemain)) {
                                itemViewHolderNormal.weight += products.get(position - 1).getStep();
                                itemViewHolderNormal.totalWeightTextView.setText(ArabicString.toArabic(String.valueOf(itemViewHolderNormal.weight)));
                                itemViewHolderNormal.totalPriceTextView.setText(ArabicString.toArabic(String.valueOf(round(itemViewHolderNormal.weight * itemPrice * 100) / 100.0)));
                                order.addUnit(new OrderUnit(products.get(position - 1), itemViewHolderNormal.weight));
                                orderViewModel.setOrderValue(order);
                            }
                        }
                    }
                });
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER_VIEW;
        else
            return NORMAL_VIEW;
    }

    @Override
    public int getItemCount() {
        return products.size() + 1;
    }

    class ItemViewHolderNormal extends RecyclerView.ViewHolder {
        double weight = 0;
        ImageButton incrementImageButton;
        ImageButton decrementImageButton;

        TextView totalWeightTextView;
        TextView totalPriceTextView;
        TextView itemPriceTextView;
        TextView itemNameTextView;

        ImageView itemImage;

        public ItemViewHolderNormal(@NonNull View itemView) {
            super(itemView);

            incrementImageButton = itemView.findViewById(R.id.btn_increment_weight);
            decrementImageButton = itemView.findViewById(R.id.btn_decrement_weight);

            itemImage = itemView.findViewById(R.id.iv_item_image_add_to_cart);

            totalWeightTextView = itemView.findViewById(R.id.tv_total_weight);
            totalPriceTextView = itemView.findViewById(R.id.tv_total_price);
            itemPriceTextView = itemView.findViewById(R.id.tv_price);
            itemNameTextView = itemView.findViewById(R.id.tv_name);
        }
    }

    class ItemViewHolderHeader extends RecyclerView.ViewHolder {
        TextView deliveryDate;
        TextView phoneNumber1;
        TextView phoneNumber2;

        public ItemViewHolderHeader(@NonNull View itemView) {
            super(itemView);
            deliveryDate = itemView.findViewById(R.id.tv_delivery_date_add_to_cart);
            phoneNumber1 = itemView.findViewById(R.id.tv_phone_1_add_to_cart);
            phoneNumber2 = itemView.findViewById(R.id.tv_phone_2_add_to_cart);
        }
    }
}
