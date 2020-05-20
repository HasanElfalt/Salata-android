package com.ar.salata.repositories.model;

public class AuthenticationUser {
	private String phoneNumber;
	private String name;
	private String password;
	private String passwordConfirmation;
	private String address;
	private int zoneID;
	
	public AuthenticationUser(String phoneNumber, String name, String password, String passwordConfirmation, String address, int zoneID) {
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.address = address;
		this.zoneID = zoneID;
	}
	
	public AuthenticationUser(String phoneNumber, String password) {
		this.phoneNumber = phoneNumber;
		this.password = password;
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getZoneID() {
		return zoneID;
	}
	
	public void setZoneID(int zoneID) {
		this.zoneID = zoneID;
	}
}
