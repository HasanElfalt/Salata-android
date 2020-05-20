package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SliderItem extends RealmObject {
	@PrimaryKey
	private int id;
	private String description;
	@SerializedName("url")
	private String imageUrl;
	
	public SliderItem() {
	}
	
	public SliderItem(String imageUrl, String description) {
		this.imageUrl = imageUrl;
		this.description = description;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
}
