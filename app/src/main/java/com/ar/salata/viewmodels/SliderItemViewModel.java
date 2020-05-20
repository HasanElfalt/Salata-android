package com.ar.salata.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.salata.repositories.SliderItemRepository;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.SliderItem;

import java.util.List;

public class SliderItemViewModel extends ViewModel {
	SliderItemRepository sliderItemRepository = new SliderItemRepository();
	
	public MutableLiveData<UserRepository.APIResponse> loadSliderItems() {
		return sliderItemRepository.loadSliderItems();
	}
	
	public List<SliderItem> getSliderItems() {
		return sliderItemRepository.getSliderItems();
	}
}
