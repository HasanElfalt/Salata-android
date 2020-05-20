package com.ar.salata.repositories.model;

import java.util.List;

public class ZoneList {
	private List<Zone> zones;
	
	public ZoneList(List<Zone> zones) {
		this.zones = zones;
	}
	
	public List<Zone> getZones() {
		return zones;
	}
	
	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}
}
