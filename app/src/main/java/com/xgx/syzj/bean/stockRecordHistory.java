package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**出入库历史
 * Created by Administrator on 2016/7/28 0028.
 */
public class stockRecordHistory implements Parcelable {
    private String flag ;
    private int stock_count;
    private String createTime;
    private String status;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getStock_count() {
        return stock_count;
    }

    public void setStock_count(int stock_count) {
        this.stock_count = stock_count;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public stockRecordHistory() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.flag);
        dest.writeInt(this.stock_count);
        dest.writeString(this.createTime);
        dest.writeString(this.status);
    }
    protected stockRecordHistory(Parcel in){
        this.flag=in.readString();
        this.stock_count=in.readInt();
        this.createTime=in.readString();
        this.status=in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<stockRecordHistory> CREATOR = new Creator<stockRecordHistory>() {
        @Override
        public stockRecordHistory createFromParcel(Parcel in) {
            return new stockRecordHistory(in);
        }

        @Override
        public stockRecordHistory[] newArray(int size) {
            return new stockRecordHistory[size];
        }
    };
}
