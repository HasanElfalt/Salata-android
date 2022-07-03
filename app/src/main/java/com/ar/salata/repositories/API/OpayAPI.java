package com.ar.salata.repositories.API;

import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.OpaySetting;
import com.ar.salata.repositories.model.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OpayAPI {

    @GET("api/getOpaySitting")
    Call<OpaySetting> getOpaySetting();

    @POST("api/setOpayPaymentInput")
    Call<List<String>> setOpayPaymentInput(@Query("user_id") int userId,
                                          @Query("reference") String reference);
}
