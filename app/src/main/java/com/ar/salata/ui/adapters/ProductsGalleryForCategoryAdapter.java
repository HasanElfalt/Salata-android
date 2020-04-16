package com.ar.salata.ui.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ar.salata.model.Product;
import com.ar.salata.ui.fragments.ProductsGalleryForCategoryFragment;

import java.util.ArrayList;

public class ProductsGalleryForCategoryAdapter extends FragmentStateAdapter {

    public ProductsGalleryForCategoryAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // to be replaced by end point request
        Product product =  new Product("Product#"+position);
        ArrayList<Product> list = new ArrayList();
        list.add(product);

        Fragment productsForCategoryFragment = ProductsGalleryForCategoryFragment.newInstance(list);
        return productsForCategoryFragment;
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
