package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

/**
 * 商品
 *
 * @author zajo
 * @created 2015年08月20日 11:33
 */
public class Goods implements Parcelable {
//            "quantity": 5,
//            "productId": 69,
//            "unitName": "件",
//            "specification": "50*50cm",
//            "storeId": 1,
//            "inputPrice": 20,
//            "productName": "AAAAAA",
//            "uid": 7,
//            "sellingPrice": 50,
//            "unitid": 1,
//            "barcode": "0000000000",
//            "brand": "宝马",
//            "categoryId": "1"

    private int productId;

    private String productName;

    private int storeId;

    private int quantity;

    private String barcode;

    private String brand;

    private String categoryId;

    private String categoryName;

    private double inputPrice;

    private double sellingPrice;

    private String image;

    private String supplier;

    private int unitid;

    private int uid;

    private String unitName;

    private String specification;

    private int revenueCount;//销售时统计数量

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getInputPrice() {
        return inputPrice;
    }

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getStrCostPrice() {
        if (this.inputPrice > 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(this.inputPrice);
        } else {
            return "0";
        }
    }

    public void setInputPrice(double inputPrice) {
        this.inputPrice = inputPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public String getStrSellingPrice() {
        if (this.sellingPrice > 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(this.sellingPrice);
        } else {
            return "0";
        }
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getRevenueCount() {
        return revenueCount;
    }

    public void setRevenueCount(int revenueCount) {
        this.revenueCount = revenueCount;
    }

    public Goods() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.productId);
        dest.writeString(this.productName);
        dest.writeInt(this.storeId);
        dest.writeInt(this.quantity);
        dest.writeString(this.barcode);
        dest.writeString(this.specification);
        dest.writeString(this.brand);
        dest.writeString(this.categoryId);
        dest.writeString(this.categoryName);
        dest.writeDouble(this.inputPrice);
        dest.writeDouble(this.sellingPrice);
        dest.writeString(this.image);
        dest.writeString(this.supplier);
        dest.writeInt(this.uid);
        dest.writeInt(this.revenueCount);
    }

    protected Goods(Parcel in) {
        this.productId = in.readInt();
        this.productName = in.readString();
        this.storeId = in.readInt();
        this.quantity = in.readInt();
        this.barcode = in.readString();
        this.specification = in.readString();
        this.brand=in.readString();
        this.categoryId = in.readString();
        this.categoryName = in.readString();
        this.inputPrice = in.readDouble();
        this.sellingPrice = in.readDouble();
        this.image = in.readString();
        this.supplier = in.readString();
        this.uid=in.readInt();
        this.revenueCount = in.readInt();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        public Goods createFromParcel(Parcel source) {
            return new Goods(source);
        }

        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };
}
