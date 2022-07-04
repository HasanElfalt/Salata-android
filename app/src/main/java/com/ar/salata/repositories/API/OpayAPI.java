package com.ar.salata.repositories.API;

import com.ar.salata.repositories.model.OpaySetting;
import com.ar.salata.repositories.model.Refund;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OpayAPI {

    @GET("api/getOpaySitting")
    Call<OpaySetting> getOpaySetting();

    @POST("api/setOpayPaymentInput")
    Call<List<String>> setOpayPaymentInput(@Query("user_id") int userId,
                                          @Query("reference") String reference);

    @GET("api/refund")
    Call<Refund> refund(@Query("order_id") String orderId);
}
