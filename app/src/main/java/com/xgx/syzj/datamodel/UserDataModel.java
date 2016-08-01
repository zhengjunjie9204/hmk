package com.xgx.syzj.datamodel;

import com.xgx.syzj.app.Api;

/**
 * @author zajo
 * @created 2015年10月14日 10:05
 */
public class UserDataModel extends BaseDataModel {

    public static final byte LOGIN_SUCCESS = 0x10;
    public static final byte REGISTER_SUCCESS = 0x11;
    public static final byte REGISTER_STAFF_SUCCESS = 0x12;

    public static void loginByToken(String token){
        code = LOGIN_SUCCESS;
        Api.loginByToken(token,listener);
    }

    public static void login(String username, String password){
        code = LOGIN_SUCCESS;
        Api.login(username, password, listener);
    }

    public static void register(String phoneStr, String pswStr, String codeStr){
        code = REGISTER_SUCCESS;
        Api.register(phoneStr, pswStr, codeStr, listener);
    }

    public static void registerStaff(String phoneStr, String pswStr, String nameStr, int authority, int storeId){
        code = REGISTER_STAFF_SUCCESS;
        Api.registerStaff(phoneStr, pswStr, nameStr, authority, storeId, listener);
    }
}
