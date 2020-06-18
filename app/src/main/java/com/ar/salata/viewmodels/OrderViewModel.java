package com.ar.salata.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.salata.repositories.OrderRepository;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.Order;

public class OrderViewModel extends ViewModel {
    public static final String ORDER_ID = "OrderId";
    private OrderRepository orderRepository = new OrderRepository();
    private MutableLiveData<Order> orderMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<UserRepository.APIResponse> submitOrder(APIToken token, Order order) {
        return orderRepository.submitOrder(token, order);
    }

    public String createOrder(Order order) {
        return orderRepository.createOrder(order);
    }

    public Order getOrder(String id) {
        return orderRepository.getOrder(id);
    }

    public void setOrderValue(Order order) {
        orderMutableLiveData.setValue(order);
    }

    public MutableLiveData<Order> getOrderMutableLiveData() {
        return orderMutableLiveData;
    }
}
