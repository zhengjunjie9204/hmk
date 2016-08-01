package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sam on 2016/3/18 16:02.
 */
public class GetDataInfo implements Parcelable {
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.info);
    }

    public GetDataInfo() {
    }

    protected GetDataInfo(Parcel in) {
        this.info = in.readString();
    }

    public static final Creator<GetDataInfo> CREATOR = new Creator<GetDataInfo>() {
        public GetDataInfo createFromParcel(Parcel source) {
            return new GetDataInfo(source);
        }

        public GetDataInfo[] newArray(int size) {
            return new GetDataInfo[size];
        }
    };
}
