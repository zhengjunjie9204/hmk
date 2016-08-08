package com.xgx.syzj.app;

import com.orhanobut.logger.LogLevel;

/**
 * 应用中常量定制
 *
 * @author zajo
 * @created 2015年08月06日 17:49
 */
public class Constants {

    //if debug
    public static boolean DEBUG = true;
    public static LogLevel LOGLEVEL = LogLevel.FULL;//发布切换为NONE

    public static int LOAD_COUNT = 40;//列表分页加载数量
    //数据库
    public static String DB_NAME = "hmk.db3";
    public static int DB_VERSION = 1;

    public static class SharedPreferencesClass {
        public static final String SP_FIRST_START = "SP_FIRST_START";   //首次启动
        public static final String SP_PHONE = "SP_PHONE";   //手机号码
        public static final String SP_PSW = "SP_PSW";   //密码
        public static final String SP_MAIN_SHORTCUT = "SP_MAIN_SHORTCUT";//首页快捷菜单
        public static final String SP_MAIN_USER_NAME = "SP_MAIN_USER_NAME";//用户老板名字
        public static final String SP_MAIN_SHOP_NAME = "SP_MAIN_SHOP_NAME";//店铺名字
        public static final String SP_MAIN_SHOP_ADDR = "SP_MAIN_SHOP_ADDR";//店铺地址
        public static final String SP_MAIN_SHOP_PHONE = "SP_MAIN_SHOP_PHONE";//店铺电话
        public static final String SP_MAIN_BUSINESS_TYPE = "SP_MAIN_BUSINESS_TYPE";//行业类型
        public static final String SP_TOKEN="SP_TOKEN";//登录token
        public static final String SP_ROLES="SP_ROLES";//用户角色
        public static final String SP_STORE_ID="SP_STORE_ID";//用户角色

    }

    public static class Broadcast {
        public static String RECEIVER_ADD_RECHARGE = "com.xgx.syzj.RECEIVER_ADD_RECHARGE";//储值、充次、兑换积分
        public static String RECEIVER_DELETE_MEMBER = "com.xgx.syzj.RECEIVER_DELETE_MEMBER";//删除会员
        public static String RECEIVER_UPDATE_MEMBER = "com.xgx.syzj.RECEIVER_UPDATE_MEMBER";//更新会员
        public static String RECEIVER_UPDATE_CARD = "com.xgx.syzj.RECEIVER_UPDATE_CARD";//更新会员卡
        public static String RECEIVER_DELETE_CARD = "com.xgx.syzj.RECEIVER_DELETE_CARD";//删除会员卡
    }

    //充值类型
    public static class RechargeType {
        public static int RECHARGE_VALUE = 0;//储值
        public static int RECHARGE_COUNT = 1;//充次
    }
}
