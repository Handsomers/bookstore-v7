package com.nick.service.impl;

import com.nick.bean.Cart;
import com.nick.bean.CartItem;
import com.nick.bean.Order;
import com.nick.bean.User;
import com.nick.constants.BookStoreConstants;
import com.nick.dao.BookDao;
import com.nick.dao.OrderDao;
import com.nick.dao.OrderItemDao;
import com.nick.dao.impl.BookDaoImpl;
import com.nick.dao.impl.OrderDaoImpl;
import com.nick.dao.impl.OrderItemDaoImpl;
import com.nick.service.OrderService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public String checkout(Cart cart, User user) throws Exception {
        //1. 往订单表插⼊⼀条数据  //1.1 ⽣成⼀个唯⼀的订单号(使⽤UUID)
        String orderSequence = UUID.randomUUID().toString();
        //1.2 ⽣成当前时间createTime
        String createTime = new SimpleDateFormat("dd-MM-yy:HH:mm:ss").format(new Date());
        //1.3 订单的totalCount就是cart的totalCount
        Integer totalCount = cart.getTotalCount();
        //1.4 订单的totalAmount就是购物⻋的totalAmount
        Double totalAmount = cart.getTotalAmount();
        //1.5 设置订单的状态为0
        Integer status = BookStoreConstants.ORDER_PAYED;
        //1.6 订单的userId就是user对象的id
        Integer userId = user.getUserId();
        //将上述六个数据封装到⼀个Order对象中
        Order order = new Order(null,orderSequence,createTime,totalCount,totalAmount,status,userId);
        //1.7 调⽤持久层OrderDao的insertOrder⽅法添加订单数据，并且获取⾃增⻓的主键值
        Integer orderId = orderDao.insertOrder(order);
        //2. 往订单项表插⼊多条数据(采⽤批处理)
        //获取所有的购物项
        List<CartItem> cartItemList = cart.getCartItemList();
        //创建⼀个⼆维数组，⽤来做批量添加订单项的参数
        Object[][] orderItemArrParam = new Object[cartItemList.size()][6];
        //3. 更新t_book表中对应的书的sales和stock
        //创建⼀个⼆维数组，⽤来做批量修改图书信息的参数
        Object[][] bookArrParam = new Object[cartItemList.size()][3];
        //遍历出每⼀个购物项
        for (int i=0;i<cartItemList.size();i++) {
            //封装批量添加订单项的⼆维数组参数
            //每⼀个购物项就对应⼀个订单项
            CartItem cartItem = cartItemList.get(i);
            //2.1 bookName就是CartItem的bookName
            //设置第i条SQL语句的第⼀个参数的值
            orderItemArrParam[i][0] = cartItem.getBookName();
            //2.2 price、imgPath、itemCount、itemAmount都是CartItem中对应的数据
            //设置第i条SQL语句的第⼆个参数的值
            orderItemArrParam[i][1] = cartItem.getPrice();
            //设置第i条SQL语句的第三个参数的值
            orderItemArrParam[i][2] = cartItem.getImgPath();
            //设置第i条SQL语句的第四个参数的值
            orderItemArrParam[i][3] = cartItem.getCount();
            //设置第i条SQL语句的第五个参数的值
            orderItemArrParam[i][4] = cartItem.getAmount();
            //2.3 orderId就是第⼀步中保存的订单的id
            //设置第i条SQL语句的第六个参数的值
            orderItemArrParam[i][5] = orderId;
            //封装批量更新图书库存和销量的⼆维数组参数
            // 设置第i条SQL语句的第⼀个参数: 就是要增加的销量就是cartItem的count
            bookArrParam[i][0] = cartItem.getCount();
            // 设置第i条SQL语句的第⼆个参数: 就是要减少的库存就是cartItem的count bookArrParam[i][1] = cartItem.getCount();
            // 设置第i条SQL语句的第三个参数: 就是要修改的图书的bookId就是cartItem的bookId
            bookArrParam[i][2] = cartItem.getBookId();
        }
        //2.4 调⽤持久层OrderItemDao的insertOrderItemArr⽅法进⾏批量添加
        orderItemDao.insertOrderItemArr(orderItemArrParam);
        //3 调⽤持久层BookDao的updateBookArr⽅法进⾏批量更新
        bookDao.updateBookArr(bookArrParam);
        //4. 返回订单号
        return orderSequence;
    }

    @Override
    public List<Order> getOrderList() throws Exception {
        return orderDao.selectOrderList();
    }

    @Override
    public void removeOrder(Integer id) throws Exception {
        orderDao.removeOrder(id);
    }

    @Override
    public void saveOrUpdateBook(Order order) throws Exception {
        //1、判断book中的bookId是否为空
        if (order.getOrderId() == null||"".equals(order.getOrderId())||order.getOrderId()==0){
            //调用持久层的方法添加
            orderDao.insertOrder(order);
            System.out.println(order.getOrderId());
            System.out.println("插入语句");
        }else {
            //说明bookId不能为空
            //调用持久层的方法进行修改
            orderDao.updateOrder(order);
            System.out.println("调用更新语句");
        }
    }

    @Override
    public Order getOrderById(Integer id) throws Exception {
        return orderDao.selectOrderById(id);
    }
}