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
//             productId": 101,
//            "unitName": "件",
//            "specification": "",
//            "inputPrice": 58,
//            "productName": "辉腾",
//            "sellingPrice": 198,
//            "pic1": "http://qcmr.junrenwl.com/qcmr/upload/enterprice/product/201681720254832.jpg",
//            "unitid": 1,
//            "pic2": "http://qcmr.junrenwl.com/qcmr/upload/enterprice/product/201681720251621.jpg",
//            "barcode": "101",
//            "brand": "",
//            "pic3": "http://qcmr.junrenwl.com/qcmr/upload/enterprice/product/201681720251220.jpg",


//            "categoryId": "1"
//

    private int productId;
    private String productName;
    private int storeId;
    private int vip_price;
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
    private int quantity;
    private int revenueCount;//销售时统计数量
    private int count;
    private double totalPrice;
    private String pic1;
    private String pic2;
    private String pic3;
    public Goods() {
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getVip_price() {
        return vip_price;
    }
    public void setVip_price(int vip_price) {
        this.vip_price = vip_price;
    }

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
    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
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


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.productId);
        dest.writeString(this.productName);
        dest.writeInt(this.storeId);
        dest.writeInt(this.vip_price);
        dest.writeString(this.barcode);
        dest.writeString(this.brand);
        dest.writeString(this.categoryId);
        dest.writeString(this.categoryName);
        dest.writeDouble(this.inputPrice);
        dest.writeDouble(this.sellingPrice);
        dest.writeString(this.image);
        dest.writeString(this.supplier);
        dest.writeInt(this.unitid);
        dest.writeInt(this.uid);
        dest.writeString(this.unitName);
        dest.writeString(this.specification);
        dest.writeInt(this.quantity);
        dest.writeInt(this.revenueCount);
        dest.writeInt(this.count);
        dest.writeDouble(this.totalPrice);
        dest.writeString(this.pic1);
        dest.writeString(this.pic2);
        dest.writeString(this.pic3);
    }

    protected Goods(Parcel in)
    {
        this.productId = in.readInt();
        this.productName = in.readString();
        this.storeId = in.readInt();
        this.vip_price = in.readInt();
        this.barcode = in.readString();
        this.brand = in.readString();
        this.categoryId = in.readString();
        this.categoryName = in.readString();
        this.inputPrice = in.readDouble();
        this.sellingPrice = in.readDouble();
        this.image = in.readString();
        this.supplier = in.readString();
        this.unitid = in.readInt();
        this.uid = in.readInt();
        this.unitName = in.readString();
        this.specification = in.readString();
        this.quantity = in.readInt();
        this.revenueCount = in.readInt();
        this.count = in.readInt();
        this.totalPrice = in.readDouble();
        this.pic1=in.readString();
        this.pic2=in.readString();
        this.pic3=in.readString();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel source)
        {
            return new Goods(source);
        }

        @Override
        public Goods[] newArray(int size)
        {
            return new Goods[size];
        }
    };
}
