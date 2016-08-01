package com.xgx.syzj.bean;

import java.io.Serializable;

/**
 * Created by sam on 2016/3/17 21:02.
 */
public class ShopMessage implements Serializable {
    //店铺名字
    private String shopName;

    //店铺电话
    private String shopPhone;

    //店铺地址
    private String shopAddr;

    //店铺类型
    private String shopType;

    //店铺Id
    private int storeId;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
