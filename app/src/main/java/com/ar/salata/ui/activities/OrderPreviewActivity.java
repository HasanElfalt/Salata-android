package com.ar.salata.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.ui.adapters.FinalBillRecyclerAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class OrderPreviewActivity extends BaseActivity {

    private ExtendedFloatingActionButton orderEditEFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderEditEFAB = findViewById(R.id.efab_edit_order_preview);
        orderEditEFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderPreviewActivity.this, OrderEditActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_order_preview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FinalBillRecyclerAdapter adapter = new FinalBillRecyclerAdapter(getApplicationContext());
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_Order_preview);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_order_preview;
    }
}
