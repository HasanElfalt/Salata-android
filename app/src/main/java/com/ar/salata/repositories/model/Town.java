package com.ar.salata.repositories.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Town extends RealmObject {
	@PrimaryKey
	private int id;
	
	private String name = null;
	@SerializedName("city_id")
	private int cityId;
	
	public Town(int id, String name, int cityId) {
		this.id = id;
		this.name = name;
		this.cityId = cityId;
	}
	
	public Town() {
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCityId() {
		return cityId;
	}
	
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	@NonNull
	@Override
	public String toString() {
		return name;
	}
}
