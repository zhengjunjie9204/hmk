package com.xgx.syzj.bean;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class Combos {
    private String name;
    private double price;
    private long count;
    private long totalPrice;
    private long payType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getPayType() {
        return payType;
    }

    public void setPayType(long payType) {
        this.payType = payType;
    }
}
