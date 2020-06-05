package com.ar.salata.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.model.Category;
import com.ar.salata.repositories.model.Product;
import com.ar.salata.repositories.model.ProductList;
import com.ar.salata.ui.activities.HomeActivity;
import com.ar.salata.ui.adapters.ProductGalleryViewRecyclerAdapter;
import com.ar.salata.ui.utils.OffsetDecoration;
import com.ar.salata.viewmodels.GoodsViewModel;

import java.util.ArrayList;

public class ProductsGalleryForCategoryFragment extends Fragment {

    private static final String PRODUCTS_CATEGORY = "category";
    private boolean fabVisibility = true;

    private Category categoryOFProductsToBeDisplayed;
    private ArrayList<Product> productsList = new ArrayList<>();

    private GoodsViewModel goodsViewModel;

    public ProductsGalleryForCategoryFragment() {
    }

    public static ProductsGalleryForCategoryFragment newInstance(Category categoryOFProductsToBeDisplayed) {
        ProductsGalleryForCategoryFragment fragment = new ProductsGalleryForCategoryFragment();

        Bundle args = new Bundle();
        args.putParcelable(PRODUCTS_CATEGORY, categoryOFProductsToBeDisplayed);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryOFProductsToBeDisplayed = getArguments().getParcelable(PRODUCTS_CATEGORY);
        }
		
/*
		productsList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			productsList.add(new Product("طماطم", 45.2, "كيلو", ""));
			productsList.add(new Product("بطاطس", 45.2, "كيلو", ""));
			productsList.add(new Product("بصل", 45.2, "كيلو", ""));
			productsList.add(new Product("فلفل", 45.2, "كيلو", ""));
		}
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_gallery_for_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goodsViewModel = new ViewModelProvider(this).get(GoodsViewModel.class);

        RecyclerView productGalleryRecyclerView = view.findViewById(R.id.products_gallery);
        RecyclerView.Adapter productsAdapter = new ProductGalleryViewRecyclerAdapter(productsList, this);
        productGalleryRecyclerView.setAdapter(productsAdapter);

        RecyclerView.LayoutManager productsViewManager = new GridLayoutManager(this.getActivity(), 4);
        productGalleryRecyclerView.setLayoutManager(productsViewManager);

        OffsetDecoration itemDecoration = new OffsetDecoration(getContext(), R.dimen.product_item_offset);
        productGalleryRecyclerView.addItemDecoration(itemDecoration);

        NestedScrollView productsGalleryScrollView = view.findViewById(R.id.sv_product_gallery);

        productsGalleryScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    ((HomeActivity) getActivity()).setEFABVisibility(false);
                    fabVisibility = false;
                } else if (oldScrollY > scrollY) {
                    ((HomeActivity) getActivity()).setEFABVisibility(true);
                    fabVisibility = true;
                }
            }
        });

        MutableLiveData<ProductList> productListMutableLiveData = goodsViewModel.getProducts(categoryOFProductsToBeDisplayed.getCategoryID());
        productListMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ProductList>() {
            @Override
            public void onChanged(ProductList productList) {
                productsList.addAll(productList.getProductList());
                productsAdapter.notifyDataSetChanged();
            }
        });
    }
	
	@Override
	public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setEFABVisibility(fabVisibility);
    }
}
