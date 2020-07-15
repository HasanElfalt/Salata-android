package com.ar.salata.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.salata.repositories.AddressRepository;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.City;
import com.ar.salata.repositories.model.Town;
import com.ar.salata.repositories.model.Zone;

import java.util.List;

public class AddressViewModel extends ViewModel {

    private AddressRepository addressRepository = new AddressRepository();

    public AddressViewModel() {
    }

    public MutableLiveData<UserRepository.APIResponse> loadCities() {
        return addressRepository.loadCities();
    }

    public MutableLiveData<UserRepository.APIResponse> loadTowns() {
        return addressRepository.loadTowns();
    }

    public MutableLiveData<UserRepository.APIResponse> loadZones() {
        return addressRepository.loadZones();
    }

    public List<Town> getTowns(int cityId) {
        return addressRepository.getTowns(cityId);
    }

    public List<Zone> getZones(int zoneId) {
        return addressRepository.getZones(zoneId);
    }

    public List<City> getCities() {
        return addressRepository.getCities();
    }

    public MutableLiveData<UserRepository.APIResponse> addAddress(APIToken token, String address, int zoneId) {
        return addressRepository.addAddress(token, address, zoneId);
    }

    public MutableLiveData<UserRepository.APIResponse> loadAddresses(APIToken token) {
        return addressRepository.loadAddresses(token);
    }

    public MutableLiveData<UserRepository.APIResponse> deleteAddress(APIToken token, int addressId) {
        return addressRepository.deleteAddress(token, addressId);
    }
}
