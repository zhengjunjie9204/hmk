package com.xgx.syzj.bean;

/**
 * @author zajo
 * @created 2015年08月06日 15:11
 */
public class Result {
    //"result":{"message":"新增成功","result":{},"status":200}
    private int secret = 0;//1:数据已被加密，0：数据未加密
    private String code = "";//接口返回的状态码
    private String result = "";//接口返回的主数据
    private String message = "";//接口返回的描述
    private int status;
    private String data="";
    private int orderType;//单据列表的类型
    private long time = 0l;//接口返回时间

    private byte eCode = 0x0;

    /**
     * 成功
     */
    public static final String SUCCESS = "9999999";
    public static final String TOKEN_TIME_OUT = "0000001";

    /**
     * 检测Token是否过期
     * @return
     */
    public boolean isTokenTimeOut(){
        if(code.equals(TOKEN_TIME_OUT)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 接口是否调用成功
     * @return
     */
    public boolean APISuccess(){
//        if(code.equals(SUCCESS)){
//            return true;
//        }else{
//            return false;
//        }
        return true;
    }

    public int getSecret() {
        return secret;
    }

    public String getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }
    public String getData(){
        return data;
    }
    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public byte geteCode() {
        return eCode;
    }

    public void seteCode(byte eCode) {
        this.eCode = eCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
