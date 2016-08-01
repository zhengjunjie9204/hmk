package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sam on 2016/3/16 16:50.
 */
public class BillItemDetailBean implements Parcelable{

    /**
     * customerType : 1
     * billDatetime : 1458023665000
     * associatorId : 21
     * associatorName : 萨摩
     * sellingPrice : 98
     * costPrice : 69
     * flag : 0
     * billDetailsId : 63
     * billId : 41
     * quantity : 1
     * storeId : 18
     * productName : 茶壶
     * totalValue : 98
     * returnReason : 0
     * productId : 46
     */

    private int customerType;
    private long billDatetime;
    private int associatorId;
    private String associatorName;
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

    public void setAssociatorId(int associatorId) {
        this.associatorId = associatorId;
    }

    public void setAssociatorName(String associatorName) {
        this.associatorName = associatorName;
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

    public int getAssociatorId() {
        return associatorId;
    }

    public String getAssociatorName() {
        return associatorName;
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
        dest.writeInt(this.associatorId);
        dest.writeString(this.associatorName);
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

    public BillItemDetailBean() {
    }

    protected BillItemDetailBean(Parcel in) {
        this.customerType = in.readInt();
        this.billDatetime = in.readLong();
        this.associatorId = in.readInt();
        this.associatorName = in.readString();
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

    public static final Creator<BillItemDetailBean> CREATOR = new Creator<BillItemDetailBean>() {
        public BillItemDetailBean createFromParcel(Parcel source) {
            return new BillItemDetailBean(source);
        }

        public BillItemDetailBean[] newArray(int size) {
            return new BillItemDetailBean[size];
        }
    };
}
