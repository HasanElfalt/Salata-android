package com.ar.salata.ui.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.ar.salata.R;
import com.ar.salata.repositories.API.UserAPI;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.fragments.LoadingDialogFragment;
import com.ar.salata.viewmodels.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class UpdatePasswordActivity extends BaseActivity {

    private TextInputLayout currentPasswordUpdate;
    private TextInputLayout passwordUpdate;
    private TextInputLayout passwordConfirmUpdate;
    private UserViewModel userViewModel;
    private MutableLiveData<UserRepository.APIResponse> updatePasswordResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentPasswordUpdate = findViewById(R.id.et_password_current_update);
        passwordUpdate = findViewById(R.id.et_password_update);
        passwordConfirmUpdate = findViewById(R.id.et_password_confirm_update);

        findViewById(R.id.btn_activity_password_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentPassword = currentPasswordUpdate.getEditText().getText().toString().trim();
                String password = passwordUpdate.getEditText().getText().toString().trim();
                String passwordConfirmation = passwordConfirmUpdate.getEditText().getText().toString().trim();

                userViewModel = new ViewModelProvider(UpdatePasswordActivity.this).get(UserViewModel.class);

                boolean inputsValidated = true;

                if (currentPassword.length() < 6) {
                    currentPasswordUpdate.setError("يجب ان يتكون الرقم السري من 6 احرف أو أكثر");
                    inputsValidated = false;
                } else {
                    currentPasswordUpdate.setError(null);
                }
                if (password.length() < 6) {
                    passwordUpdate.setError("يجب ان يتكون الرقم السري من 6 احرف أو أكثر");
                    inputsValidated = false;
                } else {
                    passwordUpdate.setError(null);
                }
                if (!passwordConfirmation.equals(password)) {
                    passwordConfirmUpdate.setError("الرقم السرى ليس متطابقا");
                    inputsValidated = false;
                } else {
                    passwordConfirmUpdate.setError(null);
                }

                if (inputsValidated) {
                    LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
                    loadingDialogFragment.show(getSupportFragmentManager(), null);

                    updatePasswordResponse = userViewModel.updatePassword(userViewModel.getToken(), currentPassword, password, passwordConfirmation);
                    updatePasswordResponse.observe(UpdatePasswordActivity.this, new Observer<UserRepository.APIResponse>() {
                        @Override
                        public void onChanged(UserRepository.APIResponse response) {
                            switch (response) {
                                case SUCCESS: {
                                    loadingDialogFragment.dismiss();
                                    ErrorDialogFragment dialogFragment =
                                            new ErrorDialogFragment("تم التعديل", "تم تغيير كلمة المرور بنجاح\nمن فضلك قم بتسجيل الدخول بكلمة المرور الجديدة", true);
                                    dialogFragment.show(getSupportFragmentManager(), null);
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
        return findViewById(R.id.toolbar_update_password);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_update_password;
    }
}
