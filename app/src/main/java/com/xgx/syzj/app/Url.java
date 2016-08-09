package com.xgx.syzj.app;

/**
 * 程序联网的url集合
 *
 * @author zajo
 * @created 2015年08月06日 15:44
 */
public class Url {

    //局域网
    // public static final String HOST_URL = "http://172.16.4.245:8080/easydigpon_web/";
    //阿里云
    //http://test10.messcat.com/
    public static final String HOST_URL = "http://qcmr.junrenwl.com:8080/";

    //基础请求
    public static final String USER_REGISTER = HOST_URL + "user/register";//注册
    public static final String USER_LOGIN = HOST_URL + "qcmr/login_loginByUserName.action";//登录
    public static final String USER_LOGIB_BY_TKEN=HOST_URL+"qcmr/login_loginByToken.action";//token登录
    public static final String REFRESH_ACCESSTOKEN = HOST_URL + "token/refreshToken";//重新获取Token
    public static final String USER_ADD_STAFF = HOST_URL + "employee/add";//添加员工
    public static final String USER_DELETE_STAFF = HOST_URL + "employee/delete";//删除员工
    public static final String USER_GETLIST_STAFF = HOST_URL + "employee/getlist";//获取员工列表
    public static final String USER_UPDATE_STAFF = HOST_URL + "employee/update";//更新员工

    //商品
    public static final String SEARCH_EXT_PRODUCTS = HOST_URL + "qcmr/product_findByKey.action";//商品列表带分类
    public static final String SEARCH_EXT_PRODUCTS_BRAND = HOST_URL + "qcmr/product_findByBrandAndCategory.action";//商品列表带分类通过品牌和车型
    public static final String SEARCH_EXT_COUNT = HOST_URL + "product/getsearchcount";//获取商品总数
    public static final String ADD_PRODUCTS = HOST_URL + "qcmr/product_add.action";//添加商品
    public static final String UPDATE_PRODUCTS = HOST_URL + "qcmr/product_update.action";//更新商品
    public static final String DELETE_PRODUCTS = HOST_URL + "qcmr/product_removeProductFromStore.action";//删除商品
    public static final String INTO_OUT_PRODUCTS = HOST_URL + "qcmr/stockRecord_add.action";//商品出入库
    public static final String DELETE_PRODUCTS_TWO=HOST_URL+"qcmr/product_removeProducyFromStore.action";//删除商品2
    public static final String STOCK_RECORD=HOST_URL+"qcmr/stockRecord_stockRecordHistory.action";//库存历史
    public static final String ADD_PRODUCTS_BY_STORE=HOST_URL+"qcmr/product_findByNoStore.action";//添加未存在商品
    public static final String ADD_PRODUCT_TO_STORE=HOST_URL+"qcmr/product_addProductToStore.action";//添加新商品到门店

    //项目
    public static final String PROJECT_EXT_LIST=HOST_URL+"qcmr/item_findStoreItemByKey.action";//项目列表
    //会员
    public static final String ASSOCIATOR_ADD = HOST_URL + "qcmr/member_add.action";//添加会员
    public static final String ASSOCIATOR_EXT_LIST = HOST_URL + "qcmr/member_findAll.action";//列表会员（搜索）扩展
    public static final String ASSOCIATOR_EXT_COUNT = HOST_URL + "associator/getsearchcount";//列表会员总数
    public static final String ASSOCIATOR_UPDATE = HOST_URL + "qcmr/member_modifyMember.action";//更新会员
    public static final String ASSOCIATOR_RECHARGE = HOST_URL + "recharge/add";//会员储值
    public static final String ASSOCIATOR_RECHARGE_LIST = HOST_URL + "recharge/getlist";//会员储值列表
    public static final String ASSOCIATOR_EXC_INTEGRAL = HOST_URL + "integralexchange/add"; //积分兑换

    //充值
    public static final String ASSOCIATOR_RECHARGE_UPDATE = HOST_URL + "recharge/update";//修改充值
    public static final String ASSOCIATOR_RECHARGE_DELETE = HOST_URL + "recharge/delete";//删除充值

    //积分券
    public static final String ASSOCIATOR_EXC_INTEGRAL_UPDATE = HOST_URL + "integralexchange/update"; //更新积分兑换
    public static final String ASSOCIATOR_EXC_INTEGRAL_DELETE = HOST_URL + "integralexchange/delete"; //删除积分兑换
    public static final String ASSOCIATOR_EXC_INTEGRAL_GETLIST = HOST_URL + "integralexchange/getlist"; //获取积分兑换列表

