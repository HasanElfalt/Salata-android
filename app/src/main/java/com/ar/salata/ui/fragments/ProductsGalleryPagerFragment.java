package com.ar.salata.ui.fragments;

import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.ar.salata.repositories.model.CategoryList;
import com.ar.salata.ui.adapters.ProductsGalleryForCategoryPagerAdapter;

public class ProductsGalleryPagerFragment extends PagerFragment {
    private ProductsGalleryForCategoryPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ProductsGalleryForCategoryPagerAdapter(this, getProductCategories());
        goodsViewModel.getCategories().observe(this, new Observer<CategoryList>() {
            @Override
            public void onChanged(CategoryList categoryList) {
                productCategories.addAll(categoryList.getCategoryList());
                adapter.notifyDataSetChanged();
            }
        });
        setAdapter(adapter);
    }
}
