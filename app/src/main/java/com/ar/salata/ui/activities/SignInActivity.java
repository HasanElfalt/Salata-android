package com.ar.salata.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.AuthenticationUser;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.viewmodels.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends BaseActivity {
	
	private TextInputLayout phoneSignIn;
	private TextInputLayout passwordSignIn;
	private UserViewModel userViewModel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
		
		phoneSignIn = findViewById(R.id.et_phone_sign_in);
		passwordSignIn = findViewById(R.id.et_password_sign_in);
		
		findViewById(R.id.btn_activity_sign_in).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean inputsValidated = true;
				
				String phoneNumber = phoneSignIn.getEditText().getText().toString().trim();
				String password = passwordSignIn.getEditText().getText().toString().trim();
				
				if (phoneNumber.length() < 8) {
					phoneSignIn.setError("يجب ان يتكون رقم التليفون من 8 ارقام أو أكثر");
					inputsValidated = false;
				} else {
					phoneSignIn.setError(null);
				}
				if (password.length() < 6) {
					passwordSignIn.setError("يجب ان يتكون الرقم السري من 6 احرف أو أكثر");
					inputsValidated = false;
				} else {
					passwordSignIn.setError(null);
				}
				
				if (inputsValidated) {
					AuthenticationUser user = new AuthenticationUser(phoneNumber, password);
					
					userViewModel.loginUser(user);
					userViewModel.getAuthenticationStateObservable().observe(SignInActivity.this, new Observer<UserRepository.Authentication>() {
						@Override
						public void onChanged(UserRepository.Authentication authentication) {
							switch (authentication) {
								case AUTHENTICATED:
									finish();
									break;
								case AUTHENTICATION_ERROR: {
									ErrorDialogFragment dialogFragment =
											new ErrorDialogFragment("حدث خطأ", "فشلت عملية تسجيل الدخول");
									dialogFragment.show(getSupportFragmentManager(), null);
									break;
								}
								case AUTHENTICATION_FAILED: {
									ErrorDialogFragment dialogFragment =
											new ErrorDialogFragment("حدث خطأ", "يوجد خطا فى الاتصال بالانترنت");
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
		return findViewById(R.id.toolbar_sign_in);
	}
	
	@Override
	int deliverLayout() {
		return R.layout.activity_sign_in;
	}
	
	
}
