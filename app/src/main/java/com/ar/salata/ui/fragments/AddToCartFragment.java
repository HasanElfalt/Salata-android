package com.ar.salata.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.model.Category;
import com.ar.salata.repositories.model.Product;
import com.ar.salata.repositories.model.ProductList;
import com.ar.salata.ui.activities.AddToCartActivity;
import com.ar.salata.ui.adapters.CartRecyclerAdapter;
import com.ar.salata.viewmodels.GoodsViewModel;
import com.ar.salata.viewmodels.OrderViewModel;

import java.util.ArrayList;

public class AddToCartFragment extends Fragment {
    private static final String PRODUCTS_CATEGORY = "category";
    private RecyclerView cartRecyclerView;
    private ArrayList<Product> products = new ArrayList<>();
    private Category categoryOFProductsToBeDisplayed;
    private GoodsViewModel goodsViewModel;
    private OrderViewModel orderViewModel;

    public static AddToCartFragment newInstance(Category category) {
        AddToCartFragment fragment = new AddToCartFragment();

        Bundle args = new Bundle();
        args.putParcelable(PRODUCTS_CATEGORY, category);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryOFProductsToBeDisplayed = getArguments().getParcelable(PRODUCTS_CATEGORY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_cart, container, false);
        cartRecyclerView = view.findViewById(R.id.rv_add_to_cart);

        goodsViewModel = new ViewModelProvider(this).get(GoodsViewModel.class);
        orderViewModel = ((AddToCartActivity) getActivity()).getOrderViewModel();

        CartRecyclerAdapter cartRecyclerAdapter = new CartRecyclerAdapter(getContext(), products, orderViewModel);
        cartRecyclerAdapter.setHasStableIds(true);
        cartRecyclerView.setAdapter(cartRecyclerAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        goodsViewModel.getProducts(categoryOFProductsToBeDisplayed.getCategoryID()).observe(getViewLifecycleOwner(), new Observer<ProductList>() {
            @Override
            public void onChanged(ProductList productList) {
                products.addAll(productList.getProductList());
                cartRecyclerAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
