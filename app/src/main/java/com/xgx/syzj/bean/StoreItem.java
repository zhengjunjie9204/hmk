package com.xgx.syzj.bean;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class StoreItem {
    private String name;
    private long Price;
    private int id;
    private String LaborTime;

    public String getLaborTime() {
        return LaborTime;
    }

    public void setLaborTime(String laborTime) {
        LaborTime = laborTime;
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }
}
