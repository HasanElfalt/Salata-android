package com.ar.salata.repositories.model;

import io.realm.RealmObject;

public class OrderUnit extends RealmObject {
	private int id;
	private double count;

	public OrderUnit(int id, double count) {
		this.id = id;
		this.count = count;
	}

	public OrderUnit() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}
}
