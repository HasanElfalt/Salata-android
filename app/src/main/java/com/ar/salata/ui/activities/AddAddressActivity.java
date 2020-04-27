package com.ar.salata.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.ar.salata.R;

public class AddAddressActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_add_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_add_address);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_add_address;
    }
}
