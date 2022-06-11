package com.ar.salata.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.Category;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.repositories.model.OrderUnit;
import com.ar.salata.repositories.model.StockProduct;
import com.ar.salata.repositories.model.StockProductList;
import com.ar.salata.ui.activities.AddToCartActivity;
import com.ar.salata.ui.activities.OrderEditActivity;
import com.ar.salata.ui.adapters.CartRecyclerAdapter;
import com.ar.salata.viewmodels.AppConfigViewModel;
import com.ar.salata.viewmodels.GoodsViewModel;
import com.ar.salata.viewmodels.OrderViewModel;
import com.ar.salata.viewmodels.UserViewModel;

import java.util.ArrayList;

public class AddToCartFragment extends Fragment {
    private static final String PRODUCTS_CATEGORY = "category";
    private RecyclerView cartRecyclerView;
    private ArrayList<StockProduct> products = new ArrayList<>();
    private Category categoryOFProductsToBeDisplayed;
    private GoodsViewModel goodsViewModel;
    private OrderViewModel orderViewModel;
    private AppConfigViewModel appConfigViewModel;
    private UserViewModel userViewModel;
    private MutableLiveData<UserRepository.APIResponse> productsApiResponse;

    public static AddToCartFragment newInstance(Category category) {
        AddToCartFragment fragment = new AddToCartFragment();

        Bundle args = new Bundle();
        args.putParcelable(PRODUCTS_CATEGORY, category);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryOFProductsToBeDisplayed = getArguments().getParcelable(PRODUCTS_CATEGORY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_cart, container, false);

        cartRecyclerView = view.findViewById(R.id.rv_add_to_cart);
        appConfigViewModel = new ViewModelProvider(this).get(AppConfigViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        goodsViewModel = new ViewModelProvider(this).get(GoodsViewModel.class);

        String mode = "create";
        if (getActivity() instanceof AddToCartActivity) {
            orderViewModel = ((AddToCartActivity) getActivity()).getOrderViewModel();
        } else if (getActivity() instanceof OrderEditActivity) {
            orderViewModel = ((OrderEditActivity) getActivity()).getOrderViewModel();
            mode = "edit";
        }

        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.show(getActivity().getSupportFragmentManager(), null);

        CartRecyclerAdapter cartRecyclerAdapter = new CartRecyclerAdapter(getContext(), products, orderViewModel, appConfigViewModel.getPhones(), mode);
        cartRecyclerAdapter.setHasStableIds(true);
        cartRecyclerView.setAdapter(cartRecyclerAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ///////// From here to down will be commented /////////////////
        productsApiResponse = goodsViewModel.loadProducts(userViewModel.getToken(), orderViewModel.getOrderMutableLiveData().getValue().getAddressId());

        productsApiResponse.observe(getViewLifecycleOwner(), new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse response) {
                switch (response) {
                    case SUCCESS: {
                        loadingDialogFragment.dismiss();
                        goodsViewModel.getProducts(categoryOFProductsToBeDisplayed.getCategoryID(), orderViewModel.getOrderMutableLiveData().getValue().getAddressId()).observe(getViewLifecycleOwner(), new Observer<StockProductList>() {
                            @Override
                            public void onChanged(StockProductList productList) {
                                if (getActivity() instanceof AddToCartActivity) {
                                    for (StockProduct product : productList.getProductList()) {
                                        if (product.getRemain() > 0)
                                            products.add(product);
                                    }
                                } else if (getActivity() instanceof OrderEditActivity) {
//                                    products.addAll(productList.getProductList());
                                    Order order = orderViewModel.getOrderMutableLiveData().getValue();
                                    ArrayList<Integer> ids = new ArrayList<>();
                                    for (OrderUnit unit : order.getUnits()) {
                                        ids.add(unit.getProductId());
                                    }
                                    for (StockProduct stockProduct : productList.getProductList()) {
                                        if (stockProduct.getRemain() > 0 || ids.contains(stockProduct.getId())) {
                                            products.add(stockProduct);
                                        }
                                    }
                                }
                                cartRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                        break;
                    }
                    case ERROR: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم", false);
                        dialogFragment.show(getActivity().getSupportFragmentManager(), null);
                        break;
                    }
                    case FAILED: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), false);
                        dialogFragment.show(getActivity().getSupportFragmentManager(), null);
                        break;
                    }

                }
            }
        });

        return view;
    }
}
