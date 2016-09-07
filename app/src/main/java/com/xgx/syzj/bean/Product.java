package com.xgx.syzj.bean;

/**
 * Created by 32918 on 2016/8/9.
 */
public class Product {
    /**
     * name : 车窗
     * price : 100
     * count : 2
     * totalPrice : 100
     * returnCount : 1
     * returnPrice : 100
     */

    private String name;
    private double price;
    private int count;
    private int totalPrice;
    private int returnCount;
    private int returnPrice;
    private int  productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public int getReturnCount()
    {
        return returnCount;
    }

    public void setReturnCount(int returnCount)
    {
        this.returnCount = returnCount;
    }

    public int getReturnPrice()
    {
        return returnPrice;
    }

    public void setReturnPrice(int returnPrice)
    {
        this.returnPrice = returnPrice;
    }
}
