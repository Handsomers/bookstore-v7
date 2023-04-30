package com.nick.bean;


import java.math.BigDecimal;
import java.util.*;

import com.nick.bean.CartItem;

public class Cart {
    /**
     * 当前购物车的总金额
     */
    private Double totalAmount = 0d;

    /**
     * 当前购物车的商品总数
     */
    private Integer totalCount=0;

    /**
     * 存储购物项的容器
     * 以购物项的bookId作为key,以购物项的cartItem作为value
     */
    private Map<Integer, CartItem> cartItemMap = new HashMap<>();
    /**
     * 将某本书添加进购物车
     * @param book
     */

    public void addBookToCart(Book book){
        //1、判断当前购物车中是否已经有这本书了
        if (cartItemMap.containsKey(book.getBookId())){
            //说明当前购物车已经包含了这本书，那么只需要将这本书对应的购物项的count+1就行了
            cartItemMap.get(book.getBookId()).countIncrease();
        }else {
            //说明当前购物车中不包括这本书，就要添加一个购物项
            CartItem cartItem = new CartItem();
            //设置cartItem中的内容
            cartItem.setBookId(book.getBookId());
            cartItem.setBookName(book.getBookName());
            cartItem.setImgPath(book.getImgPath());
            cartItem.setPrice(book.getPrice());
            cartItem.setCount(1);
            //将cartItem添加cartItemMap
            cartItemMap.put(book.getBookId(),cartItem);
        }
    }
    /**
     * 将某一个购物项的数量+1
     * @param bookId
     */
    public void itemCountIncrease(Integer bookId){
        //1、根据bookId找到对应的购物项
        //2、调用购物项的countDecrease()方法进行数量+1
        cartItemMap.get(bookId).countIncrease();
    }
    /**
     * 将某一个购物项的数量-1
     * @param bookId
     */
    public void itemCountDecrease(Integer bookId){
        //1、根据bookId找到对应的购物项
        //2、调用购物项的countDecrease()方法进行数量-1
        CartItem cartItem = cartItemMap.get(bookId);
        cartItem.countDecrease();
        //3、判断当前购物项恶数量是否大于0，如果不大于0，说明我们需要当前从购物项中删除
        if (cartItem.getCount()==0){
            cartItemMap.remove(bookId);
        }
    }
    /**
     * 根据bookId将购物项从购物车中移除
     * @param bookId
     */
    public void removeCartItem(Integer bookId){
        cartItemMap.remove(bookId);
    }
    /**
     * 修改某个购物项的数量
     * @param bookId
     * @param newCount
     */
    public void updateItemCount(Integer bookId,Integer newCount){
        //1、根据bookId找到对应的购物项
        //2、将newCount设置到购物项恶count属性
        cartItemMap.get(bookId).setCount(newCount);
    }


    /**
     * 计算商品的总金额
     * @return
     */
    //计算商品的总金额
    public Double getTotalAmount(){
        BigDecimal bigDecimalTotalAmount = new BigDecimal("0.0");
        //累加
        Set<Map.Entry<Integer,CartItem>> entries = cartItemMap.entrySet();
        for (Map.Entry<Integer,CartItem> entry:entries){
            CartItem cartItem = entry.getValue();
            Double amount = cartItem.getAmount();
            bigDecimalTotalAmount = bigDecimalTotalAmount.add(new BigDecimal(amount+""));
        }
        //将bigDecimalToalAmount转成double类型付给this.totalAmount
        this.totalAmount = bigDecimalTotalAmount.doubleValue();
        return this.totalAmount;
    }

    public void setTotalAmount(Double totalAmount){
        this.totalAmount = totalAmount;
    }

    /**
     * 计算商品总数量
     */
    public Integer getTotalCount(){
        //计算购物车中所有的商品总数，期数就是累加一个购物项目count
        this.totalCount = 0;
        //获取到Map中所有的value
        Collection<CartItem> values = cartItemMap.values();
        //遍历每一个value
        for (CartItem cartItem:values){
            this.totalCount += cartItem.getCount();
        }
        return this.totalCount;
    }
    public void setTotalCount(Integer totalCount){
        this.totalCount = totalCount;
    }

    public Map<Integer, CartItem> getCartItemMap(){
        return cartItemMap;
    }

    public void  setCartItemMap(Map<Integer, CartItem> cartItemMap){
        this.cartItemMap =cartItemMap;
    }

    //获取购物项列表
    public List<CartItem> getCartItemList(){
        List<CartItem> cartItemList = new ArrayList<>();
        for (CartItem cartItem : cartItemMap.values()){
            cartItemList.add(cartItem);
        }
        return cartItemList;
    }





}
