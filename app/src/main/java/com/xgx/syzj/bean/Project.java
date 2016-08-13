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
    private String type;

    public Project(){}

    public Project(int id, String name, double price, double laborTime, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.laborTime = laborTime;
        this.type = type;
    }

    protected Project(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        laborTime = in.readDouble();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeDouble(laborTime);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

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
}
