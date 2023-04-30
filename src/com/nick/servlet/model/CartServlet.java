package com.nick.servlet.model;

import com.nick.bean.Book;
import com.nick.bean.Cart;
import com.nick.bean.CartItem;
import com.nick.bean.CommonResult;
import com.nick.constants.BookStoreConstants;
import com.nick.service.BookService;
import com.nick.service.impl.BookServiceImpl;
import com.nick.servlet.base.ModelBaseServlet;
import com.nick.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends ModelBaseServlet {
    private BookService bookService = new BookServiceImpl();

    /**
     * 将书添加进购物车
     * @param request
     * @param response
     */
    public void addCartItem(HttpServletRequest request,HttpServletResponse response){
        CommonResult commonResult = null;
        try{
            //1、获取请求参数：书的id
            Integer id = Integer.valueOf(request.getParameter("id"));
            //2、调用业务层的方法，根据id查询到书
            Book book = bookService.getBookById(id);
            //3、尝试从会话域session中获取购物车信息，主要目的时判断是否是第一次添加商品进购物车
            HttpSession session = request.getSession();
            Cart cart=(Cart) session.getAttribute(BookStoreConstants.CART_SESSIONKEY);
            if (cart == null){
                //说明之前没有购物车，那么就新建一个cart对象
                cart = new Cart();
                //将cart添加到session中
                session.setAttribute(BookStoreConstants.CART_SESSIONKEY,cart);
            }
            //将书添加进购物车
            cart.addBookToCart(book);
            //封装响应数据
            commonResult = CommonResult.success().setResultData(cart.getTotalCount());
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage(e.getMessage());
        }
        //将commResult对象转成jason字符串，响应给客户端
        JsonUtils.writeResult(response,commonResult);
    }
    //获取购物车的商品总数
    public void getTotalCount(HttpServletRequest request,HttpServletResponse response){
        CommonResult commonResult = null;
        try{
            //1、获取购物车
            Cart cart = (Cart) request.getSession().getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //2、获取商品数
            Integer totalCount = 0;
            if (cart!=null){
                totalCount = cart.getTotalCount();
            }
            //3、封装响应结果
            commonResult = CommonResult.success().setResultData(totalCount);
        }catch (Exception e){
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage(e.getMessage());
        }
        //将commonResult转成json响应给客户端
        JsonUtils.writeResult(response,commonResult);
    }
    //跳转购物车页面
    public void toCartPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
        processTemplate("cart/cart",request,response);
    }

    //获取购物车的数据
    public void getCartJSON(HttpServletRequest request,HttpServletResponse response){
        CommonResult commonResult = null;
        try {
            //1、获取购物车信息
            Cart cart = (Cart) request.getSession().getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //2、创建一个Map用于封装客户端需要的数据
            Map responseMap = new HashMap();
            if (cart!=null){
                responseMap.put("totalCount",cart.getTotalCount());
                responseMap.put("totalAmount",cart.getTotalAmount());
                //3、获取cart中的所有购物项：返回一个List<CartItem>
                responseMap.put("cartItemList",cart.getCartItemList());
            }
            //4、将responseMap存储到commonResult对象中
            commonResult = CommonResult.success().setResultData(responseMap);
        }catch (Exception e){
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage(e.getMessage());
        }
        //将commonResult对象转成Json字符串输出到客户端
        JsonUtils.writeResult(response,commonResult);
    }

    //清空购物车
    public void cleanCart(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //1、将cart从session从移除
        request.getSession().removeAttribute(BookStoreConstants.CART_SESSIONKEY);
        //2、跳转回到cart页面
        processTemplate("cart/cart",request,response);
    }
    //获取操作购物车之后的响应数据
    private Map getResponseMap(Integer id,Cart cart){
        CartItem cartItem = cart.getCartItemMap().get(id);
        //将要响应到客户端的数据存储到Map中
        Map responseMap = new HashMap();
        if (cartItem!=null){
            Integer count = cartItem.getCount();
            responseMap.put("count",count);

            Double amount = cartItem.getAmount();
            responseMap.put("amount",amount);
        }
        Integer totalCount = cart.getTotalCount();
        responseMap.put("totalCount",totalCount);

        Double totalAmount = cart.getTotalAmount();
        responseMap.put("totalAmount",totalAmount);

        return responseMap;
    }

    //购物项-1
    public void countDecrease(HttpServletRequest request,HttpServletResponse response) throws IOException{
        CommonResult commonResult = null;
        try {
            //1、获取id的值
            Integer id = Integer.valueOf(request.getParameter("id"));
            //2、从session中获取购物车
            Cart cart = (Cart) request.getSession().getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //3、调用cart的itemCountDecrease(id)方法对某个购物项进行-1操作
            cart.itemCountDecrease(id);
            //4、获取需要响应给客户端的数据：当前购物项的count、amount以及当前购物车的totalCount、totalAmount
            Map responseMap = getResponseMap(id,cart);
            //-1成功
            commonResult = CommonResult.success().setResultData(responseMap);
        }catch (Exception e){
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage(e.getMessage());
        }

        //将commonResult对象转成Json响应给客户端
        JsonUtils.writeResult(response,commonResult);
    }


    //购物项+1
    public void countIncrease(HttpServletRequest request,HttpServletResponse response) throws IOException{
        CommonResult commonResult = null;
        try {
            //1、获取加以的购物项的id
            Integer id = Integer.valueOf(request.getParameter("id"));
            //2、从session中获取购物车信息
            Cart cart = (Cart) request.getSession().getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //3、调用cart的itemCountIncrease(id)方法对购物项+1
            cart.itemCountIncrease(id);
            //4、封装响应数据
            Map responseMap = getResponseMap(id,cart);

            commonResult = CommonResult.success().setResultData(responseMap);
        }catch (Exception e){
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage(e.getMessage());
        }
        //将commonResult转成json响应给客户端
        JsonUtils.writeResult(response,commonResult);
    }

    //删除购物项
    public  void removeCartItem(HttpServletRequest request,HttpServletResponse response) throws IOException{
        CommonResult commonResult = null;
        try {
            //1、获取要删除的购物项的id
            Integer id = Integer.valueOf(request.getParameter("id"));
            //2、从session中获取购物车
            Cart cart = (Cart) request.getSession().getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //3、调用cart的removeCartItem(id)删除购物项
            cart.removeCartItem(id);
            //4、封装响应数量
            Map respomseMap = getResponseMap(id,cart);

            commonResult = CommonResult.success().setResultData(respomseMap);
        }catch (Exception e){
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage(e.getMessage());
        }
        //将commonResult转成json响应给客户端
        JsonUtils.writeResult(response,commonResult);
    }

    //修改某个购物项的count
    public void updateCartItemCount(HttpServletRequest request,HttpServletResponse response) throws IOException{
        CommonResult commonResult = null;
        try {
            //1、获取修改的购物项的id以及修改后的newCount
            Integer id = Integer.valueOf(request.getParameter("id"));
            Integer newCount = Integer.valueOf(request.getParameter("newCount"));
            //2、从session中获取cart
            Cart cart = (Cart) request.getSession().getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //3、调用cart
            cart.updateItemCount(id,newCount);
            //4、封装响应 数据
            Map responseMap = getResponseMap(id,cart);
            commonResult = CommonResult.success().setResultData(responseMap);
        }catch (Exception e){
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage(e.getMessage());
        }
        //将commonResult转成json响应给客户端
        JsonUtils.writeResult(response,commonResult);
    }





}
