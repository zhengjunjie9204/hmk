package com.xgx.syzj.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class PayType {
//    "1";	//微信支付
//    "2";	//余额支付
//    "3"; //现金支付
//    "4";	//银行卡支付
//    "5";	//支付宝支付
//    "6";//(储值，现金)，
//    "7";//(储值，微信)
//    "8";	//项目从计次里面扣除
//    "9";	//项目不是从计次里面扣
    public Map payType;

    public PayType() {
        payType= new HashMap();
    }


    public Map getPayType() {
        payType.put("1","微信支付");
        payType.put("2","余额支付");
        payType.put("3","现金支付");
        payType.put("4","银行卡支付");
        payType.put("5","支付宝支付");
        payType.put("6","储值，现金");
        payType.put("7","储值，微信");
        payType.put("8","项目从计次里面扣除");
        payType.put("9","项目不是从计次里面扣");
        return payType;
    }

    public void setPayType(Map payType) {
        this.payType = payType;
    }
}
