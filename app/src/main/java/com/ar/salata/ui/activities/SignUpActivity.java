package com.ar.salata.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.AuthenticationUser;
import com.ar.salata.repositories.model.City;
import com.ar.salata.repositories.model.Town;
import com.ar.salata.repositories.model.Zone;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.viewmodels.AddressViewModel;
import com.ar.salata.viewmodels.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SignUpActivity extends BaseActivity {
	
	private TextInputLayout nameSignUp;
	private TextInputLayout phoneSignUp;
	private TextInputLayout passwordSignUp;
	private TextInputLayout passwordConfirmationSignUp;
	private TextInputLayout citySignUp;
	private AutoCompleteTextView cityAutoSignUp;
	private TextInputLayout townSignUp;
	private AutoCompleteTextView townAutoSignUp;
	private TextInputLayout zoneSignUp;
	private AutoCompleteTextView zoneAutoSignUp;
	private TextInputLayout addressDetailsSignUp;
	private int zoneId = -1;
	private UserViewModel userViewModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
		
		nameSignUp = findViewById(R.id.et_name_sign_up);
		phoneSignUp = findViewById(R.id.et_phone_sign_up);
		passwordSignUp = findViewById(R.id.et_password_sign_up);
		passwordConfirmationSignUp = findViewById(R.id.et_password_confirm_sign_up);
		citySignUp = findViewById(R.id.et_city_sign_up);
		cityAutoSignUp = findViewById(R.id.actv_city_sign_up);
		townSignUp = findViewById(R.id.et_town_sign_up);
		townAutoSignUp = findViewById(R.id.actv_town_sign_up);
		zoneSignUp = findViewById(R.id.et_zone_sign_up);
		zoneAutoSignUp = findViewById(R.id.actv_zone_sign_up);
		addressDetailsSignUp = findViewById(R.id.et_address_details_sign_up);
		
		AddressViewModel addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
		
		
		List<City> cities = addressViewModel.getCities();
		
		ArrayAdapter<City> citiesAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_dropdown_item,
				cities);
		
		
		cityAutoSignUp.setAdapter(citiesAdapter);
		
		cityAutoSignUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int cityId = ((ArrayAdapter<City>) parent.getAdapter()).getItem(position).getId();
				List<Town> towns = addressViewModel.getTowns(cityId);
				ArrayAdapter<Town> townsAdapter = new ArrayAdapter<>(SignUpActivity.this,
						android.R.layout.simple_spinner_dropdown_item,
						towns);
				
				townAutoSignUp.setAdapter(townsAdapter);
			}
		});
		
		townAutoSignUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int townId = ((ArrayAdapter<Town>) parent.getAdapter()).getItem(position).getId();
				List<Zone> zones = addressViewModel.getZones(townId);
				ArrayAdapter<Zone> zonesAdapter = new ArrayAdapter<>(SignUpActivity.this,
						android.R.layout.simple_spinner_dropdown_item,
						zones);
				
				zoneAutoSignUp.setAdapter(zonesAdapter);
			}
		});
		
		zoneAutoSignUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				zoneId = ((Zone) parent.getAdapter().getItem(position)).getId();
			}
		});
		
		
		findViewById(R.id.btn_activity_sign_up).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean inputsValidated = true;
				
				String name = nameSignUp.getEditText().getText().toString().trim();
				String phoneNumber = phoneSignUp.getEditText().getText().toString().trim();
				String password = passwordSignUp.getEditText().getText().toString().trim();
				String passwordConfirmation = passwordConfirmationSignUp.getEditText().getText().toString().trim();
				String address = addressDetailsSignUp.getEditText().getText().toString().trim();
				
				if (name.length() < 4) {
					nameSignUp.setError("يجب ان يتكون الاسم من 4 احرف أو أكثر");
					inputsValidated = false;
				} else {
					nameSignUp.setError(null);
				}
				if (phoneNumber.length() < 8) {
					phoneSignUp.setError("يجب ان يتكون رقم التليفون من 8 ارقام أو أكثر");
					inputsValidated = false;
				} else {
					phoneSignUp.setError(null);
				}
				if (password.length() < 6) {
					passwordSignUp.setError("يجب ان يتكون الرقم السري من 6 احرف أو أكثر");
					inputsValidated = false;
				} else {
					passwordSignUp.setError(null);
				}
				if (address.length() < 4) {
					addressDetailsSignUp.setError("يجب ان يتكون العنوان من 4 احرف أو أكثر");
					inputsValidated = false;
				} else {
					addressDetailsSignUp.setError(null);
				}
				if (!passwordConfirmation.equals(password)) {
					passwordConfirmationSignUp.setError("الرقم السرى ليس متطابقا");
					inputsValidated = false;
				} else {
					passwordConfirmationSignUp.setError(null);
				}
				if (zoneId < 0) {
					zoneSignUp.setError("اختر منطقة");
					inputsValidated = false;
				} else {
					zoneSignUp.setError(null);
				}
				
				if (inputsValidated) {
					AuthenticationUser user = new AuthenticationUser(phoneNumber, name, password,
							passwordConfirmation, address, zoneId);
					
					userViewModel.registerUser(user);
					userViewModel.getAuthenticationStateObservable().observe(SignUpActivity.this, new Observer<UserRepository.Authentication>() {
						@Override
						public void onChanged(UserRepository.Authentication authentication) {
							switch (authentication) {
								case AUTHENTICATED:
									finish();
									break;
								case AUTHENTICATION_ERROR: {
									ErrorDialogFragment dialogFragment =
                                            new ErrorDialogFragment("حدث خطأ", "فشلت عملية التسجيل", false);
									dialogFragment.show(getSupportFragmentManager(), null);
									break;
								}
								case AUTHENTICATION_FAILED: {
                                    ErrorDialogFragment dialogFragment =
                                            new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), false);
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
		return findViewById(R.id.toolbar_sign_up);
	}
	
	@Override
	int deliverLayout() {
		return R.layout.activity_sign_up;
	}
}
