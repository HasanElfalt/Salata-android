package com.ar.salata.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.salata.repositories.OpayRepository;
import com.ar.salata.repositories.model.OpaySetting;

public class OpayViewModel extends ViewModel {

    private OpayRepository opayRepository = new OpayRepository();

    public MutableLiveData<OpaySetting> getOpaySetting(){
        return opayRepository.getOpaySettings();
    }
}
