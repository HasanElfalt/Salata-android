package com.ar.salata.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.salata.repositories.ConfigRepository;
import com.ar.salata.repositories.UserRepository;

import java.util.List;

public class AppConfigViewModel extends ViewModel {
    private ConfigRepository configRepository;

    public AppConfigViewModel() {
        configRepository = new ConfigRepository();
    }

    public MutableLiveData<UserRepository.APIResponse> loadPhones() {
        return configRepository.loadPhones();
    }

    public List<String> getPhones() {
        return configRepository.getPhones();
    }

    public MutableLiveData<UserRepository.APIResponse> loadMinimumPurchases() {
        return configRepository.loadMinimumPurchases();
    }

    public double getMinimumPurchases() {
        return configRepository.getMinimumPurchases();
    }
}
