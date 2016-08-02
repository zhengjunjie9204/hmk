package com.xgx.syzj.bean;

/**
 * 消费记录
 * @author ding
 */
public class ConsumeHistory {


    /**
     * payType : 2
     * payAmount : 485
     * createTime : 2016-04-15T11:58:00
     * payTime : 2016-04-15T11:58:00
     * name : 罗伟键
     * mobile : 13725199413
     * orderNum : 201604151158000182
     * id : 9
     * store : null
     * employee : null
     * payStatus : 1
     */

    private int payType;
    private int payAmount;
    private String createTime;
    private String payTime;
    private String name;
    private String mobile;
    private String orderNum;
    private int id;
    private int store;
    private int employee;
    private String payStatus;

    public int getPayType()
    {
        return payType;
    }

    public void setPayType(int payType)
    {
        this.payType = payType;
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

    public String getPayTime()
    {
        return payTime;
    }

    public void setPayTime(String payTime)
    {
        this.payTime = payTime;
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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getStore()
    {
        return store;
    }

    public void setStore(int store)
    {
        this.store = store;
    }

    public int getEmployee()
    {
        return employee;
    }

    public void setEmployee(int employee)
    {
        this.employee = employee;
    }

    public String getPayStatus()
    {
        return payStatus;
    }

    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }
}
