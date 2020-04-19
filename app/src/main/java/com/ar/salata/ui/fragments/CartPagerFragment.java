package com.ar.salata.ui.fragments;

import android.os.Bundle;

import com.ar.salata.ui.adapters.CartForCategoryPagerAdapter;

public class CartPagerFragment extends PagerFragment {
    private CartForCategoryPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new CartForCategoryPagerAdapter(this, getProductCategories());
        setAdapter(adapter);
    }
}
