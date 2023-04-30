package com.nick.dao.impl;


import com.nick.bean.Book;
import com.nick.bean.Order;
import com.nick.dao.BaseDao;
import com.nick.dao.OrderDao;
import com.nick.utils.JDBCUtil;

import java.sql.*;
import java.util.List;

public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {

    @Override
    public Integer insertOrder(Order order) throws Exception {
        try {
            String sql = "insert into t_order (order_sequence,create_time,total_count,total_amount,order_status,user_id) values (?,?,?,?,?,?)";
            //因为使⽤DBUtils执⾏增删改的SQL语句没法获取⾃增⻓的id主键，所以我们只能使⽤原始的JDBC执⾏这个添加数据的SQL语句并且获取⾃增⻓的i
            // 1. 获取连接
            Connection conn = JDBCUtil.getConnection();
            //2. 预编译SQL语句
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //3. 设置问号处的参数
            preparedStatement.setObject(1, order.getOrderSequence());
            preparedStatement.setObject(2, order.getCreateTime());
            preparedStatement.setObject(3, order.getTotalCount());
            preparedStatement.setObject(4, order.getTotalAmount());
            preparedStatement.setObject(5, order.getOrderStatus());
            preparedStatement.setObject(6, order.getUserId());
            //4. 执⾏SQL语句
            preparedStatement.executeUpdate();
            //5. 获取⾃增⻓的主键值
            ResultSet rst = preparedStatement.getGeneratedKeys();
            //因为⾃增⻓的主键只有⼀个值，所以不需要while循环遍历
            int orderId = 0;
            if (rst.next()) {
                orderId = rst.getInt(1);
            }
            //关闭连接
//        JDBCUtil.releaseConnection(conn);
            return orderId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
//    }
        }
    }
    @Override
    public List<Order> selectOrderList() throws SQLException {
        String sql = "select order_id orderId,order_sequence orderSequence,create_time createTime,total_count totalCount,total_amount totalAmount,order_status orderStatus,user_id userId from t_order";
        return getBeanList(Order.class,sql);
    }

    @Override
    public void removeOrder(Integer id) throws SQLException {
        String sql = "delete from t_order where order_id=?" ;
        update(sql,id);
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        String sql = "update t_order set order_sequence=?,create_time=?,total_amount=?,order_status=?,user_id=?,total_amount=?  where order_id = ?";
        update(sql,order.getOrderSequence(),order.getCreateTime(),order.getTotalAmount(),order.getOrderStatus(),order.getUserId(),order.getTotalAmount(),order.getOrderId());
    }

    @Override
    public Order selectOrderById(Integer id) throws SQLException {
        String sql = "select order_id orderId,order_sequence orderSequence,create_time createTime,total_count totalCount,total_amount totalAmount,order_status orderStatus,user_id userId from t_order where order_id = ?";

        return getBean(Order.class,sql,id);
    }


}