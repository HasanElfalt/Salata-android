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
import com.ar.salata.viewmodels.GoodsViewModel;
import com.ar.salata.viewmodels.SliderItemViewModel;

public class SplashActivity extends AppCompatActivity {
    private boolean citiesLoaded = false;
    private boolean townsLoaded = false;
    private boolean zonesLoaded = false;
    private boolean sliderItemsLoaded = false;
    private boolean categoriesItemsLoaded = false;
    private boolean productsItemsLoaded = false;
    private Error error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AddressViewModel addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        SliderItemViewModel sliderItemViewModel = new ViewModelProvider(this).get(SliderItemViewModel.class);
        GoodsViewModel goodsViewModel = new ViewModelProvider(this).get(GoodsViewModel.class);

        addressViewModel.loadCities().observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse apiResponse) {
                citiesLoaded = handleResponse(apiResponse);
                endSplash();
            }
        });
        addressViewModel.loadTowns().observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
			public void onChanged(UserRepository.APIResponse apiResponse) {
                townsLoaded = handleResponse(apiResponse);
                endSplash();
            }
		});
		addressViewModel.loadZones().observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse apiResponse) {
                zonesLoaded = handleResponse(apiResponse);
                endSplash();
            }
        });

        sliderItemViewModel.loadSliderItems().observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse apiResponse) {
                sliderItemsLoaded = handleResponse(apiResponse);
                endSplash();
            }
        });

        goodsViewModel.loadCategories().observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse apiResponse) {
                categoriesItemsLoaded = handleResponse(apiResponse);
                endSplash();
            }
        });
        goodsViewModel.loadProducts().observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse apiResponse) {
                productsItemsLoaded = handleResponse(apiResponse);
                endSplash();
            }
        });
    }

    private boolean handleResponse(UserRepository.APIResponse apiResponse) {
        boolean result = false;
        switch (apiResponse) {
            case ERROR:
                error = Error.ERROR;
                break;
            case FAILED:
                error = Error.FAILED;
                break;
            case SUCCESS: {
                result = true;
                break;
            }
        }
        return result;
    }

    private void endSplash() {
        if (error == Error.ERROR) {
            ErrorDialogFragment dialogFragment =
                    new ErrorDialogFragment("حدث خطأ", "فشل الاتصال بالسيرفر", true);
            dialogFragment.show(getSupportFragmentManager(), null);
        } else if (error == Error.FAILED) {
            ErrorDialogFragment dialogFragment =
                    new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), true);
            dialogFragment.show(getSupportFragmentManager(), null);
        } else if (citiesLoaded && townsLoaded && zonesLoaded && sliderItemsLoaded
                && categoriesItemsLoaded && productsItemsLoaded) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);

            finish();
        }
    }

    private enum Error {
        ERROR, FAILED, SUCCESS
    }
}
