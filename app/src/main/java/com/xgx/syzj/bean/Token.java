package com.xgx.syzj.bean;

import java.io.Serializable;

/**
 * @author zajo
 * @created 2015年08月27日 10:29
 */
public class Token implements Serializable {

    //用户token
    private String accessToken;

    //刷新token
    private String refreshToken;

    //用户id
    private String userId;

    //创建时间
    private long createTime;

    //令牌类型
    private String grantType;

    //用户随机生成的 key
    private String userKey;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
