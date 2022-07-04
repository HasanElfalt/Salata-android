package com.ar.salata.ui.activities;

import static com.ar.salata.ui.fragments.PaymentMethodDialogFragment.CREDIT_CARD;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.repositories.model.PaymentMethods;
import com.ar.salata.ui.adapters.FinalBillRecyclerAdapter;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.fragments.LoadingDialogFragment;
import com.ar.salata.ui.fragments.PaymentMethodDialogFragment;
import com.ar.salata.viewmodels.AppConfigViewModel;
import com.ar.salata.viewmodels.OrderViewModel;
import com.ar.salata.viewmodels.UserViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import team.opay.business.cashier.sdk.api.PaymentStatus;
import team.opay.business.cashier.sdk.api.WebJsResponse;
import team.opay.business.cashier.sdk.pay.PaymentTask;

public class PayActivity extends BaseActivity {
    private static final int REQUESTORDER = 2;
    private ExtendedFloatingActionButton efab;
    private OrderViewModel orderViewModel;
    private UserViewModel userViewModel;
    private AppConfigViewModel appConfigViewModel;
    private MutableLiveData<UserRepository.APIResponse> submitOrderResponse;
    private TextInputLayout txInputNotes;
    Order order;
    APIToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        txInputNotes   = findViewById(R.id.et_notes_pay);
        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        setSupportActionBar(toolbar);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        appConfigViewModel = new ViewModelProvider(this).get(AppConfigViewModel.class);

        order = getIntent().getParcelableExtra(OrderViewModel.ORDER);
        // set notes text
        order.setNotes(txInputNotes.getEditText().getText().toString());

//        Order order = orderViewModel.getOrder(orderLocalId);
        token = userViewModel.getToken();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        RecyclerView recyclerView = findViewById(R.id.rv_bill_final);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FinalBillRecyclerAdapter adapter = new FinalBillRecyclerAdapter(getApplicationContext(), order, appConfigViewModel.getPhones());
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        efab = findViewById(R.id.efab_confirm);
        efab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double minimum = appConfigViewModel.getMinimumPurchases();
                if (order.getOrderPrice() < minimum) {
                    ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment("خطأ", "الحد الادنى لقيمة الفاتورة هى " + minimum + " جنيه", false);
                    errorDialogFragment.show(getSupportFragmentManager(), null);
                    return;
                }
                // Loading LoadingDialog
                LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
                loadingDialogFragment.show(getSupportFragmentManager(), null);

                // Getting Payment Methods
                orderViewModel.getPaymentMethods().observe(PayActivity.this, new Observer<List<PaymentMethods>>() {
                    @Override
                    public void onChanged(List<PaymentMethods> paymentMethods) {
                        loadingDialogFragment.dismiss();
                        PaymentMethodDialogFragment paymentMethodDialogFragment = new PaymentMethodDialogFragment(paymentMethods,token, order);
                        paymentMethodDialogFragment.show(getSupportFragmentManager(),null);
                    }
                });


                //Intent intent = new Intent(PayActivity.this, OrdersActivity.class);
                //////////////////////////add notes////////////////////////////////
                /*
                submitOrderResponse = orderViewModel.submitOrder(token, order);
                submitOrderResponse.observe(PayActivity.this, new Observer<UserRepository.APIResponse>() {
                    @Override
                    public void onChanged(UserRepository.APIResponse apiResponse) {
                        switch (apiResponse) {
                            case SUCCESS: {
                                loadingDialogFragment.dismiss();
                  //              startActivityForResult(intent, REQUESTORDER);
//                                order.setSubmitted(true);
//                                orderViewModel.updateOrder(order);
                                break;
                            }
                            case ERROR: {
                                loadingDialogFragment.dismiss();
                                ErrorDialogFragment dialogFragment =
                                        new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم", false);
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
                });*/
            }
        });

        NestedScrollView payScrollView = findViewById(R.id.nsv_pay);

        payScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    efab.hide();
                } else if (oldScrollY > scrollY) {
                    efab.show();
                }
            }
        });

    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_pay);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_pay;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTORDER && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }else if (requestCode == PaymentTask.REQUEST_PAYMENT) {
//                Log.e("onActivityResult", "onActivityResult2");
                if (resultCode == PaymentTask.RESULT_PAYMENT) {
  //                  Log.e("onActivityResult", "onActivityResult3");
                    WebJsResponse response = (WebJsResponse) data.getExtras().getSerializable(PaymentTask.RESPONSE_DATA);
                    switch (response.getOrderStatus()) {
                        case PaymentStatus.INITIAL: {
                            Log.e("onActivityResult", response.getOrderStatus());
                            break;
                        }
                        case PaymentStatus.SUCCESS: {
                            Log.e("onActivityResult", response.getOrderStatus());
                            order.setPaymentType(CREDIT_CARD);
                            Toast.makeText(this, "تم الدفع بنجاح",Toast.LENGTH_SHORT).show();

                            submitOrderResponse = orderViewModel.submitOrder(token, order);
                            submitOrderResponse.observe(this, new Observer<UserRepository.APIResponse>() {
                                @Override
                                public void onChanged(UserRepository.APIResponse apiResponse) {
                                    switch (apiResponse) {
                                        case SUCCESS: {
                                            Intent intent = new Intent(PayActivity.this, OrdersActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        }
                                        case ERROR: {
                                            ErrorDialogFragment dialogFragment =
                                                    new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم", false);
                                            dialogFragment.show(getSupportFragmentManager(), null);
                                            break;
                                        }
                                        case FAILED: {
                                            ErrorDialogFragment dialogFragment =
                                                    new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), false);
                                            dialogFragment.show(getSupportFragmentManager(), null);
                                            break;
                                        }
                                    }
                                }
                            });
                            break;
                        }
                        case PaymentStatus.FAIL: {
                            Log.e("onActivityResult", response.getOrderStatus());
                            ErrorDialogFragment dialogFragment =
                                    new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), false);
                            dialogFragment.show(getSupportFragmentManager(), null);

                            break;
                        }
                        case PaymentStatus.PENDING: {
                            break;
                        }
                    }
                }

        }

    }
}
