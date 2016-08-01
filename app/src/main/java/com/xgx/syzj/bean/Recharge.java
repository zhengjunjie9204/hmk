package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 充值记录
 *
 * @author zajo
 * @created 2015年08月31日 16:43
 */
public class Recharge implements Parcelable {


    /**
     * accountId : 13431024081
     * rechargeType : 1
     * rechargeDatetime : 1446188723000
     * description : 梵蒂冈梵蒂冈
     * rechargeAmount : 5000
     * associatorId : 30
     * giftAmount : 50
     * storeId : 13
     * rechargeId : 8
     */

    private String accountId;
    private int rechargeType;
    private long rechargeDatetime;
    private String description;
    private int rechargeAmount;
    private int associatorId;
    private int giftAmount;
    private int storeId;
    private int rechargeId;

    private double afterRechargeCount;
    private double afterRechargeValue;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(int rechargeType) {
        this.rechargeType = rechargeType;
    }

    public long getRechargeDatetime() {
        return rechargeDatetime;
    }

    public void setRechargeDatetime(long rechargeDatetime) {
        this.rechargeDatetime = rechargeDatetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(int rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public int getAssociatorId() {
        return associatorId;
    }

    public void setAssociatorId(int associatorId) {
        this.associatorId = associatorId;
    }

    public int getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(int giftAmount) {
        this.giftAmount = giftAmount;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(int rechargeId) {
        this.rechargeId = rechargeId;
    }

    public double getAfterRechargeCount() {
        return afterRechargeCount;
    }

    public void setAfterRechargeCount(double afterRechargeCount) {
        this.afterRechargeCount = afterRechargeCount;
    }

    public double getAfterRechargeValue() {
        return afterRechargeValue;
    }

    public void setAfterRechargeValue(double afterRechargeValue) {
        this.afterRechargeValue = afterRechargeValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountId);
        dest.writeInt(this.rechargeType);
        dest.writeLong(this.rechargeDatetime);
        dest.writeString(this.description);
        dest.writeInt(this.rechargeAmount);
        dest.writeInt(this.associatorId);
        dest.writeInt(this.giftAmount);
        dest.writeInt(this.storeId);
        dest.writeInt(this.rechargeId);
        dest.writeDouble(this.afterRechargeCount);
        dest.writeDouble(this.afterRechargeValue);
    }

    public Recharge() {
    }

    protected Recharge(Parcel in) {
        this.accountId = in.readString();
        this.rechargeType = in.readInt();
        this.rechargeDatetime = in.readLong();
        this.description = in.readString();
        this.rechargeAmount = in.readInt();
        this.associatorId = in.readInt();
        this.giftAmount = in.readInt();
        this.storeId = in.readInt();
        this.rechargeId = in.readInt();
        this.afterRechargeCount = in.readDouble();
        this.afterRechargeValue = in.readDouble();
    }

    public static final Creator<Recharge> CREATOR = new Creator<Recharge>() {
        public Recharge createFromParcel(Parcel source) {
            return new Recharge(source);
        }

        public Recharge[] newArray(int size) {
            return new Recharge[size];
        }
    };
}
