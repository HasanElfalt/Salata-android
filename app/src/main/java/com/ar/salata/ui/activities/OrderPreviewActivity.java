package com.ar.salata.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.ui.adapters.FinalBillRecyclerAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class OrderPreviewActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton orderEditEFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_preview);

        Toolbar toolbar = findViewById(R.id.toolbar_Order_preview);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        orderEditEFAB = findViewById(R.id.efab_edit_order_preview);

        RecyclerView recyclerView = findViewById(R.id.rv_order_preview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FinalBillRecyclerAdapter adapter = new FinalBillRecyclerAdapter(getApplicationContext());
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        orderEditEFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderPreviewActivity.this, OrderEditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
