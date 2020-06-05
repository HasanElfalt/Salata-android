package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class UserAddress extends RealmObject {

    @SerializedName("details")
    private String address;
    @SerializedName("id")
    private int addressId;
    private String zone;
    @SerializedName("zone_id")
    private int zoneId;
    private String town;
    @SerializedName("town_id")
    private int townId;
    private String city;
    @SerializedName("city_id")
    private int cityId;
    private RealmList<DeliveryDate> dates;

    public UserAddress() {
    }

    public UserAddress(String address, int zoneId) {
        this.address = address;
        this.zoneId = zoneId;
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public List<DeliveryDate> getDates() {
        return dates;
    }

    public void setDates(RealmList<DeliveryDate> dates) {
        this.dates = dates;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
