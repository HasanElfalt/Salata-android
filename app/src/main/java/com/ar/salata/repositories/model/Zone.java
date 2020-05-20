package com.ar.salata.repositories.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Zone extends RealmObject {
	@PrimaryKey
	private int id;
	private String name = null;
	@SerializedName("town_id")
	private int townId;
	@SerializedName("town_name")
	private String townName;
	@SerializedName("city_id")
	private int cityId;
	@SerializedName("city_name")
	private String cityName;
	
	public Zone() {
	}
	
	public Zone(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getTownName() {
		return townName;
	}
	
	public void setTownName(String townName) {
		this.townName = townName;
	}
	
	public int getCityId() {
		return cityId;
	}
	
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
	
	public int getTownId() {
		return townId;
	}
	
	public void setTownId(int townId) {
		this.townId = townId;
	}
	
	@NonNull
	@Override
	public String toString() {
		return name;
	}
}
