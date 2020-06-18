package com.ar.salata.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.ui.adapters.FinalBillRecyclerAdapter;
import com.ar.salata.viewmodels.OrderViewModel;
import com.ar.salata.viewmodels.UserViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class PayActivity extends BaseActivity {
    private static final int REQUESTORDER = 2;
    private ExtendedFloatingActionButton efab;
    private OrderViewModel orderViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        setSupportActionBar(toolbar);

        orderViewModel = new ViewModelProvider(PayActivity.this).get(OrderViewModel.class);
        userViewModel = new ViewModelProvider(PayActivity.this).get(UserViewModel.class);

        String orderLocalId = getIntent().getStringExtra(OrderViewModel.ORDER_ID);

        Order order = orderViewModel.getOrder(orderLocalId);
        APIToken token = userViewModel.getToken();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        RecyclerView recyclerView = findViewById(R.id.rv_bill_final);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FinalBillRecyclerAdapter adapter = new FinalBillRecyclerAdapter(getApplicationContext(), order);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        efab = findViewById(R.id.efab_confirm);
        efab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayActivity.this, OrdersActivity.class);
                orderViewModel.submitOrder(token, order);
                startActivityForResult(intent, REQUESTORDER);
            }
        });

        NestedScrollView payScrollView = findViewById(R.id.nsv_pay);

        payScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    efab.hide();
                } else if (oldScrollY > scrollY) {
                    efab.show();
                }
            }
        });

    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_pay);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_pay;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTORDER && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
