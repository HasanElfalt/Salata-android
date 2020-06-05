package com.ar.salata.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ar.salata.R;

public class ErrorDialogFragment extends DialogFragment {

    private String errorTitle;
    private String errorMessage;
    private boolean finishActivity;

    public ErrorDialogFragment(String errorTitle, String errorMessage, boolean finishActivity) {
        this.errorTitle = errorTitle;
        this.errorMessage = errorMessage;
        this.finishActivity = finishActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.dialog_error, null);
		
		TextView errorTitleTextView = view.findViewById(R.id.tv_dialog_error_title);
		TextView errorMessageTextView = view.findViewById(R.id.tv_dialog_error_message);
		
		Button neutralButton = view.findViewById(R.id.btn_confirm_error_dialog);
		
		errorTitleTextView.setText(errorTitle);
		errorMessageTextView.setText(errorMessage);
		
		neutralButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                dismiss();
                if (finishActivity) getActivity().finish();
            }
		});
		
		AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view).create();
		dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
		
		return dialog;
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_error, container, false);
	}
}
