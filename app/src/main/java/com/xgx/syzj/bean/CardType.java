package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 会员卡
 *
 * @author zajo
 * @created 2015年10月26日 11:20
 */
public class CardType implements Parcelable {

    private int cardTypeId;

    private String cardTypeName;

    private boolean supportIntegral;//积分

    private boolean supportValue;//储值

    private boolean supportCount;//计次

    private boolean needPassord;//是否需要密码

    private String storeId;

    private String discount;

    private String integralRatio;

    public int getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(int cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public boolean isSupportIntegral() {
        return supportIntegral;
    }

    public void setSupportIntegral(boolean supportIntegral) {
        this.supportIntegral = supportIntegral;
    }

    public boolean isSupportValue() {
        return supportValue;
    }

    public void setSupportValue(boolean supportValue) {
        this.supportValue = supportValue;
    }

    public boolean isSupportCount() {
        return supportCount;
    }

    public void setSupportCount(boolean supportCount) {
        this.supportCount = supportCount;
    }

    public boolean isNeedPassord() {
        return needPassord;
    }

    public void setNeedPassord(boolean needPassord) {
        this.needPassord = needPassord;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getIntegralRatio() {
        return integralRatio;
    }

    public void setIntegralRatio(String integralRatio) {
        this.integralRatio = integralRatio;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cardTypeId);
        dest.writeString(this.cardTypeName);
        dest.writeByte(supportIntegral ? (byte) 1 : (byte) 0);
        dest.writeByte(supportValue ? (byte) 1 : (byte) 0);
        dest.writeByte(supportCount ? (byte) 1 : (byte) 0);
        dest.writeByte(needPassord ? (byte) 1 : (byte) 0);
        dest.writeString(this.storeId);
        dest.writeString(this.discount);
        dest.writeString(this.integralRatio);
    }

    public CardType() {
    }

    protected CardType(Parcel in) {
        this.cardTypeId = in.readInt();
        this.cardTypeName = in.readString();
        this.supportIntegral = in.readByte() != 0;
        this.supportValue = in.readByte() != 0;
        this.supportCount = in.readByte() != 0;
        this.needPassord = in.readByte() != 0;
        this.storeId = in.readString();
        this.discount = in.readString();
        this.integralRatio = in.readString();
    }

    public static final Parcelable.Creator<CardType> CREATOR = new Parcelable.Creator<CardType>() {
        public CardType createFromParcel(Parcel source) {
            return new CardType(source);
        }

        public CardType[] newArray(int size) {
            return new CardType[size];
        }
    };
}
