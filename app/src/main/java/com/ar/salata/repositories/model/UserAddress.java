package com.ar.salata.repositories.model;

import io.realm.RealmObject;

public class UserAddress extends RealmObject {
	private String address;
	private int zoneId;
	private int userId;
	
	public UserAddress() {
	}
	
	public UserAddress(String address, int zoneId, int userId) {
		this.address = address;
		this.zoneId = zoneId;
		this.userId = userId;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getZoneId() {
		return zoneId;
	}
	
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