    //会员卡
    public static final String CARD_CREATE = HOST_URL + "cardtype/add";//创建会员卡
    public static final String CARD_LIST = HOST_URL + "cardtype/getlist";//获取会员卡列表
    public static final String CARD_DETAIL = HOST_URL + "card/getcard";//获取会员卡详情
    public static final String CARD_DELETE = HOST_URL + "cardtype/delete";//删除会员卡
    public static final String CARD_UPDATE = HOST_URL + "cardtype/update";//更新会员卡

    //收银销售
    public static final String CART_PUT_UP = HOST_URL + "cart/putup";//挂单
    public static final String CART_DETAIL = HOST_URL + "cart/getcartdetails";//挂单详情
    public static final String CART_LIST = HOST_URL + "cart/getextlist";//挂单列表
    public static final String CART_BILL_CHECK = HOST_URL + "bill/check";//结算

    //分析
    public static final String ANALYSIS_TOTAL_SALE = HOST_URL + "analysis/totalconsumption";//全部收银记录
    public static final String ANALYSIS_MEMBER_SALE = HOST_URL + "analysis/associatorsurvey";//会员收银记录
    public static final String ANALYSIS_GROSS_SALE = HOST_URL + "analysis/grosssales";//散客收银记录


    //账单
    public static final String BILL_ADD = HOST_URL + "bill/add";//增加结帐单
    public static final String BILL_UPDATE = HOST_URL + "bill/update";//修改结帐单
    public static final String BILL_DELETE = HOST_URL + "bill/delete";//删除结帐单
    public static final String BILL_GETLIST = HOST_URL + "bill/getlist";//获取结帐单列表
    public static final String BILL_GETEXTCOUNT = HOST_URL + "bill/getextcount";// 获取结帐单数量
    public static final String BILL_GETEXTLIST = HOST_URL + "bill/getextlist";//获取已结帐单列表
    public static final String BILL_CANCEL = HOST_URL + "bill/cancel";// 取消结帐单
    public static final String BILL_SALESRETURN = HOST_URL + "bill/salesreturn";//订单退回
    public static final String BILL_GETORDERDETAILS = HOST_URL + "order/getorderdetails";//获取订单详细列表



    //购物车
    public static final String CART_ADD = HOST_URL + "cart/add";//增加购物车
    public static final String CART_UPDATE = HOST_URL + "cart/update";//更新购物车
    public static final String CART_DELETE = HOST_URL + "cart/delete";//删除购物车

    public static final String STORE_UPDATE = HOST_URL + "store/update";//更新店铺信息
    public static final String BILL_SALES_RETURN = HOST_URL + "bill/salesreturn";//退货

    //报表分析
    public static final String  ANALY_SALE_TOTAL = HOST_URL + "analysis/salestotal";//销售合计
    public static final String  ANALY_SALE_GROSS_PROFIT = HOST_URL + "analysis/salesgrossprofit";//销售毛利润
    public static final String  ANALY_SALE_GROSSSALES = HOST_URL + "analysis/grosssales";//销售总额
    public static final String  ANALY_SALE_TTOTAL_CONSUMPTION = HOST_URL + "analysis/totalconsumption";//消费统计
    public static final String  ANALY_SALE_SALESCOUNT = HOST_URL + "analysis/salescount";//销售总量
    public static final String  ANALY_SALE_GROSSPROFIT = HOST_URL + "analysis/grossprofit";// 利润总额
    public static final String  ANALY_SALE_COUNT = HOST_URL + "analysis/salescount";// 利润总额
    //门店管理
    public static final String USER_INFO = HOST_URL + "qcmr/user_userInfo.action";//3.9.1账号信息
    public static final String STORE_INFO = HOST_URL + "qcmr/store_storeInfo.action";//3.9.2门店信息
    public static final String FIND_STORE_EMPLOYEE = HOST_URL + "qcmr/store_findStoreEmployee.action";//3.9.2门店信息

    //会员
    public static final String COMBO_LIST = HOST_URL + "qcmr/combo_findByKey.action";//3.4.1.	根据套餐名关键字查询门店下套餐
    public static final String ALL_STORE_ITEM = HOST_URL + "qcmr/item_findAllStoreItem.action";//3.3.2.	查询门店下所有项目

