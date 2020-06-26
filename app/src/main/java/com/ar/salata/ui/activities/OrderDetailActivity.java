package com.ar.salata.ui.activities;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.ui.adapters.FinalBillRecyclerAdapter;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.fragments.LoadingDialogFragment;
import com.ar.salata.viewmodels.AppConfigViewModel;
import com.ar.salata.viewmodels.OrderViewModel;
import com.ar.salata.viewmodels.UserViewModel;

import static com.ar.salata.viewmodels.OrderViewModel.ORDER_ID;

public class OrderDetailActivity extends BaseActivity {

    private AppConfigViewModel appConfigViewModel;
    private OrderViewModel orderViewModel;
    private UserViewModel userViewModel;
    private int orderId;
    private MutableLiveData<UserRepository.APIResponse> orderProductsLiveData;
    private Order order;
    private FinalBillRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appConfigViewModel = new ViewModelProvider(this).get(AppConfigViewModel.class);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        orderId = getIntent().getIntExtra(ORDER_ID, 0);

        RecyclerView recyclerView = findViewById(R.id.rv_order_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(false);

        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.show(getSupportFragmentManager(), null);

        orderProductsLiveData = orderViewModel.loadOrderProducts(userViewModel.getToken(), orderId);
        orderProductsLiveData.observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse response) {
                switch (response) {
                    case SUCCESS: {
                        order = orderViewModel.getOrder(orderId);
                        adapter = new FinalBillRecyclerAdapter(getApplicationContext(), order, appConfigViewModel.getPhones());
                        adapter.setHasStableIds(true);
                        recyclerView.setAdapter(adapter);
                        loadingDialogFragment.dismiss();
                        break;
                    }
                    case ERROR: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم", true);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                    case FAILED: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), true);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                }
            }
        });


        FinalBillRecyclerAdapter adapter = new FinalBillRecyclerAdapter(getApplicationContext(), new Order(), appConfigViewModel.getPhones());
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_Order_detail);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_order_detail;
    }
}
