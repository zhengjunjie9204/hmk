package com.xgx.syzj.bean;

import java.util.List;

/**
 * Created by 32918 on 2016/7/30.
 */
public class StoreRecord {
    //储值卡累计消费
    private double consumeByChuzhi;
    //可用金额
    private int storedMoney;

    private int length;

    private List<StoreRecordBean> records;

    public double getConsumeByChuzhi()
    {
        return consumeByChuzhi;
    }

    public void setConsumeByChuzhi(double consumeByChuzhi)
    {
        this.consumeByChuzhi = consumeByChuzhi;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public int getStoredMoney()
    {
        return storedMoney;
    }

    public void setStoredMoney(int storedMoney)
    {
        this.storedMoney = storedMoney;
    }

    public List<StoreRecordBean> getRecords()
    {
        return records;
    }

    public void setRecords(List<StoreRecordBean> records)
    {
        this.records = records;
    }

}
