package com.ar.salata.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String productName;

    public Product(String productName) {
        this.productName = productName;
    }

    protected Product(Parcel in) {
        productName = in.readString();
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
    }
}
