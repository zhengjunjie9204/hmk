package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sam on 2016/3/14 14:25.
 */
public class BillGoodsDetailbean implements Parcelable{


    /**
     * customerType : 1
     * billDatetime : 1457698597000
     * sellingPrice : 3.8
     * costPrice : 0.8
     * flag : 0
     * billDetailsId : 46
     * billId : 34
     * quantity : 5
     * storeId : 15
     * productName : eggg
     * totalValue : 19
     * returnReason : 0
     * productId : 43
     */

    private int customerType;
    private long billDatetime;
    private double sellingPrice;
    private double costPrice;
    private int flag;
    private int billDetailsId;
    private int billId;
    private int quantity;
    private int storeId;
    private String productName;
    private double totalValue;
    private int returnReason;
    private int productId;

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public void setBillDatetime(long billDatetime) {
        this.billDatetime = billDatetime;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setBillDetailsId(int billDetailsId) {
        this.billDetailsId = billDetailsId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public void setReturnReason(int returnReason) {
        this.returnReason = returnReason;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCustomerType() {
        return customerType;
    }

    public long getBillDatetime() {
        return billDatetime;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public int getFlag() {
        return flag;
    }

    public int getBillDetailsId() {
        return billDetailsId;
    }

    public int getBillId() {
        return billId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getProductName() {
        return productName;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public int getReturnReason() {
        return returnReason;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.customerType);
        dest.writeLong(this.billDatetime);
        dest.writeDouble(this.sellingPrice);
        dest.writeDouble(this.costPrice);
        dest.writeInt(this.flag);
        dest.writeInt(this.billDetailsId);
        dest.writeInt(this.billId);
        dest.writeInt(this.quantity);
        dest.writeInt(this.storeId);
        dest.writeString(this.productName);
        dest.writeDouble(this.totalValue);
        dest.writeInt(this.returnReason);
        dest.writeInt(this.productId);
    }

    public BillGoodsDetailbean() {
    }

    protected BillGoodsDetailbean(Parcel in) {
        this.customerType = in.readInt();
        this.billDatetime = in.readLong();
        this.sellingPrice = in.readDouble();
        this.costPrice = in.readDouble();
        this.flag = in.readInt();
        this.billDetailsId = in.readInt();
        this.billId = in.readInt();
        this.quantity = in.readInt();
        this.storeId = in.readInt();
        this.productName = in.readString();
        this.totalValue = in.readDouble();
        this.returnReason = in.readInt();
        this.productId = in.readInt();
    }

    public static final Creator<BillGoodsDetailbean> CREATOR = new Creator<BillGoodsDetailbean>() {
        public BillGoodsDetailbean createFromParcel(Parcel source) {
            return new BillGoodsDetailbean(source);
        }

        public BillGoodsDetailbean[] newArray(int size) {
            return new BillGoodsDetailbean[size];
        }
    };
}
