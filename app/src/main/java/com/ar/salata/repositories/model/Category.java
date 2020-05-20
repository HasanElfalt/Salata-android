package com.ar.salata.repositories.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Category extends RealmObject implements Parcelable {
	public static final Creator<Category> CREATOR = new Creator<Category>() {
		@Override
		public Category createFromParcel(Parcel in) {
			return new Category(in);
		}
		
		@Override
		public Category[] newArray(int size) {
			return new Category[size];
		}
	};
	private String categoryID;
	private String categoryName;
	
	public Category(String categoryID, String categoryName) {
		this.categoryID = categoryID;
		this.categoryName = categoryName;
	}
	
	protected Category(Parcel in) {
		categoryID = in.readString();
		categoryName = in.readString();
	}
	
	public Category() {
	}
	
	public String getCategoryID() {
		return categoryID;
	}
	
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(categoryID);
		parcel.writeString(categoryName);
	}
}
