package com.ar.salata.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ar.salata.R;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.fragments.OrderEditConfirmationDialogFragment;
import com.ar.salata.ui.utils.ArabicString;
import com.ar.salata.viewmodels.OrderViewModel;

import static com.ar.salata.viewmodels.OrderViewModel.ORDER_ID;


public class OrderEditActivity extends BaseActivity {

    private Button button;
    private TextView totalValueTextView;
    private OrderViewModel orderViewModel;
    private Order order;
    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button = findViewById(R.id.btn_edit_bill_confirm);
        totalValueTextView = findViewById(R.id.tv_total_value_order_edit);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        orderId = getIntent().getIntExtra(ORDER_ID, 0);

        order = orderViewModel.getOrder(orderId);
        orderViewModel.setOrderValue(order);
        orderViewModel.getOrderMutableLiveData().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                totalValueTextView.setText(ArabicString.toArabic(String.valueOf(order.getOrderPrice())));
            }
        });

        totalValueTextView.setText(ArabicString.toArabic(String.valueOf(order.getOrderPrice())));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderViewModel.getOrder(orderId).equals(orderViewModel.getOrderMutableLiveData().getValue())) {
                    ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment("خطأ", "لم يتم اجراء اى تعديل على الطلب", false);
                    errorDialogFragment.show(getSupportFragmentManager(), null);
                    return;
                }
                DialogFragment dialog = OrderEditConfirmationDialogFragment.newInstance("تعديل الطلب", "هل بالفعل تريد تعديل الطلب رقم " + orderId, null);
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

    public OrderViewModel getOrderViewModel() {
        return orderViewModel;
    }

    public void editOrder() {
//        orderViewModel.edit
        setResult(Activity.RESULT_OK);
        finish();
    }
}
