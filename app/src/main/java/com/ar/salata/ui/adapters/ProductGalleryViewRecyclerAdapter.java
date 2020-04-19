package com.ar.salata.ui.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.model.GalleryProduct;
import com.ar.salata.ui.fragments.ProductDetailsDialogFragment;

import java.util.ArrayList;


public class ProductGalleryViewRecyclerAdapter extends RecyclerView.Adapter {

    ArrayList<GalleryProduct> products;
    Fragment myFragment;

    public ProductGalleryViewRecyclerAdapter(ArrayList<GalleryProduct> products,Fragment myFragment) {
        this.products = products;
        this.myFragment = myFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_product_view,parent,false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextView)((ProductViewHolder)holder).parentLayout.getViewById(R.id.product_name_text_view)).setText(products.get(position).getProductName()+"/"+products.get(position).getUnit());
        ((TextView)((ProductViewHolder)holder).parentLayout.getViewById(R.id.product_price_text_view)).setText(products.get(position).getMaxPrice().toString());
        ((ImageView)((ProductViewHolder)holder).parentLayout.getViewById(R.id.product_image_view)).setImageResource(R.drawable.ic_tomato_background);
        final GalleryProduct product = products.get(position);

        ((ProductViewHolder)holder).parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ProductDetailsDialogFragment dialogFragment = new ProductDetailsDialogFragment(product);
                dialogFragment.show(myFragment.getActivity().getSupportFragmentManager(),"fragment_home_tag");

                return false;
            }

        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout parentLayout;
        public ProductViewHolder(@NonNull ConstraintLayout parentLayout) {
            super(parentLayout);
            this.parentLayout = parentLayout;
        }

    }
}
