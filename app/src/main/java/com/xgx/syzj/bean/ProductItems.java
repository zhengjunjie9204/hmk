package com.xgx.syzj.bean;

/**
 * Created by 32918 on 2016/8/9.
 */
public class ProductItems {


    /**
     * totalPrice : 100.0
     * price : 50.0
     * laborTime : 2.0
     * name : 打蜡
     */

    private double totalPrice;
    private double price;
    private double laborTime;
    private String name;

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getLaborTime()
    {
        return laborTime;
    }

    public void setLaborTime(double laborTime)
    {
        this.laborTime = laborTime;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}