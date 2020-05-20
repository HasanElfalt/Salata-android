package com.ar.salata.repositories.model;

import io.realm.RealmObject;

public class Order extends RealmObject {
    private String orderId;
    private String orderDateDay;
    private String orderDateHour;
    private double orderPrice;

    private int orderImage;
	private boolean orderFulfilled;

    public Order(String orderId,
                 String orderDateDay,
                 String orderDateHour,
                 double orderPrice,
                 int orderImage) {

        this.orderDateDay = orderDateDay;
        this.orderDateHour = orderDateHour;
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.orderImage = orderImage;
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
}
