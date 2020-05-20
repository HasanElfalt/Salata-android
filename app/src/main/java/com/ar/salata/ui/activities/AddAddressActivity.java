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
import com.ar.salata.repositories.model.City;
import com.ar.salata.repositories.model.Town;
import com.ar.salata.repositories.model.Zone;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.viewmodels.AddressViewModel;
import com.ar.salata.viewmodels.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AddAddressActivity extends BaseActivity {
	
	private TextInputLayout citySignUp;
	private AutoCompleteTextView cityAutoSignUp;
	private TextInputLayout townSignUp;
	private AutoCompleteTextView townAutoSignUp;
	private TextInputLayout zoneSignUp;
	private AutoCompleteTextView zoneAutoSignUp;
	private TextInputLayout addressDetailsSignUp;
	private int zoneId = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		citySignUp = findViewById(R.id.et_city_add_address);
		cityAutoSignUp = findViewById(R.id.actv_city_add_address);
		townSignUp = findViewById(R.id.et_town_add_address);
		townAutoSignUp = findViewById(R.id.actv_town_add_address);
		zoneSignUp = findViewById(R.id.et_zone_add_address);
		zoneAutoSignUp = findViewById(R.id.actv_zone_add_address);
		addressDetailsSignUp = findViewById(R.id.et_address_details_add_address);
		
		AddressViewModel addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
		UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
		
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
				ArrayAdapter<Town> townsAdapter = new ArrayAdapter<>(AddAddressActivity.this,
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
				ArrayAdapter<Zone> zonesAdapter = new ArrayAdapter<>(AddAddressActivity.this,
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
		
		findViewById(R.id.btn_add_address).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean inputsValidated = true;
				
				String address = addressDetailsSignUp.getEditText().getText().toString().trim();
				
				if (address.length() < 4) {
					addressDetailsSignUp.setError("يجب ان يتكون العنوان من 4 احرف أو أكثر");
					inputsValidated = false;
				} else {
					addressDetailsSignUp.setError(null);
				}
				if (zoneId < 0) {
					zoneSignUp.setError("اختر منطقة");
					inputsValidated = false;
				} else {
					zoneSignUp.setError(null);
				}
				
				if (inputsValidated) {
					addressViewModel.addAddress(userViewModel.getToken(), address, zoneId).observe(AddAddressActivity.this, new Observer<UserRepository.APIResponse>() {
						@Override
						public void onChanged(UserRepository.APIResponse apiResponse) {
							switch (apiResponse) {
								case SUCCESS: {
									finish();
									break;
								}
								case ERROR: {
									ErrorDialogFragment dialogFragment =
											new ErrorDialogFragment("حدث خطأ", "فشلت عملية اضافة عنوان");
									dialogFragment.show(getSupportFragmentManager(), null);
									break;
								}
								case FAILED: {
									ErrorDialogFragment dialogFragment =
											new ErrorDialogFragment("حدث خطأ", "يوجد خطا فى الاتصال بالانترنت");
									dialogFragment.show(getSupportFragmentManager(), null);
									break;
								}
							}
						}
					});
				}

//				finish();
			}
			
		});
	}
	
	@Override
	Toolbar deliverToolBar() {
		return findViewById(R.id.toolbar_add_address);
	}
	
	@Override
	int deliverLayout() {
		return R.layout.activity_add_address;
	}
}
