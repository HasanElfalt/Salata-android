package com.ar.salata.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.model.Product;
import com.ar.salata.ui.fragments.ProductDetailsDialogFragment;
import com.ar.salata.ui.utils.ArabicString;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ProductGalleryViewRecyclerAdapter extends RecyclerView.Adapter {
	
	ArrayList<Product> products;
	Fragment myFragment;
	
	public ProductGalleryViewRecyclerAdapter(ArrayList<Product> products, Fragment myFragment) {
		this.products = products;
		this.myFragment = myFragment;
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_product_view, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);
        return viewHolder;
    }
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder viewHolder = (ProductViewHolder) holder;
        viewHolder.productName.setText(products.get(position).getProductName() + "/" + products.get(position).getUnitName());
        viewHolder.productPrice.setText(ArabicString.toArabic(products.get(position).getMaxPrice().toString()));
        Glide.with(holder.itemView)
                .load(products.get(position).getBrochureImage())
                .fitCenter()
                .into(viewHolder.productImage);


        final Product product = products.get(position);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsDialogFragment dialogFragment = ProductDetailsDialogFragment.newInstance(product);
                dialogFragment.show(myFragment.getActivity().getSupportFragmentManager(), null);
            }
        });
    }
	
	@Override
	public int getItemCount() {
		return products.size();
	}

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public View parentLayout;

        TextView productName;
        TextView productPrice;

        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView;

            productName = itemView.findViewById(R.id.product_name_text_view);
            productPrice = itemView.findViewById(R.id.product_price_text_view);

            productImage = itemView.findViewById(R.id.product_image_view);
        }

    }
}
