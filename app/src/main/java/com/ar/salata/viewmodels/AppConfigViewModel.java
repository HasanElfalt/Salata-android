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
/*
    public MutableLiveData<UserRepository.APIResponse> loadMinimumPurchases() {
        return configRepository.loadMinimumPurchases();
    }
*/
    public double getMinimumPurchases() {
        return configRepository.getMinimumPurchases();
    }

    public MutableLiveData<UserRepository.APIResponse> loadFacebook() {
        return configRepository.loadFacebook();
    }

    public String[] getFacebook() {
        return configRepository.getFacebook();
    }

    public MutableLiveData<UserRepository.APIResponse> loadTwitter() {
        return configRepository.loadTwitter();
    }

    public String[] getTwitter() {
        return configRepository.getTwitter();
    }

    public MutableLiveData<UserRepository.APIResponse> loadInstagram() {
        return configRepository.loadInstagram();
    }

    public String[] getInstagram() {
        return configRepository.getInstagram();
    }

    public MutableLiveData<UserRepository.APIResponse> loadContactUs() {
        return configRepository.loadContactUs();
    }

    public String getContactUs() {
        return configRepository.getContactUs();
    }

    public MutableLiveData<UserRepository.APIResponse> loadAboutUs() {
        return configRepository.loadAboutUs();
    }

    public String getAboutUs() {
        return configRepository.getAboutUs();
    }
}
