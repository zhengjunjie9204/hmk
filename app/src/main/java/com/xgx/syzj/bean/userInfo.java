package com.xgx.syzj.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class UserInfo implements Serializable{
    private String phone;
    private String name;
    private String right;

    public UserInfo() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }



}
