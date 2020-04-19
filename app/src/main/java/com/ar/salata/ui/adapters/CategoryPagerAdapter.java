package com.ar.salata.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ar.salata.model.Category;

import java.util.ArrayList;

public abstract class CategoryPagerAdapter extends FragmentStateAdapter {
    private ArrayList<Category> productCategories;

    public CategoryPagerAdapter(@NonNull Fragment fragment, ArrayList<Category> productCategories) {
        super(fragment);
        this.productCategories = productCategories;
    }

    @Override
    public int getItemCount() {
        return productCategories.size();
    }

    protected ArrayList<Category> getProductCategories() {
        return productCategories;
    }
}
