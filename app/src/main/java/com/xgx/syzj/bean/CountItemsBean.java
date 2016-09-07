package com.xgx.syzj.bean;

import java.io.Serializable;

/**
 * Created by 32918 on 2016/8/10.
 */
public class CountItemsBean implements Serializable {
    private int itemId;
    private String name;
    private int count;
    private double labourTime;
    private long price;

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public double getLabourTime() {
        return labourTime;
    }

    public void setLabourTime(double labourTime) {
        this.labourTime = labourTime;
    }

    public CountItemsBean(){}

    public CountItemsBean(int itemId,String name,int count){
        this.itemId = itemId;
        this.name = name;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }
}
