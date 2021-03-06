package com.xgx.syzj.bean;

import java.io.Serializable;

/**
 * Created by 32918 on 2016/8/5.
 */
public class OrderList implements Serializable {
    private int id;
    private double payAmount;
    private String createTime;
    private String name;
    private String mobile;
    private int employeeId;
    private String count;
    private String orderNum;
    /*0:未支付未完成,3:未支付已完成*/
    private int payStatus;
    private String carNumber;
    private int itemId;
    private String itemName;
    //1: 消费类  0: 充值类*/
    private String orderType;



    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }



    public String getCount()
    {
        return count;
    }

    public void setCount(String count)
    {
        this.count = count;
    }

    public int getItemId()
    {
        return itemId;
    }

    public void setItemId(int itemId)
    {
        this.itemId = itemId;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public double getPayAmount()
    {
        return payAmount;
    }

    public void setPayAmount(double payAmount)
    {
        this.payAmount = payAmount;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public int getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(int employeeId)
    {
        this.employeeId = employeeId;
    }

    public int getPayStatus()
    {
        return payStatus;
    }

    public void setPayStatus(int payStatus)
    {
        this.payStatus = payStatus;
    }

    public String getCarNumber()
    {
        return carNumber;
    }

    public void setCarNumber(String carNumber)
    {
        this.carNumber = carNumber;
    }
}
