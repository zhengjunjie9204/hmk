package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品类别
 *
 * @author zajo
 * @created 2015年08月20日 15:54
 */
public class GoodsCategory implements Parcelable {

    private int categoryId;

    private String categoryName;

    private int parentId;

    private int storeId;

    private int classLayer;

    private int sortId;

    private String classList;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getClassLayer() {
        return classLayer;
    }

    public void setClassLayer(int classLayer) {
        this.classLayer = classLayer;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getClassList() {
        return classList;
    }

    public void setClassList(String classList) {
        this.classList = classList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.categoryId);
        dest.writeString(this.categoryName);
        dest.writeInt(this.parentId);
        dest.writeInt(this.storeId);
        dest.writeInt(this.classLayer);
        dest.writeInt(this.sortId);
        dest.writeString(this.classList);
    }

    public GoodsCategory() {
    }

    protected GoodsCategory(Parcel in) {
        this.categoryId = in.readInt();
        this.categoryName = in.readString();
        this.parentId = in.readInt();
        this.storeId = in.readInt();
        this.classLayer = in.readInt();
        this.sortId = in.readInt();
        this.classList = in.readString();
    }

    public static final Creator<GoodsCategory> CREATOR = new Creator<GoodsCategory>() {
        public GoodsCategory createFromParcel(Parcel source) {
            return new GoodsCategory(source);
        }

        public GoodsCategory[] newArray(int size) {
            return new GoodsCategory[size];
        }
    };

    public GoodsCategory(String name){
        this.categoryName = name;
    }
}
