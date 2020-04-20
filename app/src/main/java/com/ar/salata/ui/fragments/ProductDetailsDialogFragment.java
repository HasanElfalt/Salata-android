package com.ar.salata.ui.fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ar.salata.R;
import com.ar.salata.model.GalleryProduct;

public class ProductDetailsDialogFragment extends DialogFragment {
    private GalleryProduct product;

    public ProductDetailsDialogFragment() {
    }

    public static ProductDetailsDialogFragment newInstance(GalleryProduct product){
        ProductDetailsDialogFragment productDetailsDialogFragment = new ProductDetailsDialogFragment();
        Bundle data = new Bundle();
        data.putParcelable("product",product);
        productDetailsDialogFragment.setArguments(data);
        return productDetailsDialogFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        this.product = getArguments().getParcelable("product");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.gallery_product_view, null);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        ((TextView)view.findViewById(R.id.product_name_text_view)).setText(product.getProductName()+"/"+product.getUnit());
        ((TextView)view.findViewById(R.id.product_name_text_view)).setTextAppearance(R.style.DialogProductNameTextAppearance);
        ((TextView)view.findViewById(R.id.product_price_text_view)).setText(product.getMaxPrice().toString());
        ((TextView)view.findViewById(R.id.product_price_text_view)).setTextAppearance(R.style.DialogPriceTextAppearance);
        ((ImageView)view.findViewById(R.id.product_image_view)).setImageResource(R.drawable.ic_tomato_background);
        ((TextView)view.findViewById(R.id.gneeh)).setTextAppearance(R.style.DialogProductNameTextAppearance);
        return dialog;
    }

}
