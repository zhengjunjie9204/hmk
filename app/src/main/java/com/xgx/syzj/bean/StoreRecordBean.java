package com.xgx.syzj.bean;

/**
 * Created by 32918 on 2016/7/30.
 */
public class StoreRecordBean {

    private String createTime;
    //储值金额
    private String storeFee;
    //
    private String payOrder;
    //储值后金额
    private double storeMoneySum;

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getStoreFee()
    {
        return storeFee;
    }

    public void setStoreFee(String storeFee)
    {
        this.storeFee = storeFee;
    }

    public String getPayOrder()
    {
        return payOrder;
    }

    public void setPayOrder(String payOrder)
    {
        this.payOrder = payOrder;
    }

    public double getStoreMoneySum()
    {
        return storeMoneySum;
    }

    public void setStoreMoneySum(double storeMoneySum)
    {
        this.storeMoneySum = storeMoneySum;
    }
}
