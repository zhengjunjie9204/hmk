package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品
 *
 * @author zajo
 * @created 2015年08月20日 11:33
 */
public class Goods implements Parcelable {

    private int productId;
    private String productName;
    private int storeId;



    private int vipPrice;
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
    private String unit;
    private String specification;
    private int quantity;
    private int revenueCount;//销售时统计数量
    private int count;
    private double totalPrice;
    private List<ImagesBean> images;
    private boolean isFinish=false;

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public Goods() {
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public List<ImagesBean> getImages()
    {
        return images;
    }

    public void setImages(List<ImagesBean> images)
    {
        this.images = images;
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
    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
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

    public static class ImagesBean implements Parcelable{
        int index;
        String image;

        public int getIndex()
        {
            return index;
        }

        public void setIndex(int index)
        {
            this.index = index;
        }

        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.index);
            dest.writeString(this.image);
        }

        public ImagesBean() {
        }

        protected ImagesBean(Parcel in) {
            this.index = in.readInt();
            this.image = in.readString();
        }

        public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
            @Override
            public ImagesBean createFromParcel(Parcel source) {
                return new ImagesBean(source);
            }

            @Override
            public ImagesBean[] newArray(int size) {
                return new ImagesBean[size];
            }
        };
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
        dest.writeInt(this.vipPrice);
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
        dest.writeString(this.unit);
        dest.writeString(this.specification);
        dest.writeInt(this.quantity);
        dest.writeInt(this.revenueCount);
        dest.writeInt(this.count);
        dest.writeDouble(this.totalPrice);
        dest.writeList(this.images);
    }

    protected Goods(Parcel in)
    {
        this.productId = in.readInt();
        this.productName = in.readString();
        this.storeId = in.readInt();
        this.vipPrice = in.readInt();
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
        this.unit = in.readString();
        this.specification = in.readString();
        this.quantity = in.readInt();
        this.revenueCount = in.readInt();
        this.count = in.readInt();
        this.totalPrice = in.readDouble();
        this.images = new ArrayList<ImagesBean>();
        in.readList(this.images, ImagesBean.class.getClassLoader());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goods goods = (Goods) o;

        if (productId != goods.productId) return false;
        if (storeId != goods.storeId) return false;
        return images != null ? images.equals(goods.images) : goods.images == null;

    }
}
