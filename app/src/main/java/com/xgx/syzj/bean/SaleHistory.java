package com.xgx.syzj.bean;

import java.text.DecimalFormat;

/**
 * @author zajo
 * @created 2015年11月24日 16:16
 */
public class SaleHistory {

    private int id;

    private String productId;

    private String productName;

    private int productCount;

    private boolean member;

    private double sellMoney;

    public double getSellMoney() {
        return sellMoney;
    }

    public void setSellMoney(double sellMoney) {
        this.sellMoney = sellMoney;
    }

    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getStrSellMoney() {
        if (this.sellMoney > 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(this.sellMoney);
        } else {
            return "0";
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }
}
