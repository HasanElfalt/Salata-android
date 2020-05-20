package com.ar.salata.repositories.model;

import java.util.List;

public class TownList {
	private List<Town> towns;
	
	public TownList(List<Town> towns) {
		this.towns = towns;
	}
	
	public List<Town> getTowns() {
		return towns;
	}
	
	public void setTowns(List<Town> towns) {
		this.towns = towns;
	}
}
