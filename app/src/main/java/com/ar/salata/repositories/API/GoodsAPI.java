package com.ar.salata.repositories.API;

import com.ar.salata.repositories.model.CategoryList;
import com.ar.salata.repositories.model.ProductList;
import com.ar.salata.repositories.model.StockProductList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GoodsAPI {
    @GET("/api/getCats")
    Call<CategoryList> getCategories();

    @GET("/api/getAllProducts")
    Call<ProductList> getAllProducts();

    @Headers({"Accept: application/json"})
    @GET("/api/getAllProductsByAddressWithRemain")
    Call<StockProductList> getAllProducts(@Query("api_token") String token, @Query("address_id") int addressId);

}
