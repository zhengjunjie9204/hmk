package com.xgx.syzj.datamodel;

import com.android.volley.Response;
import com.xgx.syzj.app.Api;

/**
 * @author zajo
 * @created 2015年10月14日 10:05
 */
public class UserDataModel extends BaseDataModel {

    public static final byte LOGIN_SUCCESS = 0x10;
    public static final byte REGISTER_SUCCESS = 0x11;
    public static final byte REGISTER_STAFF_SUCCESS = 0x12;
    public static final byte GET_CODENUM=0x14;
    public static final byte CHECK_CODENUM=0x15;
    public static final byte FORGET_PASSWORD=0x16;
    public static final byte CHECK_VERSION_SUCCESS=0x17;
    public static final byte DOWNLOAD_APK_SUCCESS=0x18;

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


    public static void getCode(String inputValue){
        code = GET_CODENUM;
        Api.getCode(inputValue, listener);
    }

    public static void checkCodenum(String phone,String codenum){
        code=CHECK_CODENUM;
        Api.checkCodenum(phone, codenum, listener);
    }

    public static void forgetPsw(String mobilephone,String codenum, String newpassword){
        code=FORGET_PASSWORD;
        Api.forgetPassWord(mobilephone, codenum, newpassword, listener);
    }

    public static void checkVersion(){
        code=CHECK_VERSION_SUCCESS;
        Api.checkVersion(listener);
    }
    public static void downloadApk(String url, Response.ProgressListener pListener){
        code=DOWNLOAD_APK_SUCCESS;
        Api.downLoadAPK(url,listener,pListener);
    }
}
