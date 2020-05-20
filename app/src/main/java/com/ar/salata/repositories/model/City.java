package com.ar.salata.repositories.model;

import androidx.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class City extends RealmObject {
	@PrimaryKey
	private int id;
	
	private String name = null;
	private int shown;
	
	public City(int id, String createdAt, String updatedAt, String name, int shown) {
		this.id = id;
		this.name = name;
		this.shown = shown;
	}
	
	public City() {
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
	
	public int getShown() {
		return shown;
	}
	
	public void setShown(int shown) {
		this.shown = shown;
	}
	
	@NonNull
	@Override
	public String toString() {
		return name;
	}
}
