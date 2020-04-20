package com.ar.salata.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ar.salata.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseAddressDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseAddressDialogFragment extends DialogFragment {




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

        Dialog dialog = builder.create();
        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_address_dialog, container, false);
    }
}
