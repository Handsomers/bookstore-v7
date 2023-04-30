package com.nick.dao.impl;

import com.nick.bean.Order;
import com.nick.dao.BaseDao;
import com.nick.dao.OrderItemDao;

public class OrderItemDaoImpl extends BaseDao<Order> implements OrderItemDao {
    @Override
    public void insertOrderItemArr(Object[][] orderItemArrParam) {
        String sql = "insert into t_order_item (book_name,price,img_path,item_count,item_amount,order_id) values (?,?,?,?,?,?)";
        batchUpdate(sql,orderItemArrParam);
    }
}
