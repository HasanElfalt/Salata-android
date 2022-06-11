package com.ar.salata.ui.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.repositories.model.PaymentMethods;
import com.ar.salata.ui.activities.OrdersActivity;
import com.ar.salata.ui.activities.PayActivity;
import com.ar.salata.viewmodels.OrderViewModel;

import java.util.List;

public class PaymentMethodDialogFragment extends DialogFragment {

    public static final int PAYMENT_METHOD = 10;
    private OrderViewModel orderViewModel;
    private Order order;
    private APIToken token;
    private List<PaymentMethods> paymentMethods;
    private Button confirmButton, cancelButton;
    private MutableLiveData<UserRepository.APIResponse> submitOrderResponse;


    public PaymentMethodDialogFragment(List<PaymentMethods> paymentMethods, APIToken token, Order order){
        this.paymentMethods = paymentMethods;
        this.token = token;
        this.order = order;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_pay_method, null);
        builder.setView(view);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        RadioGroup paymentRadioGroup = (RadioGroup) view.findViewById(R.id.dialog_payment_options);

        confirmButton = view.findViewById(R.id.btn_payment_confirm_dialog);
        cancelButton  = view.findViewById(R.id.btn_payment_cancel_dialog);

        // Show Payment Methods Radio Buttons
        for(PaymentMethods payment: paymentMethods){
            // this id =4  for Apple Pay and it is a method for IOS users only
            if(payment.getId()==4){
                continue;
            }
            RadioButton paymentRadioButton = new RadioButton(getContext(), null, R.attr.RadioButtonStyle, R.style.RadioButtonStyle);
            paymentRadioButton.setText(payment.getName());
            paymentRadioButton.setId(payment.getId());
            paymentRadioGroup.addView(paymentRadioButton);
        }
        ((RadioButton) paymentRadioGroup.getChildAt(0)).setChecked(true);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
                loadingDialogFragment.show(getActivity().getSupportFragmentManager(), null);

                int tempId = paymentRadioGroup.getCheckedRadioButtonId();
                Log.e("PaymentMethod", String.valueOf(tempId));
                // Id = 1   for cash_on_delivery
                if(tempId == 1){
                    order.setPaymentType("cash_on_delivery");
                    submitOrderResponse = orderViewModel.submitOrder(token, order);
                    submitOrderResponse.observe(getViewLifecycleOwner(), new Observer<UserRepository.APIResponse>() {
                        @Override
                        public void onChanged(UserRepository.APIResponse apiResponse) {
                            switch (apiResponse) {
                                case SUCCESS: {
                                    loadingDialogFragment.dismiss();
                                    Intent intent = new Intent(getContext(), OrdersActivity.class);
                                    startActivity(intent);
                                    dismiss();
                                    getActivity().finish();
//                                order.setSubmitted(true);
//                                orderViewModel.updateOrder(order);
                                    break;
                                }
                                case ERROR: {
                                    loadingDialogFragment.dismiss();
                                    ErrorDialogFragment dialogFragment =
                                            new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم", false);
                                    dialogFragment.show(getActivity().getSupportFragmentManager(), null);
                                    break;
                                }
                                case FAILED: {
                                    loadingDialogFragment.dismiss();
                                    ErrorDialogFragment dialogFragment =
                                            new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), false);
                                    dialogFragment.show(getActivity().getSupportFragmentManager(), null);
                                    break;
                                }
                            }
                        }
                    });
                // Id = 2   for credit_card
                }else if(tempId == 2){
                    order.setPaymentType("credit_card");
                    loadingDialogFragment.dismiss();

                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_pay_method, container, false);

    }
}
