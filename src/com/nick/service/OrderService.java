package com.nick.service;

import com.nick.bean.Cart;
import com.nick.bean.Order;
import com.nick.bean.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    String checkout(Cart cart, User user) throws Exception;
    public List<Order> getOrderList() throws Exception;

    void removeOrder(Integer id) throws Exception;

    void saveOrUpdateBook(Order order) throws Exception;

    Order getOrderById(Integer id) throws Exception;
}
