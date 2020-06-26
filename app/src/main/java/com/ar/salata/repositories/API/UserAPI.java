package com.ar.salata.repositories.API;

import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.ResponseMessage;
import com.ar.salata.repositories.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {
    @FormUrlEncoded
    @POST("api/register")
    Call<APIToken> registerUser(@Field("phone") String phoneNumber,
                                @Field("name") String name,
                                @Field("password") String password,
                                @Field("password_confirmation") String passwordConfirmation,
                                @Field("zone_id") int zoneID,
                                @Field("address") String address);


    @FormUrlEncoded
    @POST("/api/login")
    Call<APIToken> loginUser(@Field("phone") String phoneNumber, @Field("password") String password);

    @Headers({"Accept: application/json"})
    @GET("/api/user")
    Call<User> getUser(@Query("api_token") String token);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("/api/logout")
    Call<ResponseMessage> logout(@Field("api_token") String token);
}
