package com.ar.salata.repositories.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

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
	private String productName;
	private Double maxPrice;
	private String unit;
	private String image;
	
	public Product() {
	}
	
	public Product(String productName) {
		this.productName = productName;
	}
	
	public Product(String productName, Double maxPrice, String unit, String image) {
		this.productName = productName;
		this.maxPrice = maxPrice;
		this.unit = unit;
		this.image = image;
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