    public static final String MENBER_BY_ID = HOST_URL + "qcmr/member_findMemberById.action";//3.5.1.	会员详情
    public static final String ASSOCIATOR_DELETE = HOST_URL + "qcmr/member_deleteById.action";//3.5.4.	删除会员
    public static final String STORE_MONEY_RECORD = HOST_URL + "qcmr/member_storeMoneyRecord.action";//3.5.6.	储值记录
    public static final String CONSUME_LIST = HOST_URL + "qcmr/member_itemAllConsume.action";//3.5.7.	充次记录
    public static final String CONSUME_HISTORY = HOST_URL + "qcmr/member_consumeHistory.action";//3.5.8.	消费历史
    public static final String ADD_STORE_MONEY = HOST_URL + "qcmr/memberStore_addStoreMoney.action";//3.5.9.	会员储值充值
    public static final String ADD_ITEM_COMBO = HOST_URL + "qcmr/memberStore_addItemCombo.action";//3.5.10.	会员计次充值
    public static final String FIND_MENBER_ITEM = HOST_URL + "qcmr/memberStore_findMenberItemLeft.action";//3.5.11.	查询会员项目计次余次
    public static final String MEMBER_PAY_MONEY = HOST_URL + "qcmr/memberStore_findPayByStoreMoney.action";//3.5.12.	查询会员储值卡累计消费金额
    public static final String MEMBER_SUM_MONEY = HOST_URL + "qcmr/memberStore_sumStoreMoneyRecord.action";//3.5.13.	查询会员储值累计充值金额
    public static final String SUM_ITEM_RECORD = HOST_URL + "qcmr/memberStore_sumStoreItemRecord.action";//3.5.14.	查询会员计次累计充值金额
    public static final String MEMBER_BASE_INFO = HOST_URL + "qcmr/memberStore_memberBasicInfo.action";//3.5.15.	查询会员基本信息

    public static final String ORDER_PAY = HOST_URL + "qcmr/pay_microPay.action";//3.7.1.	支付订单
    public static final String ORDER_PAY_PRODUCT = HOST_URL + "qcmr/memberStore_ payOnlyProduct.action";//3.7.2.	支付只含商品的订单
    public static final String ORDER_CREATE = HOST_URL + "qcmr/memberStore_createOrderWithItem.action";//3.7.3.	创建含项目订单
    public static final String ORDER_UNPAY_LIST = HOST_URL + "qcmr/memberStore_findUnpayedOrder.action";//3.7.4.	查询未支付项目订单
    public static final String ORDER_SET_DONE = HOST_URL + "qcmr/memberStore_setOrderUnpayDone.action";//3.7.5.	设项目订单未支付已完成
    public static final String ORDER_PAY_ITEM = HOST_URL + "qcmr/memberStore_payItemOrder.action";//3.7.6.	支付含项目订单
    public static final String ORDER_CANCEL = HOST_URL + "qcmr/memberStore_cancelOrder.action";//3.7.7.	作废订单
    public static final String ORDER_DETAILS = HOST_URL + "qcmr/payOrder_orderDetail.action";//3.7.8.	订单详情
    public static final String ORDER_FILTER_LIST = HOST_URL + "qcmr/payOrder_ filterOrder.action";//3.7.9.	订单筛选

    public static final String MONEY_REPORT = HOST_URL + "qcmr/statistics_moneyReport.action";//3.8.1.	资金流水
    public static final String SALE_REPORT = HOST_URL + "qcmr/statistics_saleReport.action";//3.8.2.	销售分析

    //未完成接口
    //消费历史、兑换积分、储值记录、销售列表、销售详情、退货原因、考勤列表、设置考勤位置、考勤、修改店铺信息
    // 打印设置、版本更新、商品分析、会员分析、销售分析
    //新增了25个功能接口
    //销售毛利润  销售合计  销售总量 利润总额  删除购物车  更新购物车  增加购物车  获取订单详细列表  订单退回  取消结帐单  获取已结帐单列表
    //获取结帐单数量   获取结帐单列表  删除结帐单 修改结帐单 增加结帐单  获取积分兑换列表  删除积分兑换  更新积分兑换  删除充值    修改充值
    //  筛选  删除员工
}