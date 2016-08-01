package com.xgx.syzj.bean;

/**
 * Created by sam on 2016/3/25 15:41.
 */
public class AnalySaleGrossProfit {
    private int salesGrossProfit;
    private String productName;
    private int productId;

    public int getSalesGrossProfit() {
        return salesGrossProfit;
    }

    public void setSalesGrossProfit(int salesGrossProfit) {
        this.salesGrossProfit = salesGrossProfit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
