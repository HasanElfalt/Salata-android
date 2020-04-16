package com.ar.salata.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.salata.R;
import com.ar.salata.model.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsGalleryForCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsGalleryForCategoryFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PRODUCTS = "products";



    private ArrayList<Product> mProduct;


    public ProductsGalleryForCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsGalleryForCategoryFragment newInstance(ArrayList<Product> products) {
        ProductsGalleryForCategoryFragment fragment = new ProductsGalleryForCategoryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PRODUCTS,products);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = getArguments().getParcelableArrayList(PRODUCTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_gallery_for_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.page_text)).setText(mProduct.get(0).getProductName());
    }
}
