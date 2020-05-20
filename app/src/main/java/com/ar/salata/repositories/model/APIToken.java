package com.ar.salata.repositories.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class APIToken extends RealmObject {
	@SerializedName("api_token")
	private String token;
	
	public APIToken() {
	}
	
	public APIToken(String token) {
		this.token = token;
	}
	
	@NonNull
	@Override
	public String toString() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}
