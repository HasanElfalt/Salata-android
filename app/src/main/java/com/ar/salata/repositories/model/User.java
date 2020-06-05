package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
	@PrimaryKey
	private int id;
	
	@SerializedName("token_time_out")
	private long tokenTimeOut;
	@SerializedName("by_admin")
	private int byAdmin;
	@SerializedName("created_at")
	private String createdAt = null;
	@SerializedName("updated_at")
	private String updatedAt = null;
	@SerializedName("phone")
	private String phoneNumber = null;
	private String name = null;
	@SerializedName("api_token")
	private String token = null;
	
	private RealmList<UserAddress> addresses = new RealmList<>();
	
	public User(int id, long tokenTimeOut, int byAdmin, String createdAt, String updatedAt, String phoneNumber, String name, String token) {
		this.id = id;
		this.tokenTimeOut = tokenTimeOut;
		this.byAdmin = byAdmin;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.token = token;
	}
	
	public User() {
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public long getTokenTimeOut() {
		return tokenTimeOut;
	}
	
	public void setTokenTimeOut(long tokenTimeOut) {
		this.tokenTimeOut = tokenTimeOut;
	}
	
	public int getByAdmin() {
		return byAdmin;
	}
	
	public void setByAdmin(int byAdmin) {
		this.byAdmin = byAdmin;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<UserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddress> addresses) {
        this.addresses.clear();
        this.addresses.addAll(addresses);
    }
}
