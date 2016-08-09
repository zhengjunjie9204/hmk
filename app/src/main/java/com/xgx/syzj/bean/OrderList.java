package com.xgx.syzj.bean;

import java.io.Serializable;

/**
 * Created by 32918 on 2016/8/5.
 */
public class OrderList implements Serializable {
    private int id;
    private int payAmount;
    private String createTime;
    private String name;
    private String mobile;
    private String orderNum;
    private int employeeId;
    /*0:未支付未完成,3:未支付已完成*/
    private int payStatus;
    private String carNumber;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getPayAmount()
    {
        return payAmount;
    }

    public void setPayAmount(int payAmount)
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
