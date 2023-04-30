package com.nick.bean;

import java.math.BigDecimal;

public class CartItem {
    /**
     * 购物项存储的那本书的id
     */
    private Integer bookId;
    /**
     * 购物项的那本书的书名
     */
    private String bookName;
    /**
     * 购物项存储那本书的图片路径
     */
    private String imgPath;
    /**
     * 购物项的单价
     */
    private Double price;
    /**
     * 购物车的书的数量
     */
    private Integer count=0;
    /**
     * 购物项的金额
     */
    private Double amount = 0d;

    public CartItem(Integer bookId, String bookName, String imgPath, Double price, Integer count, Double amount) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.imgPath = imgPath;
        this.price = price;
        this.count = count;
        this.amount = amount;
    }

    public CartItem() {

    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getCount() {
        return count;
    }

    //这个方法获取总价，要通过计算才能获得
    public Double getAmount() {
        //1、将price和count封装诚bigDecimal类型
        BigDecimal bigDecimalPrice = new BigDecimal(price +"");
        BigDecimal bigDeciCount = new BigDecimal(count+"");
        //2、使用bigDecimal恶方法进行解决
        this.amount = bigDeciCount.multiply(bigDecimalPrice).doubleValue();
        return amount;
    }


    @Override
    public String toString() {
        return "CartItem{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", amount=" + amount +
                '}';
    }

    /**
     * 将count自增1
     */
    public void countIncrease() {
        this.count++;
    }
    /**
     * 将count自减1
     */
    public void countDecrease(){
        if (this.count>0){
            this.count --;
        }
    }
}
