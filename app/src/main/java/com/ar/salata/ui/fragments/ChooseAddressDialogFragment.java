package com.ar.salata.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.ar.salata.repositories.model.DeliveryDate;
import com.ar.salata.repositories.model.Shift;
import com.ar.salata.repositories.model.UserAddress;
import com.ar.salata.ui.utils.ArabicString;
import com.ar.salata.ui.utils.TimeFormats;
import com.ar.salata.viewmodels.GoodsViewModel;
import com.ar.salata.viewmodels.UserViewModel;

import java.util.List;

public class ChooseAddressDialogFragment extends DialogFragment {

    public static final String ADDRESS_ID = "AddressId";
    public static final String SHIFT_ID = "ShiftId";
    public static final String DELIVERY_DATE = "DeliveryDate";
    public static final String DELIVERY_DATE_MS = "DeliveryDateMS";
    public static final String DELIVERY_HOUR = "DeliveryHour";
    public static final String MIN_PURCHASE = "minPurchase";
    private List<UserAddress> addresses;
    private Button confirmButton;
    private Button cancelBotton;
    private String deliveryDate;
    private int addressId;
    private int shiftId;
    private GoodsViewModel goodsViewModel;
    private UserViewModel userViewModel;
    private APIToken token;
    private MutableLiveData<UserRepository.APIResponse> productsApiResponse;
    private long deliveryDateMS;
    private String shiftString;
    private String minPurchase;
    private boolean selectedAddressHasDates = false;

    public ChooseAddressDialogFragment(List<UserAddress> addresses, APIToken token) {
        this.addresses = addresses;
        this.token = token;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_choose_address_dialog, null);
        builder.setView(view);

        goodsViewModel = new ViewModelProvider(this).get(GoodsViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        RadioGroup addressGroup = (RadioGroup) view.findViewById(R.id.address_chooser_group);
        RadioGroup daysGroup = (RadioGroup) view.findViewById(R.id.day_chooser_group);
        RadioGroup timeGroup = (RadioGroup) view.findViewById(R.id.time_chooser_group);

        for (UserAddress address : addresses) {
            RadioButton radioButton = new RadioButton(getContext(), null, R.attr.RadioButtonStyle, R.style.RadioButtonStyle);
            radioButton.setText(ArabicString.toArabic(address.getZone() + " / " + address.getAddress()));

            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) return;
                    addressId = address.getAddressId();
                    minPurchase= address.getMinPurchases();

                    daysGroup.removeAllViews();
                    timeGroup.removeAllViews();

                    for (DeliveryDate day : address.getDates()) {
                        selectedAddressHasDates = true;
                        RadioButton radioButton = new RadioButton(getContext(), null, R.attr.RadioButtonStyle, R.style.RectangleRadioBtn);
                        radioButton.setText(TimeFormats.convertToArabicString("E، d MMM yyyy", day.getDate()));

                        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (!isChecked) return;
                                deliveryDate = TimeFormats.convertToAPIFormat("E، d MMM yyyy", buttonView.getText().toString());
                                deliveryDateMS = TimeFormats.convertToLong("E، d MMM yyyy", buttonView.getText().toString());
                                timeGroup.removeAllViews();
                                for (Shift shift : day.getShifts()) {
                                    RadioButton radioButton = new RadioButton(getContext(), null, R.attr.RadioButtonStyle, R.style.RectangleRadioBtn);
                                    radioButton.setText(ArabicString.toArabic(shift.getFrom() + " ~ " + shift.getTo()));

                                    radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (!isChecked) return;

                                            shiftId = shift.getId();
                                            shiftString = ArabicString.toArabic(shift.getFrom());
                                        }
                                    });
                                    timeGroup.addView(radioButton);
                                }
                                ((RadioButton) timeGroup.getChildAt(0)).setChecked(true);
                            }
                        });
                        daysGroup.addView(radioButton);
                    }

                    if(daysGroup.getChildAt(0) != null)
                        ((RadioButton) daysGroup.getChildAt(0)).setChecked(true);

                }
            });
            addressGroup.addView(radioButton);
        }

        ((RadioButton) addressGroup.getChildAt(0)).setChecked(true);
        if(daysGroup.getChildAt(0) != null)
            ((RadioButton) daysGroup.getChildAt(0)).setChecked(true);

        confirmButton = view.findViewById(R.id.btn_confirm_address_dialog);
        cancelBotton = view.findViewById(R.id.btn_cancel_address_dialog);

        confirmButton.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedAddressHasDates) {
                            productsApiResponse = goodsViewModel.loadProducts(token, addressId);

                            LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
                            loadingDialogFragment.show(getActivity().getSupportFragmentManager(), null);


                            productsApiResponse.observe(ChooseAddressDialogFragment.this, new Observer<UserRepository.APIResponse>() {
                                @Override
                                public void onChanged(UserRepository.APIResponse apiResponse) {
                                    switch (apiResponse) {
                                        case SUCCESS:
                                            loadingDialogFragment.dismiss();
                                            Intent intent = new Intent();
                                            intent.putExtra(ADDRESS_ID, addressId);
                                            intent.putExtra(SHIFT_ID, shiftId);
                                            intent.putExtra(DELIVERY_DATE, deliveryDate);
                                            intent.putExtra(DELIVERY_DATE_MS, deliveryDateMS);
                                            intent.putExtra(DELIVERY_HOUR, shiftString);
                                            intent.putExtra(MIN_PURCHASE, minPurchase);
                                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                                            dismiss();
                                            break;
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

/*
                        Intent intent = new Intent();
                        intent.putExtra(ADDRESS_ID, addressId);
                        intent.putExtra(SHIFT_ID, shiftId);
                        intent.putExtra(DELIVERY_DATE, deliveryDate);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        dismiss();
*/
                        }else{
                            ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment("تنبيه", "سلطة غير متاحة حاليا فى هذه المنطقة.. ستكون متاحة قريبا", false);
                            errorDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                        }
                    }
                });
        cancelBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_address_dialog, container, false);

    }
}
