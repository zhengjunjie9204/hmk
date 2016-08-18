package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by and on 2016/6/22.
 */
public class Project implements Parcelable{
    private int id;
    private String name;
    private double price;
    private double laborTime;
    private double totalPrice;
    private String status;
    private int payType;
    private String type;

    public Project(){}

    public int getPayType()
    {
        return payType;
    }

    public void setPayType(int payType)
    {
        this.payType = payType;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLaborTime() {
        return laborTime;
    }

    public void setLaborTime(double laborTime) {
        this.laborTime = laborTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.price);
        dest.writeDouble(this.laborTime);
        dest.writeDouble(this.totalPrice);
        dest.writeString(this.status);
        dest.writeInt(this.payType);
        dest.writeString(this.type);
    }

    protected Project(Parcel in)
    {
        this.id = in.readInt();
        this.name = in.readString();
        this.price = in.readDouble();
        this.laborTime = in.readDouble();
        this.totalPrice = in.readDouble();
        this.status = in.readString();
        this.payType = in.readInt();
        this.type = in.readString();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel source)
        {
            return new Project(source);
        }

        @Override
        public Project[] newArray(int size)
        {
            return new Project[size];
        }
    };
}
