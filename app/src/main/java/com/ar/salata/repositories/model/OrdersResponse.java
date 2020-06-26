package com.ar.salata.repositories.model;

import java.util.List;

public class OrdersResponse {
    private List<Order> ordered;
    private List<Order> delivered;

    public List<Order> getOrdered() {
        return ordered;
    }

    public List<Order> getDelivered() {
        return delivered;
    }
}
