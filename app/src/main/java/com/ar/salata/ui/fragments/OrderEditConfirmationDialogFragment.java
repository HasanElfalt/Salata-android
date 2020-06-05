package com.ar.salata.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ar.salata.R;

public class OrderEditConfirmationDialogFragment extends DialogFragment {

    public static OrderEditConfirmationDialogFragment newInstance() {
        return new OrderEditConfirmationDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm, null, false);
        builder.setView(view);

        view.findViewById(R.id.btn_confirm_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent intent = new Intent(getActivity(), OrderEditActivity.class);
                startActivity(intent);
*/

                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
                dismiss();
            }
        });

        view.findViewById(R.id.btn_cancel_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        return dialog;
    }
}
