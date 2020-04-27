package com.ar.salata.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import com.ar.salata.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseAddressDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseAddressDialogFragment extends DialogFragment {

    String[] dayList = {"الاحد", "الاثنين", "الثلاثاء", "الخميس", "الجمعة", "السبت", "الاربعاء"};
    private Button confirmButton;

    String[] addressList = {"العنوان الثاني", "العنوان الثالث", "العنوان الاول"};
    private Button cancelBotton;
    String[] timeList = {"01:00 ظهرا", "03:00 عصرا", "05:00 عصرا"};

    public ChooseAddressDialogFragment() {
    }

    public static ChooseAddressDialogFragment newInstance() {
        ChooseAddressDialogFragment fragment = new ChooseAddressDialogFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_choose_address_dialog, null);
        builder.setView(view);
        RadioGroup addressGroup = (RadioGroup) view.findViewById(R.id.address_chooser_group);
        for (String address : addressList) {
            RadioButton radioButton = new RadioButton(getContext(), null, R.attr.RadioButtonStyle, R.style.RadioButtonStyle);
            radioButton.setText(address);

            addressGroup.addView(radioButton);
        }
        RadioGroup daysGroup = (RadioGroup) view.findViewById(R.id.day_chooser_group);
        for (String day : dayList) {
            RadioButton radioButton = new RadioButton(getContext(), null, R.attr.RadioButtonStyle, R.style.RectangleRadioBtn);
            radioButton.setText(day);
            daysGroup.addView(radioButton);
        }
        RadioGroup timeGroup = (RadioGroup) view.findViewById(R.id.time_chooser_group);
        for (String time : timeList) {
            RadioButton radioButton = new RadioButton(getContext(), null, R.attr.RadioButtonStyle, R.style.RectangleRadioBtn);
            radioButton.setText(time);
            timeGroup.addView(radioButton);
        }

        confirmButton = view.findViewById(R.id.btn_confirm_address_dialog);
        cancelBotton = view.findViewById(R.id.btn_cancel_address_dialog);

        confirmButton.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
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
