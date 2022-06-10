package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockProductList extends PaginatedResponse{
    @SerializedName("data")
    private List<StockProduct> productList;

    public StockProductList(List<StockProduct> productList) {
        this.productList = productList;
    }

    public List<StockProduct> getProductList() {
        return productList;
    }
}
