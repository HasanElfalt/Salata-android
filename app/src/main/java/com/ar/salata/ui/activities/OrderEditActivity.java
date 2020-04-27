package com.ar.salata.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.ar.salata.R;
import com.ar.salata.ui.fragments.OrderEditConfirmationDialogFragment;


public class OrderEditActivity extends BaseActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button = findViewById(R.id.btn_edit_bill_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = OrderEditConfirmationDialogFragment.newInstance();
                dialog.show(getSupportFragmentManager(), null);
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
