package com.ar.salata.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ar.salata.R;
import com.ar.salata.ui.activities.OrderEditActivity;
import com.ar.salata.ui.activities.OrdersActivity;
import com.ar.salata.ui.activities.UserProfileActivity;

public class OrderEditConfirmationDialogFragment extends DialogFragment {

    private TextView confirmTitleTextView;
    private TextView confirmMessageTextView;
    private String confirmTitle;
    private String confirmMessage;
    private Integer id;

    public OrderEditConfirmationDialogFragment(String confirmTitle, String confirmMessage, @Nullable Integer id) {
        this.confirmMessage = confirmMessage;
        this.confirmTitle = confirmTitle;
        this.id = id;
    }

    public static OrderEditConfirmationDialogFragment newInstance(String confirmTitle, String confirmMessage, @Nullable Integer orderId) {
        return new OrderEditConfirmationDialogFragment(confirmTitle, confirmMessage, orderId);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm, null, false);
        builder.setView(view);

        confirmTitleTextView = view.findViewById(R.id.tv_dialog_title);
        confirmMessageTextView = view.findViewById(R.id.tv_dialog_message);

        confirmTitleTextView.setText(confirmTitle);
        confirmMessageTextView.setText(confirmMessage);

        view.findViewById(R.id.btn_confirm_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof OrdersActivity) {
                    ((OrdersActivity) getActivity()).deleteOrder(id.intValue());
                } else if (getActivity() instanceof OrderEditActivity) {
                    ((OrderEditActivity) getActivity()).editOrder();
                } else if (getActivity() instanceof UserProfileActivity) {
                    ((UserProfileActivity) getActivity()).deleteAddress(id.intValue());
                }
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
