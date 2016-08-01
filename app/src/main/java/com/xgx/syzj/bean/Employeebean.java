package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sam on 2016/3/9 12:01.
 */
public class Employeebean implements Parcelable{

    /**
     * password : 123456
     * mobilePhone : 15820259911
     * accountId : 15820259911
     * storeId : 15
     * userName : sam_k1
     * authority : 0
     * registrationDate : 1457495924000
     */

    private String password;
    private String mobilePhone;
    private String accountId;
    private int storeId;
    private String userName;
    private int authority;
    private long registrationDate;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPassword() {
        return password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getUserName() {
        return userName;
    }

    public int getAuthority() {
        return authority;
    }

    public long getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.password);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.accountId);
        dest.writeInt(this.storeId);
        dest.writeString(this.userName);
        dest.writeInt(this.authority);
        dest.writeLong(this.registrationDate);
    }

    public Employeebean() {
    }

    protected Employeebean(Parcel in) {
        this.password = in.readString();
        this.mobilePhone = in.readString();
        this.accountId = in.readString();
        this.storeId = in.readInt();
        this.userName = in.readString();
        this.authority = in.readInt();
        this.registrationDate = in.readLong();
    }

    public static final Creator<Employeebean> CREATOR = new Creator<Employeebean>() {
        public Employeebean createFromParcel(Parcel source) {
            return new Employeebean(source);
        }

        public Employeebean[] newArray(int size) {
            return new Employeebean[size];
        }
    };
}
