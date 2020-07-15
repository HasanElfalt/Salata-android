package com.ar.salata.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.AuthenticationUser;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.fragments.LoadingDialogFragment;
import com.ar.salata.viewmodels.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateUserActivity extends BaseActivity {

    private TextInputLayout phoneUpdate;
    private TextInputLayout nameUpdate;
    private UserViewModel userViewModel;
    private MutableLiveData<UserRepository.APIResponse> userUpdateResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nameUpdate = findViewById(R.id.et_username_update);
        phoneUpdate = findViewById(R.id.et_phone_update);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        findViewById(R.id.btn_activity_user_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean inputsValidated = true;
                String phoneNumber = phoneUpdate.getEditText().getText().toString().trim();
                String name = nameUpdate.getEditText().getText().toString().trim();


                if (name.length() < 4) {
                    nameUpdate.setError("يجب ان يتكون الاسم من 4 احرف أو أكثر");
                    inputsValidated = false;
                } else {
                    nameUpdate.setError(null);
                }
                if (phoneNumber.length() < 8) {
                    phoneUpdate.setError("يجب ان يتكون رقم التليفون من 8 ارقام أو أكثر");
                    inputsValidated = false;
                } else {
                    phoneUpdate.setError(null);
                }

                if (inputsValidated) {
                    LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
                    loadingDialogFragment.show(getSupportFragmentManager(), null);

                    userUpdateResponse = userViewModel.updateUser(userViewModel.getToken(), name, phoneNumber);
                    userUpdateResponse.observe(UpdateUserActivity.this, new Observer<UserRepository.APIResponse>() {
                        @Override
                        public void onChanged(UserRepository.APIResponse response) {
                            switch (response) {
                                case SUCCESS: {
                                    loadingDialogFragment.dismiss();
                                    finish();
                                    break;
                                }
                                case ERROR:
                                case FAILED: {
                                    loadingDialogFragment.dismiss();
                                    ErrorDialogFragment dialogFragment =
                                            new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحديث البيانات", false);
                                    dialogFragment.show(getSupportFragmentManager(), null);
                                    break;
                                }
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_update_user);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_update_user;
    }
}