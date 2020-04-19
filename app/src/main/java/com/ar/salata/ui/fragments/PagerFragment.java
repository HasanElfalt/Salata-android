package com.ar.salata.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ar.salata.R;
import com.ar.salata.model.Category;
import com.ar.salata.ui.adapters.CategoryPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class PagerFragment extends Fragment {

    private CategoryPagerAdapter adapter;
    private ViewPager2 viewPager;
    private ArrayList<Category> productCategories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // end points to download categories
        productCategories = new ArrayList<>();
        productCategories.add(new Category("1", "خضروات"));
        productCategories.add(new Category("1", "فاكهة"));
        /*
        if (getArguments() != null) {

        }
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        TabLayout productsTabs = view.findViewById(R.id.products_tabes);

        new TabLayoutMediator(productsTabs, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(productCategories.get(position).getCategoryName());
            }
        }).attach();
    }

    protected void setAdapter(CategoryPagerAdapter adapter) {
        this.adapter = adapter;
    }

    protected ArrayList<Category> getProductCategories() {
        return productCategories;
    }
}
