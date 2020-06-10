package com.ar.salata.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.salata.repositories.OrderRepository;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.Order;

public class OrderViewModel extends ViewModel {
    OrderRepository orderRepository = new OrderRepository();
    MutableLiveData<Order> orderMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<UserRepository.APIResponse> submitOrder(APIToken token, Order order) {
        return orderRepository.submitOrder(token, order);
    }

    public void createOrder(Order order) {
        return orderRepository.createOrder(token, order);
    }


    public void setOrderValue(Order order) {
        orderMutableLiveData.setValue(order);
    }

    public MutableLiveData<Order> getOrderMutableLiveData() {
        return orderMutableLiveData;
    }
}
