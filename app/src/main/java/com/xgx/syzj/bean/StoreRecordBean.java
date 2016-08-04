package com.xgx.syzj.bean;

/**
 * Created by 32918 on 2016/7/30.
 */
public class StoreRecordBean {

    private String payTime;
    //储值金额
    private String fee;
    //
    private String payOrder;
    //储值后金额
    private double storedMoney;

    public String getPayTime()
    {
        return payTime;
    }

    public void setPayTime(String payTime)
    {
        this.payTime = payTime;
    }

    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }

    public String getPayOrder()
    {
        return payOrder;
    }

    public void setPayOrder(String payOrder)
    {
        this.payOrder = payOrder;
    }

    public double getStoredMoney()
    {
        return storedMoney;
    }

    public void setStoredMoney(double storedMoney)
    {
        this.storedMoney = storedMoney;
    }
}
