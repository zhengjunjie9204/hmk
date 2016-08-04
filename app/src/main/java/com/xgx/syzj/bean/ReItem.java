package com.xgx.syzj.bean;

import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class ReItem {
    private String name;
    private long money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public ReItem(String name, long money) {

        this.name = name;
        this.money = money;
    }
    @Override
    public String toString() {
        return "ReItem{" +
                "name=" + name +
                ", money=" + money +
                '}';
    }
}
