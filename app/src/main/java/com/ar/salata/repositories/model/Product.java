package com.ar.salata.repositories.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject implements Parcelable {
	public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @PrimaryKey
    private int id;
    @SerializedName("cat_id")
    private int categoryId;
    @SerializedName("name")
    private String productName;
    @SerializedName("max_price")
    private Double maxPrice;
    private Double step;
    @SerializedName("invoice_image")
    private String invoiceImage;
    @SerializedName("brochure_image")
    private String brochureImage;
    @SerializedName("unit_name")
    private String unitName;
    @SerializedName("unit_id")
    private String unitId;

    public Product() {
    }

    public Product(String productName) {
        this.productName = productName;
    }

    public Product(String productName, Double maxPrice, String unitName, String brochureImage) {
        this.productName = productName;
        this.maxPrice = maxPrice;
        this.unitName = unitName;
        this.brochureImage = brochureImage;
    }

    protected Product(Parcel in) {
        productName = in.readString();
    }
	
	public static Creator<Product> getCREATOR() {
		return CREATOR;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBrochureImage() {
        return brochureImage;
    }

    public void setBrochureImage(String brochureImage) {
        this.brochureImage = brochureImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInvoiceImage() {
        return invoiceImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
    }

    public int getId() {
        return id;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }
}
