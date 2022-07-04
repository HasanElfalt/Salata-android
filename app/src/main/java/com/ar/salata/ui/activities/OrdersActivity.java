package com.ar.salata.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.ui.adapters.OrdersRecyclerAdapter;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.fragments.LoadingDialogFragment;
import com.ar.salata.viewmodels.OpayViewModel;
import com.ar.salata.viewmodels.OrderViewModel;
import com.ar.salata.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity {
    public static final int UPDATE_ORDER = 1;
    private RecyclerView upcomingOrdersRecyclerView;
    private RecyclerView previousOrdersRecyclerView;
    private ArrayList<Order> upcomingOrders;
    private ArrayList<Order> previousOrders;
    private ConstraintLayout expandUpcomingOrdersLayout;
    private ConstraintLayout expandPreviousOrdersLayout;
    private ImageView expandPreviousOrdersButton;
    private ImageView expandUpcomingOrdersButton;
    private TextView expandPreviousOrdersTextView;
    private TextView expandUpcomingOrdersTextView;
    private OrderViewModel orderViewModel;
    private UserViewModel userViewModel;
    private OpayViewModel opayViewModel;
    private MutableLiveData<UserRepository.APIResponse> loadOrdersResponse;
    private MutableLiveData<List<Order>> getOrdersResponse;
    private OrdersRecyclerAdapter previousOrdersRecyclerAdapter;
    private OrdersRecyclerAdapter upcomingOrdersRecyclerAdapter;
    private MutableLiveData<UserRepository.APIResponse> orderDeleteResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setResult(RESULT_OK);

        expandUpcomingOrdersLayout = findViewById(R.id.cl_upcoming_orders);
        expandPreviousOrdersLayout = findViewById(R.id.cl_previous_orders);
        upcomingOrdersRecyclerView = findViewById(R.id.rv_upcoming_orders);
        previousOrdersRecyclerView = findViewById(R.id.rv_previous_orders);

        expandPreviousOrdersButton = findViewById(R.id.iv_expand_previous_orders);
        expandUpcomingOrdersButton = findViewById(R.id.iv_expand_upcoming_orders);
        expandPreviousOrdersTextView = findViewById(R.id.tv_previous_orders);
        expandUpcomingOrdersTextView = findViewById(R.id.tv_upcoming_orders);

        upcomingOrders = new ArrayList<Order>();
        previousOrders = new ArrayList<Order>();

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        opayViewModel  = new ViewModelProvider(this).get(OpayViewModel.class);


        upcomingOrdersRecyclerAdapter = new OrdersRecyclerAdapter(this, upcomingOrders, orderViewModel, userViewModel, opayViewModel,true);
        previousOrdersRecyclerAdapter = new OrdersRecyclerAdapter(this, previousOrders, orderViewModel, userViewModel, opayViewModel, false);

        loadOrders();

        upcomingOrdersRecyclerView.setAdapter(upcomingOrdersRecyclerAdapter);
        previousOrdersRecyclerView.setAdapter(previousOrdersRecyclerAdapter);

        upcomingOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        previousOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        upcomingOrdersRecyclerView.setNestedScrollingEnabled(false);
        previousOrdersRecyclerView.setNestedScrollingEnabled(false);

        expandUpcomingOrdersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.sv_orders), new AutoTransition());

                if (upcomingOrdersRecyclerView.getVisibility() == View.VISIBLE) {
                    upcomingOrdersRecyclerView.setVisibility(View.GONE);
                    expandUpcomingOrdersButton.setImageResource(R.drawable.ic_expand_more);
                    expandUpcomingOrdersButton.clearColorFilter();
                    expandUpcomingOrdersTextView.setTextColor(getResources().getColor(R.color.colorFont));
                } else {
                    upcomingOrdersRecyclerView.setVisibility(View.VISIBLE);
                    expandUpcomingOrdersButton.setColorFilter(getResources().getColor(R.color.colorAccent));
                    expandUpcomingOrdersButton.setImageResource(R.drawable.ic_expand_less);
                    expandUpcomingOrdersTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });
        expandPreviousOrdersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.sv_orders), new AutoTransition());
                if (previousOrdersRecyclerView.getVisibility() == View.VISIBLE) {
                    previousOrdersRecyclerView.setVisibility(View.GONE);
                    expandPreviousOrdersButton.setImageResource(R.drawable.ic_expand_more);
                    expandPreviousOrdersButton.clearColorFilter();
                    expandPreviousOrdersTextView.setTextColor(getResources().getColor(R.color.colorFont));
                } else {
                    previousOrdersRecyclerView.setVisibility(View.VISIBLE);
                    expandPreviousOrdersButton.setImageResource(R.drawable.ic_expand_less);
                    expandPreviousOrdersButton.setColorFilter(getResources().getColor(R.color.colorAccent));
                    expandPreviousOrdersTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });

    }

    private void loadOrders() {
        loadOrdersResponse = orderViewModel.loadOrders(userViewModel.getToken());

        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.show(getSupportFragmentManager(), null);

        loadOrdersResponse.observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse apiResponse) {
                switch (apiResponse) {
                    case SUCCESS:
                        refreshOrders(loadingDialogFragment);
                        break;
                    case ERROR: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم", false);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                    case FAILED: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), false);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                }
            }
        });
    }

    private void refreshOrders(@Nullable LoadingDialogFragment loadingDialogFragment) {
        getOrdersResponse = orderViewModel.getOrders();
        getOrdersResponse.observe(OrdersActivity.this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                previousOrders.clear();
                upcomingOrders.clear();
                for (Order order : orders) {
                    if (order.isOrderFulfilled()) {
                        previousOrders.add(order);
                    } else {
                        upcomingOrders.add(order);
                    }
                }
                previousOrdersRecyclerAdapter.notifyDataSetChanged();
                upcomingOrdersRecyclerAdapter.notifyDataSetChanged();
                if (loadingDialogFragment != null)
                    loadingDialogFragment.dismiss();
            }
        });
    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_orders);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_orders;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_ORDER && resultCode == RESULT_OK) {
            loadOrders();
        }
    }

    public void deleteOrder(int orderId) {
        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.show(getSupportFragmentManager(), null);
        orderDeleteResponse = orderViewModel.deleteOrder(userViewModel.getToken(), orderId);
        orderDeleteResponse.observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse response) {
                switch (response) {
                    case SUCCESS:
                        refreshOrders(loadingDialogFragment);
                        break;
                    case ERROR:
                    case FAILED:
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment("خطأ", "لم يتم حذف اللطلب", false);
                        errorDialogFragment.show(getSupportFragmentManager(), null);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
