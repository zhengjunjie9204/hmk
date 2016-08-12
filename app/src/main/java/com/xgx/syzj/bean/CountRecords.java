package com.xgx.syzj.bean;

/**
 * Created by 32918 on 2016/8/11.
 */
public class CountRecords {
    /**
     * amount : 3
     * payType : 3
     * payTime : 2016-04-11T19:32:29
     * name : 普洗
     */

    private int amount;
    private int payType;
    private String payTime;
    private String name;

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public int getPayType()
    {
        return payType;
    }

    public void setPayType(int payType)
    {
        this.payType = payType;
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
}
