package com.ar.salata.repositories.API;

import com.ar.salata.repositories.model.AddressList;
import com.ar.salata.repositories.model.CityList;
import com.ar.salata.repositories.model.ResponseMessage;
import com.ar.salata.repositories.model.TownList;
import com.ar.salata.repositories.model.ZoneList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AddressAPI {
	@GET("api/getAllCities")
	Call<CityList> getAllCities();
	
	@GET("api/getAllTowns")
	Call<TownList> getAllTowns();

    @GET("api/getAllZones")
    Call<ZoneList> getAllZones();

    @FormUrlEncoded
    @POST("/api/createAddress")
    Call<ResponseMessage> addAddress(
            @Field("api_token") String token,
            @Field("address") String address,
            @Field("zone_id") int zoneId);

    @GET("api/getAddresses")
    Call<AddressList> getAddresses(@Query("api_token") String token);
}
