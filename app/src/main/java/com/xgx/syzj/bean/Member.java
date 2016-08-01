package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 会员
 */
public class Member implements Parcelable {

    private int id;
    private String carNumber;
    private String carType;
    private String cardNumber;
    private double consumeRecord;
    private String name;
    private String phone;
    private String status;
    private double storedMoney;
    private String userMetering;

    public Member(){}

    protected Member(Parcel in) {
        id = in.readInt();
        carNumber = in.readString();
        carType = in.readString();
        cardNumber = in.readString();
        consumeRecord = in.readDouble();
        name = in.readString();
        phone = in.readString();
        status = in.readString();
        storedMoney = in.readDouble();
        userMetering = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(carNumber);
        dest.writeString(carType);
        dest.writeString(cardNumber);
        dest.writeDouble(consumeRecord);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(status);
        dest.writeDouble(storedMoney);
        dest.writeString(userMetering);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public double getConsumeRecord() {
        return consumeRecord;
    }

    public void setConsumeRecord(double consumeRecord) {
        this.consumeRecord = consumeRecord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getStoredMoney() {
        return storedMoney;
    }

    public void setStoredMoney(double storedMoney) {
        this.storedMoney = storedMoney;
    }

    public String getUserMetering() {
        return userMetering;
    }

    public void setUserMetering(String userMetering) {
        this.userMetering = userMetering;
    }
}
