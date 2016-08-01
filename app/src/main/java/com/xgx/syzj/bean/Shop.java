package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 店铺
 *
 * @author zajo
 * @created 2015年08月19日 15:25
 */
public class Shop implements Serializable {

    private User userInfo;

    private ShopMessage shopMessage;

    private Token tokenInfo;

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public Token getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(Token tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public ShopMessage getShopMessage() {
        return shopMessage;
    }

    public void setShopMessage(ShopMessage shopMessage) {
        this.shopMessage = shopMessage;
    }
}
