package com.nick.test;

import com.nick.dao.OrderDao;
import com.nick.dao.impl.OrderDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

public class TeseOrderDao {
    OrderDao orderDao = new OrderDaoImpl();
    @Test
    public void testList() throws SQLException {
        System.out.println(orderDao.selectOrderList());
    }
}
