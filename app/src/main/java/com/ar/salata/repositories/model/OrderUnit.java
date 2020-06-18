package com.ar.salata.repositories.model;

import io.realm.RealmObject;

public class OrderUnit extends RealmObject {
    private int productId;
    private String productName;
    private String productUnitName;
    private Double productPrice;
    private double count;

    public OrderUnit(Product product, double count) {
        this.productId = product.getId();
        this.productPrice = product.getMaxPrice();
        this.count = count;
        this.productName = product.getProductName();
        this.productUnitName = product.getUnitName();
    }

    public OrderUnit() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProduct(int productId) {
        this.productId = productId;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUnitName() {
        return productUnitName;
    }

    public void setProductUnitName(String productUnitName) {
        this.productUnitName = productUnitName;
    }
}
