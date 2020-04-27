package com.ar.salata.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.ar.salata.R;
import com.ar.salata.model.Order;
import com.ar.salata.ui.adapters.OrdersRecyclerAdapter;

import java.util.ArrayList;

public class OrdersActivity extends BaseActivity {
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
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        for (int i = 0; i < 2; i++) {
            upcomingOrders.add(new Order("1",
                    "22/4/2020",
                    "01:00",
                    500.0,
                    R.drawable.ic_launcher_background)
            );
            previousOrders.add(new Order("1",
                    "22/4/2020",
                    "01:00",
                    500.0,
                    R.drawable.ic_launcher_background)
            );
        }
        upcomingOrdersRecyclerView.setAdapter(new OrdersRecyclerAdapter(this,
                upcomingOrders,
                true)
        );
        previousOrdersRecyclerView.setAdapter(new OrdersRecyclerAdapter(this
                , upcomingOrders,
                false)
        );

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

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_orders);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_orders;
    }

}
