package com.ar.salata.repositories.API;

import com.ar.salata.repositories.model.OrderAssociative;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderAPI {

	@POST("/api/createOrder")
	Call<String> createOrder(@Body OrderAssociative units);
}
