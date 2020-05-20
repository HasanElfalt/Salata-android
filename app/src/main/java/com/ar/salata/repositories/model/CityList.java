package com.ar.salata.repositories.model;

import java.util.List;

public class CityList {
	private List<City> cities;
	
	public CityList(List<City> cities) {
		this.cities = cities;
	}
	
	public List<City> getCities() {
		return cities;
	}
	
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}
