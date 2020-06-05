package com.ar.salata.repositories.API;

import com.ar.salata.repositories.model.CategoryList;
import com.ar.salata.repositories.model.ProductList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoodsAPI {
    @GET("/api/getCats")
    Call<CategoryList> getCategories();

    @GET("/api/getAllProducts")
    Call<ProductList> getAllProducts();

}
