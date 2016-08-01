package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sam on 2016/3/18 14:56.
 */
public class MoneyRecordCard implements Parcelable{


    /**
     * cardId : 10000019
     * cumulativeUsedCount : 0
     * cumulativeRechargeCount : 568642
     * cardCount : 568642
     * cardValue : 47357
     * cumulativeUsedValue : 0
     * cardTypeId : 20
     * cumulativeRechargeAmount : 659698
     * expDate : 1458023531000
     * activationDate : 1458023531000
     * storeId : 18
     * cardIntegral : -13443
     * cardPassword :
     */


    private int cardId;
    private int cumulativeUsedCount;
    private int cumulativeRechargeCount;
    private int cardCount;
    private double cardValue;
    private double cumulativeUsedValue;
    private int cardTypeId;
    private double cumulativeRechargeAmount;
    private long expDate;
    private long activationDate;
    private int storeId;
    private int cardIntegral;
    private String cardPassword;

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setCumulativeUsedCount(int cumulativeUsedCount) {
        this.cumulativeUsedCount = cumulativeUsedCount;
    }

    public void setCumulativeRechargeCount(int cumulativeRechargeCount) {
        this.cumulativeRechargeCount = cumulativeRechargeCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public void setCardValue(double cardValue) {
        this.cardValue = cardValue;
    }

    public void setCumulativeUsedValue(double cumulativeUsedValue) {
        this.cumulativeUsedValue = cumulativeUsedValue;
    }

    public void setCardTypeId(int cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public void setCumulativeRechargeAmount(double cumulativeRechargeAmount) {
        this.cumulativeRechargeAmount = cumulativeRechargeAmount;
    }

    public void setExpDate(long expDate) {
        this.expDate = expDate;
    }

    public void setActivationDate(long activationDate) {
        this.activationDate = activationDate;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setCardIntegral(int cardIntegral) {
        this.cardIntegral = cardIntegral;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    public int getCardId() {
        return cardId;
    }

    public int getCumulativeUsedCount() {
        return cumulativeUsedCount;
    }

    public int getCumulativeRechargeCount() {
        return cumulativeRechargeCount;
    }

    public int getCardCount() {
        return cardCount;
    }

    public double getCardValue() {
        return cardValue;
    }

    public double getCumulativeUsedValue() {
        return cumulativeUsedValue;
    }

    public int getCardTypeId() {
        return cardTypeId;
    }

    public double getCumulativeRechargeAmount() {
        return cumulativeRechargeAmount;
    }

    public long getExpDate() {
        return expDate;
    }

    public long getActivationDate() {
        return activationDate;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public String getCardPassword() {
        return cardPassword;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cardId);
        dest.writeInt(this.cumulativeUsedCount);
        dest.writeInt(this.cumulativeRechargeCount);
        dest.writeInt(this.cardCount);
        dest.writeDouble(this.cardValue);
        dest.writeDouble(this.cumulativeUsedValue);
        dest.writeInt(this.cardTypeId);
        dest.writeDouble(this.cumulativeRechargeAmount);
        dest.writeLong(this.expDate);
        dest.writeLong(this.activationDate);
        dest.writeInt(this.storeId);
        dest.writeInt(this.cardIntegral);
        dest.writeString(this.cardPassword);
    }

    public MoneyRecordCard() {
    }

    protected MoneyRecordCard(Parcel in) {
        this.cardId = in.readInt();
        this.cumulativeUsedCount = in.readInt();
        this.cumulativeRechargeCount = in.readInt();
        this.cardCount = in.readInt();
        this.cardValue = in.readDouble();
        this.cumulativeUsedValue = in.readDouble();
        this.cardTypeId = in.readInt();
        this.cumulativeRechargeAmount = in.readDouble();
        this.expDate = in.readLong();
        this.activationDate = in.readLong();
        this.storeId = in.readInt();
        this.cardIntegral = in.readInt();
        this.cardPassword = in.readString();
    }

    public static final Creator<MoneyRecordCard> CREATOR = new Creator<MoneyRecordCard>() {
        public MoneyRecordCard createFromParcel(Parcel source) {
            return new MoneyRecordCard(source);
        }

        public MoneyRecordCard[] newArray(int size) {
            return new MoneyRecordCard[size];
        }
    };
}
