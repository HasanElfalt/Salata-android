package com.ar.salata.repositories.model;

import io.realm.RealmObject;

public class OrderUnit extends RealmObject {
	private Product product;
	private double count;

	public OrderUnit(Product product, double count) {
		this.product = product;
		this.count = count;
	}

	public OrderUnit() {
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}
}
