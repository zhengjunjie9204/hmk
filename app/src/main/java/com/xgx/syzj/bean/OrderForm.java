package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 微订单
 *
 * @author zajo
 * @created 2015年11月20日 10:47
 */
public class OrderForm implements Parcelable {

    //到达，未到达，作废
    public enum STATUS {
        REACH, UNREACH, INVALID
    }

    private int id;
    //订单id
    private String orderId;
    //会员名
    private String userName;
    //用户手机号
    private String userPhone;
    //订单时间
    private long orderTime;
    //预约时间
    private long reservationTime;
    //商品
    private List<Goods> products;

    private STATUS status;

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public long getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(long reservationTime) {
        this.reservationTime = reservationTime;
    }

    public List<Goods> getProducts() {
        return products;
    }

    public void setProducts(List<Goods> products) {
        this.products = products;
    }

    public OrderForm() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.orderId);
        dest.writeString(this.userName);
        dest.writeString(this.userPhone);
        dest.writeLong(this.orderTime);
        dest.writeLong(this.reservationTime);
        dest.writeTypedList(products);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    }

    protected OrderForm(Parcel in) {
        this.id = in.readInt();
        this.orderId = in.readString();
        this.userName = in.readString();
        this.userPhone = in.readString();
        this.orderTime = in.readLong();
        this.reservationTime = in.readLong();
        this.products = in.createTypedArrayList(Goods.CREATOR);
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : STATUS.values()[tmpStatus];
    }

    public static final Creator<OrderForm> CREATOR = new Creator<OrderForm>() {
        public OrderForm createFromParcel(Parcel source) {
            return new OrderForm(source);
        }

        public OrderForm[] newArray(int size) {
            return new OrderForm[size];
        }
    };
}
