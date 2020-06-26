package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StockProduct extends RealmObject {
    @PrimaryKey
    private int id;
    @SerializedName("catId")
    private int categoryId;
    @SerializedName("name")
    private String productName;

    private Double price;
    private Double step;
    @SerializedName("invoice_image")
    private String invoiceImage;
    @SerializedName("brochure_image")
    private String brochureImage;
    private String unitName;
    private int addressId;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public String getInvoiceImage() {
        return invoiceImage;
    }

    public void setInvoiceImage(String invoiceImage) {
        this.invoiceImage = invoiceImage;
    }

    public String getBrochureImage() {
        return brochureImage;
    }

    public void setBrochureImage(String brochureImage) {
        this.brochureImage = brochureImage;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
