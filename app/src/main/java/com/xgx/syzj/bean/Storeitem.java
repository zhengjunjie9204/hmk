package com.xgx.syzj.bean;

import java.io.Serializable;

/**
 * Created by 32918 on 2016/8/6.
 */
public class Storeitem implements Serializable {
    /**
     * uid : 2
     * price : 80
     * laborTime : 1
     * name : 精洗
     * id : 10
     * type : 洗车
     */
    private int uid;
    private int price;
    private int laborTime;
    private String name;
    private int id;
    private String type;

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getLaborTime()
    {
        return laborTime;
    }

    public void setLaborTime(int laborTime)
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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
