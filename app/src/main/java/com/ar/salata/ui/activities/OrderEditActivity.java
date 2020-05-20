package com.ar.salata.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.ar.salata.R;
import com.ar.salata.ui.fragments.OrderEditConfirmationDialogFragment;
import com.ar.salata.ui.utils.ArabicString;


public class OrderEditActivity extends BaseActivity {

    private Button button;
    private TextView totalValueTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button = findViewById(R.id.btn_edit_bill_confirm);
        totalValueTextView = findViewById(R.id.tv_total_value_order_edit);
    
        totalValueTextView.setText(ArabicString.toArabic("12.50 جنيه"));
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
