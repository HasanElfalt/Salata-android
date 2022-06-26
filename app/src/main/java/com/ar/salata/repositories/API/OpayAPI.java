package com.ar.salata.repositories.API;

import com.ar.salata.repositories.model.OpaySetting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OpayAPI {

    @GET("api/getOpaySitting")
    Call<OpaySetting> getOpaySetting();
/*
    @POST("api/setOpayPaymentInput")
*/
}
