package com.ar.salata.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.ar.salata.R;


public class OrderEditActivity extends BaseActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button = findViewById(R.id.btn_edit_bill_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(OrderEditActivity.this)
                        .setView(R.layout.dialog_confirm)
                        .create();
                dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                dialog.show();
            }
        });
    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_order_edit);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_order_edit;
    }
}
