package com.ar.salata.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ar.salata.model.Category;
import com.ar.salata.model.GalleryProduct;
import com.ar.salata.ui.fragments.ProductsGalleryForCategoryFragment;

import java.util.ArrayList;

public class ProductsGalleryForCategoryAdapter extends FragmentStateAdapter {

    private ArrayList<Category> productCategories;
    public ProductsGalleryForCategoryAdapter(@NonNull Fragment fragment, ArrayList<Category> productCategories) {
        super(fragment);
        this.productCategories = productCategories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment productsForCategoryFragment = ProductsGalleryForCategoryFragment.newInstance(productCategories.get(position));
        return productsForCategoryFragment;
    }

    @Override
    public int getItemCount() {
        return productCategories.size();
    }
}
