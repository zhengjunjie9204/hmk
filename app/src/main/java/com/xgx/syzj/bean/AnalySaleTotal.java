package com.xgx.syzj.bean;

/**
 * Created by sam on 2016/3/25 12:00.
 */
public class AnalySaleTotal {

    /**
     * productName : 茶壶
     * productId : 46
     * salesTotal : 3626
     */

    private String productName;
    private int productId;
    private int salesTotal;

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setSalesTotal(int salesTotal) {
        this.salesTotal = salesTotal;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public int getSalesTotal() {
        return salesTotal;
    }
}
