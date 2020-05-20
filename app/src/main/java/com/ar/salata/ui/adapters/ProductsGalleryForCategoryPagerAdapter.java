package com.ar.salata.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ar.salata.repositories.model.Category;
import com.ar.salata.ui.fragments.ProductsGalleryForCategoryFragment;

import java.util.ArrayList;

public class ProductsGalleryForCategoryPagerAdapter extends CategoryPagerAdapter {
    public ProductsGalleryForCategoryPagerAdapter(@NonNull Fragment fragment, ArrayList<Category> productCategories) {
        super(fragment, productCategories);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ProductsGalleryForCategoryFragment.newInstance(getProductCategories().get(position));
    }
}
