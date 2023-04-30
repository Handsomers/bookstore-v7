package com.nick.dao;

import com.nick.bean.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    public Integer insertOrder(Order order) throws Exception;

    //查询所有订单
    public List<Order> selectOrderList() throws SQLException;

    void removeOrder(Integer id) throws SQLException;

    void updateOrder(Order order) throws SQLException;

    Order selectOrderById(Integer id) throws SQLException;
}
