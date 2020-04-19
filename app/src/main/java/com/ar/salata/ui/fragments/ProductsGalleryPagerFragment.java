package com.ar.salata.ui.fragments;

import android.os.Bundle;

import com.ar.salata.ui.adapters.ProductsGalleryForCategoryPagerAdapter;

public class ProductsGalleryPagerFragment extends PagerFragment {
    private ProductsGalleryForCategoryPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ProductsGalleryForCategoryPagerAdapter(this, getProductCategories());
        setAdapter(adapter);
    }
}
