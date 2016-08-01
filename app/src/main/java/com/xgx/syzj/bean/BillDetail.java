package com.xgx.syzj.bean;

/**
 * 提交结算时候的商品bean
 *
 * @author zajo
 * @created 2015年12月01日 15:19
 */
public class BillDetail {

    private int productId;//id

    private int quantity;//数量

    private double totalValue;//总价

    private double sellingPrice;//单价 sellingPrice

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
