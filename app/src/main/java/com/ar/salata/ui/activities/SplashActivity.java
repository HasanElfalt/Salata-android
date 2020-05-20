package com.ar.salata.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.viewmodels.AddressViewModel;
import com.ar.salata.viewmodels.SliderItemViewModel;

public class SplashActivity extends AppCompatActivity {
	boolean citiesLoaded = false;
	boolean townsLoaded = false;
	boolean zonesLoaded = false;
	boolean sliderItemsLoaded = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		AddressViewModel addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
		SliderItemViewModel sliderItemViewModel = new ViewModelProvider(this).get(SliderItemViewModel.class);
		
		addressViewModel.loadCities().observe(this, new Observer<UserRepository.APIResponse>() {
			@Override
			public void onChanged(UserRepository.APIResponse apiResponse) {
				switch (apiResponse) {
					case ERROR: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل البيانات");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case FAILED: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "يوجد خطا فى الاتصال بالانترنت");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case SUCCESS: {
						citiesLoaded = true;
						endSplash();
						break;
					}
				}
			}
		});
		addressViewModel.loadTowns().observe(this, new Observer<UserRepository.APIResponse>() {
			@Override
			public void onChanged(UserRepository.APIResponse apiResponse) {
				switch (apiResponse) {
					case ERROR: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل البيانات");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case FAILED: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "يوجد خطا فى الاتصال بالانترنت");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case SUCCESS: {
						townsLoaded = true;
						endSplash();
						break;
					}
				}
			}
		});
		addressViewModel.loadZones().observe(this, new Observer<UserRepository.APIResponse>() {
			@Override
			public void onChanged(UserRepository.APIResponse apiResponse) {
				switch (apiResponse) {
					case ERROR: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل البيانات");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case FAILED: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "يوجد خطا فى الاتصال بالانترنت");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case SUCCESS: {
						zonesLoaded = true;
						endSplash();
						break;
					}
				}
			}
		});
		
		sliderItemViewModel.loadSliderItems().observe(this, new Observer<UserRepository.APIResponse>() {
			@Override
			public void onChanged(UserRepository.APIResponse apiResponse) {
				switch (apiResponse) {
					case ERROR: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل البيانات");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case FAILED: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "يوجد خطا فى الاتصال بالانترنت");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case SUCCESS: {
						sliderItemsLoaded = true;
						endSplash();
						break;
					}
				}
			}
		});
	}
	
	private void endSplash() {
		if (citiesLoaded && townsLoaded && zonesLoaded && sliderItemsLoaded) {
			Intent i = new Intent(getApplicationContext(), HomeActivity.class);
			startActivity(i);
			
			finish();
		}
	}
}
