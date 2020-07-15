package com.ar.salata.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ar.salata.R;
import com.ar.salata.repositories.API.ConfigAPI;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.fragments.LoadingDialogFragment;
import com.ar.salata.ui.fragments.OrderEditConfirmationDialogFragment;
import com.ar.salata.ui.utils.ArabicString;
import com.ar.salata.viewmodels.AppConfigViewModel;
import com.ar.salata.viewmodels.OrderViewModel;
import com.ar.salata.viewmodels.UserViewModel;

import static com.ar.salata.repositories.UserRepository.APIResponse.ERROR;
import static com.ar.salata.repositories.UserRepository.APIResponse.FAILED;
import static com.ar.salata.repositories.UserRepository.APIResponse.SUCCESS;
import static com.ar.salata.viewmodels.OrderViewModel.ORDER_ID;
import static java.lang.Math.round;


public class OrderEditActivity extends BaseActivity {

    private Button button;
    private TextView totalValueTextView;
    private OrderViewModel orderViewModel;
    private UserViewModel userViewModel;
    private Order order;
    private int orderId;
    private MutableLiveData<UserRepository.APIResponse> orderUpdateResponse;
    private AppConfigViewModel appConfigViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button = findViewById(R.id.btn_edit_bill_confirm);
        totalValueTextView = findViewById(R.id.tv_total_value_order_edit);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        appConfigViewModel = new ViewModelProvider(this).get(AppConfigViewModel.class);

        orderId = getIntent().getIntExtra(ORDER_ID, 0);

        order = orderViewModel.getOrder(orderId);
        orderViewModel.setOrderValue(order);
        orderViewModel.getOrderMutableLiveData().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                totalValueTextView.setText(ArabicString.toArabic(String.valueOf(round(order.getOrderPrice() * 100) / 100.0)));
            }
        });

        totalValueTextView.setText(ArabicString.toArabic(String.valueOf(round(order.getOrderPrice() * 100) / 100.0)));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderViewModel.getOrder(orderId).equals(orderViewModel.getOrderMutableLiveData().getValue())) {
                    ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment("خطأ", "لم يتم اجراء اى تعديل على الطلب", false);
                    errorDialogFragment.show(getSupportFragmentManager(), null);
                    return;
                }
                double minimum = appConfigViewModel.getMinimumPurchases();
                if (order.getOrderPrice() < minimum) {
                    ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment("خطأ", "الحد الادنى لقيمة الفاتورة هى " + minimum + " جنيه", false);
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
        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.show(getSupportFragmentManager(), null);

        orderUpdateResponse = orderViewModel.updateOrder(userViewModel.getToken(), orderViewModel.getOrderMutableLiveData().getValue());
        orderUpdateResponse.observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse response) {
                switch (response) {
                    case SUCCESS: {
                        loadingDialogFragment.dismiss();
//                        startActivityForResult(intent, REQUESTORDER);
//                        order.setSubmitted(true);
//                        orderViewModel.updateOrder(order);
                        OrderEditActivity.this.setResult(Activity.RESULT_OK);
                        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment("تم التعديل", "تم تعديل الطلب بنجاح", true);
                        errorDialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                    case ERROR: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", "فشلت عملية تعديل الطلب", false);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                    case FAILED: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), false);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                }
            }
        });
    }
}
