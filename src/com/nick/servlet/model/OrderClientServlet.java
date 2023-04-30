package com.nick.servlet.model;

import com.nick.bean.Cart;
import com.nick.bean.Order;
import com.nick.bean.User;
import com.nick.constants.BookStoreConstants;
import com.nick.service.OrderService;
import com.nick.service.impl.OrderServiceImpl;
import com.nick.servlet.base.ModelBaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderClientServlet extends ModelBaseServlet {
    private OrderService orderClientService = new OrderServiceImpl();

    //订单结算
    public void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            //1、从sessio中获取购物车信息
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //2、从session中获取用户信息
            User user = (User) session.getAttribute(BookStoreConstants.LOGINUSER_SESSIONKEY);
            //3、调用业务层的方法，进行订单结算，并且获取订单的序列号
            String orderSequence = orderClientService.checkout(cart,user);
            //4、清空购物车
            session.removeAttribute(BookStoreConstants.CART_SESSIONKEY);
            //5、将订单序列号存储到请求域对象中，并且跳转到checkout.html页面
            request.setAttribute("orderSequence",orderSequence);
            processTemplate("cart/checkout",request,response);
        } catch (Exception e) {
            e.printStackTrace();
            processTemplate("error",request,response);
            throw new RuntimeException(e.getMessage());
        }
    }
//    显示订单
    public void showOrderList(HttpServletRequest request,HttpServletResponse response) throws Exception {
        try {
            List<Order> orderlist = orderClientService.getOrderList();
//            if (orderlist!=null){
//                for(Order order:orderlist){
//                    if (order.getOrderStatus()==0){
//                        order.setOrderStatus(0);
//                    }
//                }
//            }
            request.setAttribute("orderList", orderlist);
            processTemplate("order/order", request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //删除订单
    public void removeOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
//        获取要删除订单号的id
        Integer id = Integer.valueOf(request.getParameter("id"));
        try{
            orderClientService.removeOrder(id);
//            删除成功，重新加载页面
            response.sendRedirect(request.getContextPath()+"/protected/orderClient?method=showOrderList");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
//    修改订单
    public void saveOrUpdateOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
//        请求获得参数
        Map<String,String[]> parameterMap = request.getParameterMap();
        //2、将参数封装到order对象中
        //2、将parameterMap中的数据封装到Book对象
        try{
            Order order = new Order();
            BeanUtils.populate(order,parameterMap);
            orderClientService.saveOrUpdateBook(order);
            //保存成功，则重新查询所有书籍
            response.sendRedirect(request.getContextPath()+"/protected/orderClient?method=showOrderList");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void toEditPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Integer id = Integer.valueOf(request.getParameter("id"));
        try {
            Order order = orderClientService.getOrderById(id);
            System.out.println(order);
            //将订单信息存储在请求域
            request.setAttribute("order", order);
            processTemplate("order/order_edit", request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //跳转到添加订单页面
    public void toAddPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        processTemplate("order/order_edit", request, response);
    }
}
