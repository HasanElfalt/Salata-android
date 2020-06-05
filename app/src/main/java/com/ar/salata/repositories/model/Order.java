package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Order extends RealmObject {

	@SerializedName("order_id")
	private String orderId;
	@SerializedName("order_date_day")
	private String orderDateDay;
	@SerializedName("order_date_hour")
	private String orderDateHour;
	@SerializedName("order_price")
	private double orderPrice;
	@SerializedName("shift_id")
	private int shiftId;
	@SerializedName("address_id")
	private int addressId;
	@SerializedName("user_id")
	private int userId;
	@SerializedName("delivery_date")
	private String deliveryDate;
	private RealmList<OrderUnit> units = new RealmList<>();

	private int orderImage;
	private boolean orderFulfilled;


	public Order(int shiftId,
				 int addressId,
				 int userId,
				 String deliveryDate,
				 double orderPrice) {

		this.shiftId = shiftId;
		this.addressId = addressId;
		this.userId = userId;
		this.deliveryDate = deliveryDate;
		this.orderPrice = orderPrice;
	}

	public Order() {
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public int getOrderImage() {
		return orderImage;
	}

	public void setOrderImage(int orderImage) {
		this.orderImage = orderImage;
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

	public boolean isOrderFulfilled() {
		return orderFulfilled;
	}

	public void setOrderFulfilled(boolean orderFulfilled) {
		this.orderFulfilled = orderFulfilled;
	}

	public RealmList<OrderUnit> getUnits() {
		return units;
	}

	public void setUnits(List<OrderUnit> units) {
		this.units.addAll(units);
	}
}
