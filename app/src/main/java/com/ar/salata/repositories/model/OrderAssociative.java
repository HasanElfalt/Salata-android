package com.ar.salata.repositories.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class OrderAssociative {
    private static final String TAG = "OrderAssociative";
    @SerializedName("units_count")
    private HashMap<String, Double> units = new HashMap<>();

    @SerializedName("api_token")
    private String token;

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("order_date_day")
    private String orderDateDay;
    @SerializedName("order_date_hour")
    private String orderDateHour;
    @SerializedName("total_price")
    private double orderPrice;
    @SerializedName("shift_id")
    private int shiftId;
    @SerializedName("address_id")
    private int addressId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("delivery_date")
    private String deliveryDate;

    public OrderAssociative(HashMap<String, Double> units, String token, double orderPrice, int shiftId, int addressId, int userId, String deliveryDate) {
        this.units = units;
        this.token = token;
        this.orderPrice = orderPrice;
        this.shiftId = shiftId;
        this.addressId = addressId;
        this.userId = userId;
        this.deliveryDate = deliveryDate;
    }

    public OrderAssociative(HashMap<String, Double> units, String token, double orderPrice, int shiftId, int addressId, int userId, int orderId, String deliveryDate) {
        this.units = units;
        this.token = token;
        this.orderPrice = orderPrice;
        this.shiftId = shiftId;
        this.addressId = addressId;
        this.userId = userId;
        this.orderId = orderId;
        this.deliveryDate = deliveryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderDateDay() {
        return orderDateDay;
    }

    public void setOrderDateDay(String orderDateDay) {
        this.orderDateDay = orderDateDay;
    }

    public String getOrderDateHour() {
        return orderDateHour;
    }

    public void setOrderDateHour(String orderDateHour) {
        this.orderDateHour = orderDateHour;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public HashMap<String, Double> getUnits() {
        return units;
    }

    public void setUnits(HashMap<String, Double> units) {
        this.units = units;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        for (String s : units.keySet()) {
            str.append("\"").append(s).append("\":\"").append(units.get(s)).append("\",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("}");
        Log.d(TAG, "toString: " + str.toString());
        return str.toString();
    }
}
