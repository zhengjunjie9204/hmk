package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zajo
 * @created 2015年10月30日 16:06
 */
public class Card implements Parcelable {


    /**
     * cardId : 5132
     * cardCount : 0
     * cardValue : 6885.0
     * expDate : 1446188076000
     * cardTypeId : 13
     * activationDate : 1446188076000
     * cardPassword :
     * cardIntegral : 0
     * storeId : 13
     */

    private String cardId;
    private int cardCount;
    private double cardValue;
    private long expDate;
    private int cardTypeId;
    private long activationDate;
    private String cardPassword;
    private int cardIntegral;
    private int storeId;

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public void setCardValue(double cardValue) {
        this.cardValue = cardValue;
    }

    public void setExpDate(long expDate) {
        this.expDate = expDate;
    }

    public void setCardTypeId(int cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public void setActivationDate(long activationDate) {
        this.activationDate = activationDate;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    public void setCardIntegral(int cardIntegral) {
        this.cardIntegral = cardIntegral;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getCardId() {
        return cardId;
    }

    public int getCardCount() {
        return cardCount;
    }

    public double getCardValue() {
        return cardValue;
    }

    public long getExpDate() {
        return expDate;
    }

    public int getCardTypeId() {
        return cardTypeId;
    }

    public long getActivationDate() {
        return activationDate;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public int getStoreId() {
        return storeId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cardId);
        dest.writeInt(this.cardCount);
        dest.writeDouble(this.cardValue);
        dest.writeLong(this.expDate);
        dest.writeInt(this.cardTypeId);
        dest.writeLong(this.activationDate);
        dest.writeString(this.cardPassword);
        dest.writeInt(this.cardIntegral);
        dest.writeInt(this.storeId);
    }

    public Card() {
    }

    protected Card(Parcel in) {
        this.cardId = in.readString();
        this.cardCount = in.readInt();
        this.cardValue = in.readDouble();
        this.expDate = in.readLong();
        this.cardTypeId = in.readInt();
        this.activationDate = in.readLong();
        this.cardPassword = in.readString();
        this.cardIntegral = in.readInt();
        this.storeId = in.readInt();
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
