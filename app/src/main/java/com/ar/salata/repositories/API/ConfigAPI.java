package com.ar.salata.repositories.API;

import com.ar.salata.repositories.model.CategoryList;
import com.ar.salata.repositories.model.ProductList;
import com.ar.salata.repositories.model.ResponseDouble;
import com.ar.salata.repositories.model.ResponseMessage;
import com.ar.salata.repositories.model.StockProductList;
import com.ar.salata.repositories.model.phonesList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ConfigAPI {
    @GET("/api/getMinPurchases")
    Call<ResponseDouble> getMinimumPurchases();

    @GET("/api/getMinTimeChangeOrder")
    Call<ResponseDouble> getMinimumTimeChangeOrder();

    @GET("/api/getCompanyPhones")
    Call<phonesList> getCompanyPhones();

    @GET("/api/getCompanyName")
    Call<ResponseMessage> getCompanyName();

    @GET("/api/getAboutUs")
    Call<ResponseMessage> getCompanyAbout();

    @GET("/api/getContactUs")
    Call<ResponseMessage> getCompanyContact();

    @GET("/api/getFacebookAccount")
    Call<CategoryList> getFacebookAccount();

    @GET("/api/getTwitterAccount")
    Call<ProductList> getTwitterAccount();

    @GET("/api/getInstagramAccount")
    Call<StockProductList> getInstagramAccount();
}
