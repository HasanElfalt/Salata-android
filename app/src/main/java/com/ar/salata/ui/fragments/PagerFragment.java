package com.ar.salata.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.ar.salata.R;
import com.ar.salata.repositories.model.Category;
import com.ar.salata.repositories.model.SliderItem;
import com.ar.salata.ui.activities.HomeActivity;
import com.ar.salata.ui.adapters.CategoryPagerAdapter;
import com.ar.salata.ui.adapters.ImageSliderAdapter;
import com.ar.salata.viewmodels.GoodsViewModel;
import com.ar.salata.viewmodels.SliderItemViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class PagerFragment extends Fragment {

    private CategoryPagerAdapter adapter;
    private ViewPager2 viewPager;
    protected ArrayList<Category> productCategories = new ArrayList<>();
    protected GoodsViewModel goodsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goodsViewModel = new ViewModelProvider(this).get(GoodsViewModel.class);
        // end points to download categories
/*
		productCategories = new ArrayList<>();
		productCategories.add(new Category(1, "خضروات"));
		productCategories.add(new Category(2, "فاكهة"));
		productCategories.add(new Category(3, "مكسرات"));
		productCategories.add(new Category(4, "بقوليات"));
*/
        /*
        if (getArguments() != null) {

        }
        */
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_pager, container, false);
		
		if (getActivity() instanceof HomeActivity) {
			view.findViewById(R.id.appbar_pager_fragment).setVisibility(View.VISIBLE);
			SliderView sliderView = view.findViewById(R.id.imageSlider);
			
			ImageSliderAdapter adapter = new ImageSliderAdapter(getContext());
			
			sliderView.setSliderAdapter(adapter);
			
			sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
			sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
			sliderView.setIndicatorSelectedColor(Color.WHITE);
			sliderView.setIndicatorUnselectedColor(Color.GRAY);
			sliderView.setScrollTimeInSec(4);
			sliderView.startAutoCycle();
			
			SliderItemViewModel sliderItemViewModel = new ViewModelProvider(this).get(SliderItemViewModel.class);
			
			for (SliderItem item : sliderItemViewModel.getSliderItems()) {
				adapter.addItem(item);
			}
		}
		
		return view;
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
