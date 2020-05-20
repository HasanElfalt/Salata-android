package com.ar.salata.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.model.Product;
import com.ar.salata.ui.adapters.CartRecyclerAdapter;

import java.util.ArrayList;

public class AddToCartFragment extends Fragment {
    private RecyclerView cartRecyclerView;
    private ArrayList<Product> products = new ArrayList<>();

    public static AddToCartFragment newInstance() {
        return new AddToCartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_cart, container, false);
        cartRecyclerView = view.findViewById(R.id.rv_add_to_cart);

        for (int i = 0; i < 20; i++) {
            products.add(new Product("خيار", 50.0, "كيلو", ""));
        }
        CartRecyclerAdapter cartRecyclerAdapter = new CartRecyclerAdapter(getContext(), products);
        cartRecyclerAdapter.setHasStableIds(true);
        cartRecyclerView.setAdapter(cartRecyclerAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
