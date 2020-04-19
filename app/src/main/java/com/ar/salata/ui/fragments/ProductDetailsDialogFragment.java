package com.ar.salata.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ar.salata.R;
import com.ar.salata.model.GalleryProduct;

public class ProductDetailsDialogFragment extends DialogFragment {
    GalleryProduct product;

    public ProductDetailsDialogFragment(GalleryProduct product) {
        this.product = product;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.gallery_product_view, null);
        builder.setView(view);
//        ((TextView)view.findViewById(R.id.product_name_text_view)).setText(product.getProductName()+"/"+product.getUnit());
//        ((TextView)view.findViewById(R.id.product_price_text_view)).setText(product.getMaxPrice().toString());
        Dialog dialog = builder.create();
        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        return dialog;
    }
}
