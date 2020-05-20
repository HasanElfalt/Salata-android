package com.ar.salata.repositories.API;

import com.ar.salata.repositories.model.SliderItemList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SliderAPI {
	@GET("/api/getSliderPics")
	Call<SliderItemList> getSliderItems();
}
