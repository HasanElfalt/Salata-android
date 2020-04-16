package com.ar.salata.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ar.salata.R;
import com.ar.salata.ui.adapters.ProductsGalleryForCategoryAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


/**
 * create an instance of this fragment.
 */
public class ProductsGalleryPagerFragment extends Fragment {

    private ProductsGalleryForCategoryAdapter adapter;
    private ViewPager2 productGalleryPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products_gallery_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ProductsGalleryForCategoryAdapter(this);
        productGalleryPager = view.findViewById(R.id.product_gallery_pager);
        productGalleryPager.setAdapter(adapter);
        TabLayout productsTabs = view.findViewById(R.id.products_tabes);

        new TabLayoutMediator(productsTabs,productGalleryPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText("Tab#"+position);

            }
        }).attach();
    }
}
