package com.ar.salata.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ar.salata.R;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.utils.ArabicString;
import com.ar.salata.viewmodels.OrderViewModel;
import com.ar.salata.viewmodels.UserViewModel;

import static com.ar.salata.ui.fragments.ChooseAddressDialogFragment.ADDRESS_ID;
import static com.ar.salata.ui.fragments.ChooseAddressDialogFragment.DELIVERY_DATE;
import static com.ar.salata.ui.fragments.ChooseAddressDialogFragment.DELIVERY_DATE_MS;
import static com.ar.salata.ui.fragments.ChooseAddressDialogFragment.DELIVERY_HOUR;
import static com.ar.salata.ui.fragments.ChooseAddressDialogFragment.MIN_PURCHASE;
import static com.ar.salata.ui.fragments.ChooseAddressDialogFragment.SHIFT_ID;
import static com.ar.salata.ui.fragments.HomeFragment.USER_ID;
import static java.lang.Math.round;

public class AddToCartActivity extends BaseActivity {
    private static final String TAG = "AddToCartActivity";
    private static final int REQUESTPAY = 2;
    private Button button;
    private TextView totalValueTextView;
    private UserViewModel userViewModel;
    private Order order;
    private OrderViewModel orderViewModel;
    private String minPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        button = findViewById(R.id.btn_bill_confirm);
        totalValueTextView = findViewById(R.id.tv_total_value_add_to_card);

        Intent intent = getIntent();

        order = new Order(intent.getIntExtra(SHIFT_ID, 1),
                intent.getIntExtra(ADDRESS_ID, 1),
                intent.getIntExtra(USER_ID, 0),
                intent.getLongExtra(DELIVERY_DATE_MS, 0) / 1000,
                intent.getStringExtra(DELIVERY_DATE),
                intent.getStringExtra(DELIVERY_HOUR));

        minPurchase = intent.getStringExtra(MIN_PURCHASE);

        orderViewModel.setOrderValue(order);
        orderViewModel.getOrderMutableLiveData().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                totalValueTextView.setText(ArabicString.toArabic(String.valueOf(round(order.getOrderPrice() * 100) / 100.0)));
            }
        });

        totalValueTextView.setText(ArabicString.toArabic(String.valueOf(order.getOrderPrice())));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.getOrderPrice() >= Integer.parseInt(minPurchase)) {
                    Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                    Order order = orderViewModel.getOrderMutableLiveData().getValue();
                /*intent.putExtra(SHIFT_ID, order.getShiftId());
                intent.putExtra(ADDRESS_ID, order.getAddressId());
                intent.putExtra(USER_ID, order.getUserId());
                intent.putExtra(DELIVERY_DATE, order.getDeliveryDate());
                intent.putExtra(SHIFT_ID, order.getShiftId());
                String id = orderViewModel.createOrder(order);*/

                    intent.putExtra(OrderViewModel.ORDER, order);
                    startActivityForResult(intent, REQUESTPAY);
                }else{
                    ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment("حدث خطأ", "يجب أن تكون قيمة الشراء لا تقل عن " + minPurchase+ " جنيه",false );
                    errorDialogFragment.show(getSupportFragmentManager(),null);
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTPAY && resultCode == RESULT_OK) finish();
    }

    public OrderViewModel getOrderViewModel() {
        return orderViewModel;
    }
}
