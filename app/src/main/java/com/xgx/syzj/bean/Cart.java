package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 挂单实体
 *
 * @author zajo
 * @created 2015年11月27日 15:47
 */
public class Cart implements Parcelable {

    private int cartId;

    private String cartName;

    private long cartDatetime;

    private String accountId;

    private String description;

    private int associatorId;

    private String cardId;

    private String associatorName;

    private int cardIntegral;

    private double cardValue;

    private int storeId;

    private List<GoodsCart> cartDetails;

    private double totalValue;

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public long getCartDatetime() {
        return cartDatetime;
    }

    public void setCartDatetime(long cartDatetime) {
        this.cartDatetime = cartDatetime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAssociatorId() {
        return associatorId;
    }

    public void setAssociatorId(int associatorId) {
        this.associatorId = associatorId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getAssociatorName() {
        return associatorName;
    }

    public void setAssociatorName(String associatorName) {
        this.associatorName = associatorName;
    }

    public List<GoodsCart> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<GoodsCart> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public double getCardValue() {
        return cardValue;
    }

    public void setCardValue(double cardValue) {
        this.cardValue = cardValue;
    }

    public String getStrTotalValue() {
        if (this.totalValue > 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(this.totalValue);
        } else {
            return "0";
        }
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public void setCardIntegral(int cardIntegral) {
        this.cardIntegral = cardIntegral;
    }

    public Cart() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cartId);
        dest.writeString(this.cartName);
        dest.writeLong(this.cartDatetime);
        dest.writeString(this.accountId);
        dest.writeString(this.description);
        dest.writeInt(this.associatorId);
        dest.writeString(this.cardId);
        dest.writeString(this.associatorName);
        dest.writeInt(this.cardIntegral);
        dest.writeDouble(this.cardValue);
        dest.writeInt(this.storeId);
        dest.writeList(this.cartDetails);
        dest.writeDouble(this.totalValue);
    }

    protected Cart(Parcel in) {
        this.cartId = in.readInt();
        this.cartName = in.readString();
        this.cartDatetime = in.readLong();
        this.accountId = in.readString();
        this.description = in.readString();
        this.associatorId = in.readInt();
        this.cardId = in.readString();
        this.associatorName = in.readString();
        this.cardIntegral = in.readInt();
        this.cardValue = in.readDouble();
        this.storeId = in.readInt();
        this.cartDetails = new ArrayList<GoodsCart>();
        in.readList(this.cartDetails, List.class.getClassLoader());
        this.totalValue = in.readDouble();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
