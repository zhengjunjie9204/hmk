package com.xgx.syzj.utils;

import com.xgx.syzj.bean.User;

/**
 * 系统一些缓存存储
 */
public class CacheUtil {

    private static CacheUtil mInstance = null;

    private User mUser;

    public CacheUtil(){
        mUser=new User();
    }

    public static CacheUtil getmInstance(){
        if (mInstance == null){
            mInstance = new CacheUtil();
        }
        return mInstance;
    }

    public User getUser(){
        return mUser;
    }

    public void setUser(User user){
        this.mUser = user;
    }
}
