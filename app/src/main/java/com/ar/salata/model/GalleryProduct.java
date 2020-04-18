package com.ar.salata.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GalleryProduct implements Parcelable {
    private String productName;
    private Double maxPrice;
    private String unit;
    private String image;

    public GalleryProduct(String productName) {
        this.productName = productName;
    }

    public GalleryProduct(String productName, Double maxPrice, String unit, String image) {
        this.productName = productName;
        this.maxPrice = maxPrice;
        this.unit = unit;
        this.image = image;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Creator<GalleryProduct> getCREATOR() {
        return CREATOR;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    protected GalleryProduct(Parcel in) {
        productName = in.readString();
    }

    public static final Creator<GalleryProduct> CREATOR = new Creator<GalleryProduct>() {
        @Override
        public GalleryProduct createFromParcel(Parcel in) {
            return new GalleryProduct(in);
        }

        @Override
        public GalleryProduct[] newArray(int size) {
            return new GalleryProduct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
    }
}
