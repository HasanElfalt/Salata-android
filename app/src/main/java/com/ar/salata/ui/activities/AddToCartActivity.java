package com.ar.salata.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.ar.salata.R;

public class AddToCartActivity extends BaseActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button = findViewById(R.id.btn_bill_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_add_to_cart);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_add_to_cart;
    }

}
