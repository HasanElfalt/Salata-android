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

import com.ar.salata.R;
import com.ar.salata.repositories.model.DeliveryDate;
import com.ar.salata.repositories.model.Shift;
import com.ar.salata.repositories.model.UserAddress;
import com.ar.salata.ui.utils.ArabicString;

import java.util.List;

public class ChooseAddressDialogFragment extends DialogFragment {

    public static final String ADDRESS_ID = "AddressId";
    public static final String SHIFT_ID = "ShiftId";
    public static final String DELIVERY_DATE = "DeliveryDate";
    private List<UserAddress> addresses;
    private Button confirmButton;
    private Button cancelBotton;
    private String deliveryDate;
    private int addressId;
    private int shiftId;

    public ChooseAddressDialogFragment(List<UserAddress> addresses) {
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_choose_address_dialog, null);
        builder.setView(view);

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

                    daysGroup.removeAllViews();
                    for (DeliveryDate day : address.getDates()) {
                        RadioButton radioButton = new RadioButton(getContext(), null, R.attr.RadioButtonStyle, R.style.RectangleRadioBtn);
                        radioButton.setText(ArabicString.toArabic(day.getDay()));

                        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (!isChecked) return;
                                deliveryDate = buttonView.getText().toString();

                                timeGroup.removeAllViews();
                                for (Shift shift : day.getShifts()) {
                                    RadioButton radioButton = new RadioButton(getContext(), null, R.attr.RadioButtonStyle, R.style.RectangleRadioBtn);
                                    radioButton.setText(ArabicString.toArabic(shift.getFrom() + " ~ " + shift.getTo()));

                                    radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (!isChecked) return;

                                            shiftId = shift.getId();
                                        }
                                    });
                                    timeGroup.addView(radioButton);
                                }
                                ((RadioButton) timeGroup.getChildAt(0)).setChecked(true);
                            }
                        });
                        daysGroup.addView(radioButton);
                    }
                    ((RadioButton) daysGroup.getChildAt(0)).setChecked(true);
                }
            });
            addressGroup.addView(radioButton);
        }

        ((RadioButton) addressGroup.getChildAt(0)).setChecked(true);
        ((RadioButton) daysGroup.getChildAt(0)).setChecked(true);

        confirmButton = view.findViewById(R.id.btn_confirm_address_dialog);
        cancelBotton = view.findViewById(R.id.btn_cancel_address_dialog);

        confirmButton.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra(ADDRESS_ID, addressId);
                        intent.putExtra(SHIFT_ID, shiftId);
                        intent.putExtra(DELIVERY_DATE, deliveryDate);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        dismiss();
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
