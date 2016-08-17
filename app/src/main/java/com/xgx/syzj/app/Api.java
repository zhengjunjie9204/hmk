package com.xgx.syzj.app;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.volley.request.StringRequest;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.BillDetail;
import com.xgx.syzj.bean.GoodsCart;
import com.xgx.syzj.secret.Base64Util;
import com.xgx.syzj.secret.RSAManager;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.FastJsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络请求接口
 *
 * @author zajo
 * @created 2015年08月06日 15:26
 */
public class Api extends BaseRequest {

    private static Map<String, String> getHeader()
    {
        Map<String, String> header = null;
        try {
            header = new HashMap<>();
            String accessToken = CacheUtil.getmInstance().getUser().getToken();
            header.put("token", accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return header;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param listener
     * @return
     */
    public static StringRequest login(String username, String password, final OnRequestListener listener)
    {
        Map<String, String> params = new HashMap<>();
        params.put("userName", username);
        params.put("passWord", password);

        String json = FastJsonUtil.bean2Json(params);
        String info = Base64Util.encode(json.getBytes());
        //String rsaEncrypt = RSAManager.getInstance(mContext).encrypt(json);//RSA加密

        params.clear();
        params.put("info", info);
        return getRequest(Url.USER_LOGIN, params, listener);
    }

    public static StringRequest loginByToken(String token, OnRequestListener listener)
    {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);

        String json = FastJsonUtil.bean2Json(params);
        String info = Base64Util.encode(json.getBytes());
        //String rsaEncrypt = RSAManager.getInstance(mContext).encrypt(json);//RSA加密

        params.clear();
        params.put("info", info);
        return getRequest(Url.USER_LOGIB_BY_TKEN, params, listener);
    }

    /**
     * 注册
     *
     * @param mobile
     * @param psw
     * @param listener
     * @return
     */
    public static StringRequest register(String mobile, String psw, String codeNum, OnRequestListener listener)
    {
        Map<String, String> params = new HashMap<>();
        params.put("accountId", mobile);
        params.put("mobilePhone", mobile);
        // params.put("password", StrUtil.getMD5(psw));
        String json = FastJsonUtil.bean2Json(params);
        String rsaUserEncrypt = RSAManager.getInstance(mContext).encrypt(json);//RSA加密

        params.clear();
        params.put("storeName", "zajo的店铺");
        json = FastJsonUtil.bean2Json(params);
        String rsaStoreEncrypt = RSAManager.getInstance(mContext).encrypt(json);//RSA加密

        params.clear();
        params.put("userinfo", rsaUserEncrypt);
        params.put("storeinfo", rsaStoreEncrypt);

        return getRequest(Url.USER_REGISTER, params, listener);
    }

    /**
     * 刷新Token
     *
     * @param listener
     * @return
     */
//    public static StringRequest refreshAccessToken(final OnRequestListener listener) {
//        Map<String, String> map = new HashMap();
//        String newToken = null;
//        try {
//            String tokenStr = CacheUtil.getmInstance().getShop().getTokenInfo().getRefreshToken();
//            String json = FastJsonUtil.bean2Json(tokenStr);
//            newToken = Base64Util.encode(json.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        map.put("refreshToken", newToken);
//        return getRequest(Url.REFRESH_ACCESSTOKEN, map, new OnRequestListener() {
//            @Override
//            public void onSuccess(Result result) {
//                String token = result.getResult();
//                CacheUtil.getmInstance().getUser().setToken(token);
//            }
//
//            @Override
//            public void onError(String errorCode, String message) {
//                listener.onError(errorCode, message);
//            }
//        });
//    }


    /**
     * 添加员工
     *
     * @param mobile
     * @param psw
     * @param userName
     * @param authority
     * @param storeId
     * @param listener
     * @return
     */
    public static StringRequest registerStaff(String mobile, String psw, String userName, int authority, int storeId, OnRequestListener listener)
    {
        Map<String, String> params = new HashMap<>();
        params.put("accountId", mobile);
        params.put("mobilePhone", mobile);
        params.put("password", psw);
        String json = FastJsonUtil.bean2Json(params);
        String rsaUserEncrypt = RSAManager.getInstance(mContext).encrypt(json);//RSA加密

        params.clear();
        params.put("authority", authority + "");
        params.put("userName", userName);
        params.put("storeId", storeId + "");
        json = FastJsonUtil.bean2Json(params);
        String rsaStoreEncrypt = RSAManager.getInstance(mContext).encrypt(json);//RSA加密

        params.clear();
        params.put("userinfo", rsaUserEncrypt);
        params.put("extuserinfo", rsaStoreEncrypt);

        Map<String, String> header = new HashMap<>();
        header.put("accessToken", CacheUtil.getmInstance().getUser().getToken());

        return getRequest(Url.USER_ADD_STAFF, params, header, listener);
    }


    /**
     * 更新员工信息
     *
     * @param accountId
     * @param mobilePhone
     * @param password
     * @param authority
     * @param listener
     * @return
     */
    public static StringRequest updateEmployee(String accountId, String mobilePhone, String password, int authority, OnRequestListener listener)
    {
        Map<String, String> params = null;
        String info = null;
        try {
            params = new HashMap<>();
            params.put("accountId", accountId);
            params.put("mobilePhone", mobilePhone);
            params.put("password", password);
            params.put("authority", authority + "");
            String json = FastJsonUtil.bean2Json(params);
            info = Base64Util.encode(json.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.clear();
        params.put("userinfo", info);
        return getRequest(Url.USER_UPDATE_STAFF, params, getHeader(), listener);
    }

    /**
     * 删除员工
     *
     * @param accountId
     * @param listener
     * @return
     */
    public static StringRequest deleteEmployee(String accountId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        String info = null;
        try {
            params = new HashMap<>();
            params.put("accountId", accountId);
            String json = FastJsonUtil.bean2Json(params);
            info = Base64Util.encode(json.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.clear();
        params.put("userinfo", info);
        return getRequest(Url.USER_DELETE_STAFF, params, getHeader(), listener);
    }


    /**
     * 查询员工列表
     *
     * @param page
     * @param pageSize
     * @param listener
     * @return
     */
    public static StringRequest getEmployeeList(String key, int page, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        String info = null;
        try {
            params = new HashMap<>();
            if (!TextUtils.isEmpty(key)) {
                params.put("key", key);
            }
            params.put("page", page + "");
            params.put("pageSize", pageSize + "");
            String json = FastJsonUtil.bean2Json(params);
            info = Base64Util.encode(json.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.clear();
        params.put("info", info);
        return getRequest(Url.USER_GETLIST_STAFF, params, getHeader(), listener);
    }

    /**
     * 充次记录
     *
     * @param memberId
     * @param listener
     * @return
     */
    public static StringRequest getConsumeList(int memberId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("memberId", memberId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.CONSUME_LIST, params, getHeader(), listener);
    }

    /**
     * 添加商品
     */
    public static StringRequest addProducts(String barcode, String productName, String categoryId, String inputPrice, String sellingPrice, String vip_price, String specification, String brand, String unitid, String image, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            params.put("barcode", barcode);
            params.put("productName", productName);
            params.put("categoryId", categoryId);
            params.put("inputPrice", inputPrice);
            params.put("sellingPrice", sellingPrice);
            params.put("brand", brand);
            params.put("vip_price", vip_price);
            params.put("specification", specification);
            params.put("unitid", unitid);
            String json = FastJsonUtil.bean2Json(params);
            String info = new String(Base64.encode(json.getBytes("UTF-8"), Base64.DEFAULT));
            params.clear();
            params.put("info", info);
            params.put("image", image);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return getRequest(Url.ADD_PRODUCTS, params, getHeader(), listener);
    }

    /**
     * 更新商品信息
     *
     * @param listener
     * @return
     */
    public static StringRequest updateProducts(int productId, String barcode, String productName, String categoryId, String inputPrice, String sellingPrice, String vip_price, String specification, String brand, String unitid, String image, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            params.put("productId", productId + "");
            params.put("barcode", barcode);
            params.put("productName", productName);
            params.put("categoryId", categoryId);
            params.put("inputPrice", inputPrice);
            params.put("sellingPrice", sellingPrice);
            params.put("brand", brand);
            params.put("vip_price", vip_price);
            params.put("specification", specification);
            params.put("unitid", unitid);
            String json = FastJsonUtil.bean2Json(params);
            String info = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", info);
            params.put("image", image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.UPDATE_PRODUCTS, params, getHeader(), listener);
    }

    /**
     * 添加本店没有的商品
     *
     * @param storeID
     * @param listener
     * @return
     */
    public static StringRequest addProductByStore(long storeID, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            params.put("storeID", storeID + "");
            String json = FastJsonUtil.bean2Json(params);
            String info = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.ADD_PRODUCTS_BY_STORE, params, getHeader(), listener);
    }

    /**
     * 3.1.7.	添加新商品到门店
     *
     * @param storeID
     * @param listener
     * @return
     */
    public static StringRequest addProductToStore(long storeID, long productID, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            params.put("storeId", storeID + "");
            params.put("productId", productID + "");
            String json = FastJsonUtil.bean2Json(params);
            String info = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.ADD_PRODUCT_TO_STORE, params, getHeader(), listener);
    }
    /**
     * 3.1.7.	老板查询所有商品
     *
     * @param
     * @param listener
     * @return
     */
    public static StringRequest findAllProduct(int pageNo, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            params.put("pageNo", pageNo+"");
            params.put("pageSize", pageSize+"");
            String json = FastJsonUtil.bean2Json(params);
            String info = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.find_All_PRODUCT, params, getHeader(), listener);
    }

    /**
     * 删除商品
     *
     * @param uId
     * @param listener
     * @return
     */
    public static StringRequest deleteProducts(Long uId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("uId", uId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.DELETE_PRODUCTS, params, getHeader(), listener);
    }

    /**
     * 删除商品2
     *
     * @param productId
     * @param storeId
     * @param listener
     * @return
     */
    public static StringRequest deleteProductsTwo(int productId, int storeId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("productId", productId);
            info.put("storeId", storeId);

            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.DELETE_PRODUCTS_TWO, params, getHeader(), listener);
    }

    /**
     * 商品出入库
     *
     * @param flag        0入库、1出库
     * @param productId
     * @param stockCount
     * @param description
     * @param listener
     * @return
     */
    public static StringRequest mIntoAndOutProducts(int flag, int productId, int stockCount, String description, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("productId", productId);
            info.put("stockCount", stockCount);//数量
            info.put("flag", flag);
            info.put("description", description);//备注
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.INTO_OUT_PRODUCTS, params, getHeader(), listener);
    }

    /**
     * 获取出入库历史
     *
     * @param productId
     * @return
     */
    public static StringRequest stockRecordHistory(int productId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("storeId", 1);
            info.put("productId", productId);
            String json = FastJsonUtil.bean2Json(info);
            params.clear();
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.STOCK_RECORD, params, getHeader(), listener);
    }

    /**
     * 获取商品列表
     *
     * @param key        搜索关键字
     * @param page
     * @param pageSize
     * @param listener
     * @return
     */
    public static StringRequest getProductsList(String key,long storeId, int page, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            if (!TextUtils.isEmpty(key)) {
                info.put("key", key);
            }
            info.put("storeId", storeId);
            info.put("pageNo", page);
            info.put("pageSize", pageSize);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.SEARCH_EXT_PRODUCTS, params, getHeader(), listener);
    }

    /**
     * @param brand      品牌
     * @param categoryId
     * @param page
     * @param pageSize
     * @param listener
     * @return
     */
    public static StringRequest getProductsListByBand(String brand, String categoryId, int page, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            if (!TextUtils.isEmpty(brand)) {
                info.put("brand", brand);
            }
            if (!TextUtils.isEmpty(categoryId)) {
                info.put("categoryId", categoryId);
            }
//            CacheUtil.getmInstance().getUser().getStoreId()
            info.put("pageNo", page);
            info.put("pageSize", pageSize);
            String json = FastJsonUtil.bean2Json(info);
            Log.e("json:", json);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.SEARCH_EXT_PRODUCTS_BRAND, params, getHeader(), listener);
    }

    /**
     * 获取项目列表
     */
    public static StringRequest getProjectList(String key, int page, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            if (!TextUtils.isEmpty(key)) {
                info.put("key", key);
            }
            info.put("pageNo", page);
            info.put("pageSize", pageSize);
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.PROJECT_EXT_LIST, params, getHeader(), listener);
    }


    /**
     * 3.5.11.	查询会员项目计次余次
     *
     * @param memberId
     * @param listener
     * @return
     */
    public static StringRequest getMenberItem(int memberId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("memberId", memberId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.FIND_MENBER_ITEM, params, getHeader(), listener);
    }

    /**
     * 获取商品总数量
     *
     * @param key
     * @param listener
     * @return
     */
    public static StringRequest getProductsCount(String key, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("key", key);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.SEARCH_EXT_COUNT, params, getHeader(), listener);
    }
    /**
     * 编辑已完成的订单(7.11)
     * @param listener
     * @return
     */
    public static StringRequest editOrder(int payOrderId,JSONArray itemList,JSONArray productList,OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject json = new JSONObject();
            json.put("payOrderId",payOrderId);
            json.put("itemList",itemList);
            json.put("productList",productList);
            params.put("info", Base64Util.encode(json.toString().getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.EDIT_ORDER, params, getHeader(), listener);
    }


    /**
     * 新建会员卡
     *
     * @param cardTypeName
     * @param supportIntegral
     * @param supportValue
     * @param supportCount
     * @param needPassord
     * @param discount
     * @param integralRatio
     * @param listener
     * @return
     */
    public static StringRequest addCardType(String cardTypeName, boolean supportIntegral, boolean supportValue, boolean supportCount, boolean needPassord, int discount, int integralRatio, OnRequestListener listener)
    {
        Map<String, Object> params;
        Map<String, String> params2 = null;
        try {
            params = new HashMap<>();
            params2 = new HashMap<>();
            params.put("cardTypeName", cardTypeName);
            params.put("supportIntegral", supportIntegral);
            params.put("supportValue", supportValue);
            params.put("supportCount", supportCount);
            params.put("needPassord", needPassord);
            params.put("discount", discount);
            params.put("integralRatio", integralRatio);
            String json = FastJsonUtil.bean2Json(params);
            String info = Base64Util.encode(json.getBytes("UTF-8"));
            params2.clear();
            params2.put("info", info);
        } catch (Exception e) {
        }

        return getRequest(Url.CARD_CREATE, params2, getHeader(), listener);
    }

    /**
     * 获取会员卡列表
     *
     * @param listener
     * @return
     */
    public static StringRequest getCardTypeList(OnRequestListener listener)
    {
        Map<String, Object> info;
        Map<String, String> params = null;
        try {
            info = new HashMap<>();
            params = new HashMap<>();
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", json);
        } catch (Exception e) {
        }
        return getRequest(Url.CARD_LIST, params, getHeader(), listener);
    }

    /**
     * 3.5.14.	查询会员计次累计充值金额
     *
     * @param memberId
     * @param listener
     * @return
     */
    public static StringRequest getSumRecord(int memberId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("memberId", memberId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.SUM_ITEM_RECORD, params, getHeader(), listener);
    }

    /**
     * 获取会员卡积分储值详情
     *
     * @param cardId
     * @param listener
     * @return
     */
    public static StringRequest getCardDetail(int cardId, OnRequestListener listener)
    {
        Map<String, Object> paramInfo = null;
        Map<String, String> params = new HashMap<>();

        try {
            paramInfo = new HashMap<>();
            paramInfo.put("cardId", cardId);
            String json = FastJsonUtil.bean2Json(paramInfo);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            paramInfo.clear();
            params.put("info", json);
        } catch (Exception e) {
        }
        return getRequest(Url.CARD_DETAIL, params, getHeader(), listener);
    }

    /**
     * 会员计次充值
     */
    public static StringRequest addItemCombo(int memberId, String fee, int payType, JSONArray comboList, JSONArray itemList, String content, String sendSMSFlag, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("memberId", memberId);
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("employee", CacheUtil.getmInstance().getUser().getEmployeeId());//接单员ID
            info.put("fee", fee);//充值金额
            info.put("payType", payType);//付款方式  支付类型（1：微信支付；2:余额；3：现金支付）
//            info.put("authCode", rechargeType);//微信授权码，现金支付不需要
            info.put("comboList", comboList);//Long数组 套餐ID（套和项目,二必选一）
            info.put("itemList", itemList);//Long数组 项目ID数组（套餐和项目,二必选一）
            info.put("content", content);//备注
            info.put("sendSMSFlag", sendSMSFlag);//是否发送充值短信 1：发   0：不发
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ADD_ITEM_COMBO, params, getHeader(), listener);
    }

    /**
     * 根据车牌号查询会员(5.16)
     */
    public static StringRequest getCarNumber(String carNumber, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("carNumber", carNumber);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.FIND_CARNUMBER, params, getHeader(), listener);
    }

    /**
     * 新增散客(5.17)
     */
    public static StringRequest addOtherMember(String carNumber, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("carNumber", carNumber);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ADD_OTHER_MEMBER, params, getHeader(), listener);
    }

    /**
     * 获取未支付的订单
     */
    public static StringRequest getUnpayOrder(int pageNo, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("pageNo", pageNo);
            info.put("pageSize", pageSize);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ORDER_UNPAY_LIST, params, getHeader(), listener);
    }

    /**
     * 3.7.5.	设项目订单未支付已完成
     */
    public static StringRequest setOrderDone(int payOrderId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("payOrderId", payOrderId);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ORDER_SET_DONE, params, getHeader(), listener);
    }
    /**
     * 3.7.5.	设项目订单未支付已完成
     */
    public static StringRequest getAllStore(OnRequestListener listener)
    {
        return getRequest(Url.FIND_ALL_STORE, null, getHeader(), listener);
    }

    /**
     * 3.7.2.	支付只含商品的订单
     *
     * @param memberId
     * @param payTwiceFlag
     * @param payType
     * @param authCode
     * @param productList
     */
    public static StringRequest orderPayProduct(int memberId, int payTwiceFlag, int payType, String fee, String authCode, JSONArray productList, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("employee", CacheUtil.getmInstance().getUser().getEmployeeId());
            info.put("memberId", memberId);
            info.put("fee", fee);
            info.put("payTwiceFlag", payTwiceFlag);
            info.put("payType", payType);
            info.put("authCode", authCode);
            info.put("productList", productList);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ORDER_PAY_PRODUCT, params, getHeader(), listener);
    }

    /**
     * 3.7.3.	创建含项目订单
     *
     * @param memberId    会员Id
     * @param fee         金额
     * @param payType     支付方式
     * @param productList 商品
     * @param itemList    项目
     */
    public static StringRequest orderCreate(int memberId, String fee, int payType, JSONArray productList, JSONArray itemList, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("employee", CacheUtil.getmInstance().getUser().getEmployeeId());
            info.put("memberId", memberId);
            info.put("fee", fee);
            info.put("payType", payType);
            info.put("productList", productList);
            info.put("itemList", itemList);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ORDER_CREATE, params, getHeader(), listener);
    }

    /**
     * 3.7.6.	支付含项目订单
     *
     * @param payOrderId   订单Id
     * @param payTwiceFlag 1:是，0:否,如果使用了两种支付方式则为1，否则为0
     * @param payType      如果使用两种方式（则为除储值外的第二个方式）
     * @param authCode     微信授权码
     */
    public static StringRequest orderPayItem(int payOrderId, int payTwiceFlag, int payType, String authCode, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("payOrderId", payOrderId);
            info.put("payTwiceFlag", payTwiceFlag);
            info.put("payType", payType);
            info.put("authCode", authCode);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ORDER_PAY_ITEM, params, getHeader(), listener);
    }

    /**
     * 3.7.8.	订单详情
     */
    public static StringRequest getOrderDetail(int payOrderId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("payOrderId", payOrderId);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ORDER_DETAILS, params, getHeader(), listener);
    }

    /**
     * 3.7.9.	订单筛选,订单列表
     */
    public static StringRequest getOrderFilter(String key,String minMoney,String maxMoney, String startTime, String endTime, int pageNo, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("key",key);
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("startTime", startTime);
            info.put("endTime", endTime);
            info.put("minMoney",minMoney);
            info.put("maxMoney",maxMoney);
            info.put("pageNo", pageNo);
            info.put("pageSize", pageSize);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ORDER_FILTER_LIST, params, getHeader(), listener);
    }

    /**
     * 3.7.7.	作废订单
     */
    public static StringRequest setOrderCancel(String payOrderId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("payOrderId", payOrderId);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ORDER_CANCEL, params, getHeader(), listener);
    }

    /**
     * 3.5.13.	查询会员储值累计充值金额
     */
    public static StringRequest getMemberSumMoney(String memberId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("memberId", memberId);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.MEMBER_SUM_MONEY, params, getHeader(), listener);
    }

    /**
     * 3.5.12.	查询会员储值卡累计消费金额
     */
    public static StringRequest getMemberPayMoney(String memberId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("memberId", memberId);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.MEMBER_PAY_MONEY, params, getHeader(), listener);
    }

    /**
     * 3.5.15.	查询会员基本信息
     */
    public static StringRequest getMemberBaseInfo(int memberId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("memberId", memberId);
            String json = Base64Util.encode(info.toString().getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.MEMBER_BASE_INFO, params, getHeader(), listener);
    }

    /**
     * 删除会员
     *
     * @param memberId
     * @param listener
     * @return
     */
    public static StringRequest getConsumeHistory(int memberId, int pageNo, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("memberId", memberId);
            info.put("pageNo", pageNo);
            info.put("pageSize", pageSize);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.CONSUME_HISTORY, params, getHeader(), listener);
    }

    /**
     * 删除会员卡
     *
     * @param cardTypeId
     * @param listener
     * @return
     */
    public static StringRequest deleteCardType(int cardTypeId, OnRequestListener listener)
    {
        Map<String, Object> info;
        Map<String, String> params = null;
        try {
            info = new HashMap<>();
            params = new HashMap<>();
            info.put("cardTypeId", cardTypeId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.CARD_DELETE, params, getHeader(), listener);
    }
    /**
     * 会员储值
     *
     * @param associatorId
     * @param rechargeAmount
     * @param giftAmount
     * @param rechargeType
     * @param description
     * @param listener
     * @return
     */
    /**
     * 会员储值
     */
    public static StringRequest addStoreMoney(int memberId, String fee, int payType, String giveMoney, String content, String sendSMSFlag, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("memberId", memberId);
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("employee", CacheUtil.getmInstance().getUser().getEmployeeId());//接单员ID
            info.put("fee", fee);//充值金额
            info.put("payType", payType);//付款方式  支付类型（1：微信支付；2:余额；3：现金支付）
//            info.put("authCode", rechargeType);//微信授权码，现金支付不需要
            info.put("giveMoney", giveMoney);//微信授权码，现金支付不需要
            info.put("content", content);
            info.put("sendSMSFlag", sendSMSFlag);//是否发送充值短信 1：发   0：不发
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ADD_STORE_MONEY, params, getHeader(), listener);
    }

    /**
     * 更新会员卡信息
     *
     * @param cardTypeId
     * @param cardTypeName
     * @param supportIntegral
     * @param supportValue
     * @param supportCount
     * @param needPassord
     * @param discount
     * @param integralRatio
     * @param listener
     * @return
     */
    public static StringRequest updateCardType(int cardTypeId, String cardTypeName, boolean supportIntegral, boolean supportValue, boolean supportCount, boolean needPassord, int discount, int integralRatio, OnRequestListener listener)
    {
        Map<String, Object> params;
        Map<String, String> params2 = null;
        try {
            params = new HashMap<>();
            params2 = new HashMap<>();
            params.put("cardTypeId", cardTypeId);
            params.put("cardTypeName", cardTypeName);
            params.put("supportIntegral", supportIntegral);
            params.put("supportValue", supportValue);
            params.put("supportCount", supportCount);
            params.put("needPassord", needPassord);
            params.put("discount", discount);
            params.put("integralRatio", integralRatio);
            String json = FastJsonUtil.bean2Json(params);
            String info = Base64Util.encode(json.getBytes("UTF-8"));
            params2.clear();
            params2.put("info", info);
        } catch (Exception e) {
        }

        return getRequest(Url.CARD_UPDATE, params2, getHeader(), listener);
    }

    /**
     * 新增会员
     */
    public static StringRequest addAssociator(String name, String carNumber, String telephone, String carType, String cardNumber, OnRequestListener listener)
    {
        Map<String, Object> info;
        Map<String, String> params = null;

        try {
            info = new HashMap<>();
            params = new HashMap<>();
            info.put("name", name);
            info.put("carNumber", carNumber);
            info.put("phone", telephone);
            info.put("carType", carType);
            info.put("cardNumber", cardNumber);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.ASSOCIATOR_ADD, params, getHeader(), listener);
    }

    /**
     * 获取会员列表包含搜索
     */
    public static StringRequest getAssociatorList(String key, int page, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            if (!TextUtils.isEmpty(key)) {
                info.put("condition", key);
            }
            info.put("pageNo", page);
            info.put("pageSize", pageSize);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.ASSOCIATOR_EXT_LIST, params, getHeader(), listener);
    }

    /**
     * 获取会员总数量
     *
     * @param listener
     * @return
     */
    public static StringRequest getAssociatorCount(OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.ASSOCIATOR_EXT_COUNT, params, getHeader(), listener);
    }

    /**
     * 删除会员
     *
     * @param memberId
     * @param listener
     * @return
     */
    public static StringRequest deleteAssociator(int memberId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("memberId", memberId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ASSOCIATOR_DELETE, params, getHeader(), listener);
    }

    /**
     * 获取会员详情
     *
     * @param memberId
     * @param listener
     * @return
     */
    public static StringRequest getMenberDetails(int memberId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("memberId", memberId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.MENBER_BY_ID, params, getHeader(), listener);
    }

    /**
     * 3.8.1.	资金流水
     *
     * @param startTime
     * @param endTime
     * @param listener
     * @return
     */
    public static StringRequest getMoneyReport(int storeId,String startTime, String endTime, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("startTime", startTime);
            info.put("endTime", endTime);
            info.put("storeId", storeId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.MONEY_REPORT, params, getHeader(), listener);
    }

    /**
     * 3.8.1.	资金流水
     *
     * @param startTime
     * @param endTime
     * @param listener
     * @return
     */
    public static StringRequest getSaleReport(int storeId,String startTime, String endTime, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("startTime", startTime);
            info.put("endTime", endTime);
            info.put("storeId", storeId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.SALE_REPORT, params, getHeader(), listener);
    }

    /**
     * 3.3.2.	查询门店下所有项目
     *
     * @param listener
     * @return
     */
    public static StringRequest getStoreItem(int pageNo, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("pageNo", pageNo);
            info.put("pageSize", pageSize);
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ALL_STORE_ITEM, params, getHeader(), listener);
    }

    /**
     * 更新会员信息
     */
    public static StringRequest updateAssociator(long id, String name, String carNumber, String telephone, String carType, String cardNumber, OnRequestListener listener)
    {
        Map<String, Object> info;
        Map<String, String> params = null;
        try {
            info = new HashMap<>();
            params = new HashMap<>();
            info.put("memberId", id);
            info.put("name", name);
            info.put("carNumber", carNumber);
            info.put("phone", telephone);
            info.put("carType", carType);
            info.put("cardNumber", cardNumber);
            String json = FastJsonUtil.bean2Json(info);
            Log.e("json", json);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.ASSOCIATOR_UPDATE, params, getHeader(), listener);
    }

    /**
     * 会员储值
     *
     * @param associatorId
     * @param rechargeAmount
     * @param giftAmount
     * @param rechargeType
     * @param description
     * @param listener
     * @return
     */
    public static StringRequest addRecharge(int associatorId, int rechargeAmount, int giftAmount, int rechargeCount, int rechargeType, int modeOfPayment, String description, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("associatorId", associatorId);
            info.put("rechargeAmount", rechargeAmount);//充值金额
            info.put("giftAmount", giftAmount);//赠送金额
            info.put("rechargeCount", rechargeCount);//充值次数
            info.put("rechargeType", rechargeType);//充值类型 0:储值、1:充次
            info.put("modeOfPayment", modeOfPayment);   //付款类型 0"现金支付",1"刷卡支付",2"支付宝"
            info.put("description", description);//描述
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.ASSOCIATOR_RECHARGE, params, getHeader(), listener);
    }


    /**
     * 获取会员充值历史列表
     *
     * @param memberId
     * @param listener
     * @return
     */
    public static StringRequest getRecordList(int memberId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("memberId", memberId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.STORE_MONEY_RECORD, params, getHeader(), listener);
    }

    /**
     * 获取会员充值历史列表
     *
     * @param associatorId
     * @param listener
     * @return
     */
    public static StringRequest getRechargeList(int associatorId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("associatorId", associatorId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.ASSOCIATOR_RECHARGE_LIST, params, getHeader(), listener);
    }

    /**
     * 会员兑换积分
     *
     * @param cardId
     * @param productId
     * @param integral
     * @param description
     * @param listener
     * @return
     */
    public static StringRequest exchangeIntegral(String cardId, int productId, int integral, String description, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("cardId", cardId);
            info.put("productId", productId);
            info.put("integral", integral);
            info.put("description", description);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.ASSOCIATOR_EXC_INTEGRAL, params, getHeader(), listener);
    }

    /**
     * 3.4.1.	根据套餐名关键字查询门店下套餐
     *
     * @param listener
     * @return
     */
    public static StringRequest getComboList(String key, int pageNo, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            if (!TextUtils.isEmpty(key)) {
                info.put("key", key);
            }
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("pageNo", pageNo);
            info.put("pageSize", pageSize);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.COMBO_LIST, params, getHeader(), listener);
    }

    /**
     * 挂单
     *
     * @param cartName
     * @param associatorId
     * @param totalValue
     * @param accountId
     * @param description
     * @param gcList
     * @param listener
     * @return
     */
    public static StringRequest cartPutUp(String cartName, int associatorId, double totalValue, String accountId, String description, List<GoodsCart> gcList, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("cartName", cartName);
            info.put("associatorId", associatorId);
            info.put("totalValue", totalValue);
            info.put("accountId", accountId);
            info.put("description", description);
            info.put("cartDetails", gcList);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.CART_PUT_UP, params, getHeader(), listener);
    }

    /**
     * 获取挂单详情
     *
     * @param cartId
     * @param listener
     * @return
     */
    public static StringRequest getCartDetails(int cartId, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("cartId", cartId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.CART_DETAIL, params, getHeader(), listener);
    }

    /**
     * 获取挂单列表
     *
     * @param page
     * @param pageSize
     * @param listener
     * @return
     */
    public static StringRequest getExtCartList(int page, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("page", page);
            info.put("pageSize", pageSize);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.CART_LIST, params, getHeader(), listener);
    }

    /**
     * 结算
     *
     * @param associatorId
     * @param receivableValue 应收
     * @param paidValue       实收
     * @param modeOfPay       支付方式
     * @param billDetails     物品数组
     * @param listener
     * @return
     */
    public static StringRequest cartBillCheck(int cartId, int associatorId, double receivableValue, double paidValue, int modeOfPay, int customerType, List<BillDetail> billDetails, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;
        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("associatorId", associatorId);
            info.put("receivableValue", receivableValue);
            info.put("paidValue", paidValue);
            info.put("modeOfPay", modeOfPay);
            info.put("description", "");
            info.put("customerType", customerType);
            info.put("billDetails", billDetails);
            info.put("cartId", cartId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(Url.CART_BILL_CHECK, params, getHeader(), listener);
    }

    /**
     * 收银销售
     *
     * @param begin
     * @param end
     * @param listener
     * @return
     */
    public static StringRequest saleHistory(String type, String begin, String end, OnRequestListener listener)
    {
        String url = Url.ANALYSIS_TOTAL_SALE;
        Map<String, String> params = new HashMap<>();
        params.put("begin", begin);
        params.put("end", end);
        try {
            String json = FastJsonUtil.bean2Json(params);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRequest(url, params, getHeader(), listener);
    }


    /**
     * 获取会员消费统计
     *
     * @param associatorId
     * @param begin
     * @param end
     * @param customerType
     * @param flag
     * @param listener
     * @return
     */
    public static StringRequest getBillExtcount(int associatorId, String begin, String end, int customerType, int flag, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> info;

        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("begin", begin);
            info.put("end", end);
            info.put("flag", flag);
            info.put("associatorId", associatorId);
            info.put("customerType", customerType);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.BILL_GETEXTCOUNT, params, getHeader(), listener);
    }

    /**
     * 订单列表(7.10)
     *
     * @param pageNo
     * @param pageSize
     * @param listener
     * @return
     */
    public static StringRequest getOrderList(int pageNo, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        try {
            params = new HashMap<>();
            JSONObject info = new JSONObject();
            info.put("storeId", CacheUtil.getmInstance().getUser().getStoreId());
            info.put("pageNo", pageNo);
            info.put("pageSize", pageSize);
            params.put("info", Base64Util.encode(info.toString().getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ORDER_LIST, params, getHeader(), listener);
    }


    /**
     * @param billId
     * @param listener
     */
    public static StringRequest cancelBill(int billId, OnRequestListener listener)
    {

        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, String> params = new HashMap<String, String>();
        param.put("billId", billId);
        String json = FastJsonUtil.bean2Json(param);
        try {
            json = Base64Util.encode(json.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        param.clear();
        params.put("info", json);
        return getRequest(Url.BILL_CANCEL, params, getHeader(), listener);
    }

    /**
     * 用户信息
     *
     * @param userName
     * @param listener
     * @return
     */
    public static StringRequest userInfo(String userName, OnRequestListener listener)
    {

        Map<String, String> params = null;
        Map<String, Object> info;

        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("userName", userName);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.USER_INFO, params, getHeader(), listener);
    }

    /**
     * 门店信息2
     *
     * @param storeId
     * @param listener
     * @return
     */
    public static StringRequest storeInfo(Long storeId, OnRequestListener listener)
    {

        Map<String, String> params = null;
        Map<String, Object> info;

        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("storeId", storeId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.STORE_INFO, params, getHeader(), listener);
    }

    /**
     * 门店信息2
     *
     * @param storeId
     * @param listener
     * @return
     */
    public static StringRequest findStoreEmployee(Long storeId, OnRequestListener listener)
    {

        Map<String, String> params = null;
        Map<String, Object> info;

        try {
            params = new HashMap<>();
            info = new HashMap<>();
            info.put("storeId", storeId);
            String json = FastJsonUtil.bean2Json(info);
            json = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.FIND_STORE_EMPLOYEE, params, getHeader(), listener);
    }

    /**
     * 更新店铺信息
     *
     * @param storeName
     * @param telephone
     * @param storeAddr
     * @param listener
     * @return
     */
    public static StringRequest updateStoreInfo(String storeName, String telephone, String storeAddr, OnRequestListener listener)
    {
        Map<String, String> params = null;
        String info = null;
        try {
            params = new HashMap<>();
            params.put("telephone", telephone);
            params.put("storeName", storeName);
            params.put("storeAddr", storeAddr);
            String json = FastJsonUtil.bean2Json(params);
            info = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.STORE_UPDATE, params, getHeader(), listener);
    }

    /**
     * 修改密码
     * @param userName
     * @param oldPassword
     * @param newPassword
     * @param listener
     * @return
     */
    public static StringRequest changePassword(String userName, String oldPassword, String newPassword, OnRequestListener listener)
    {
        Map<String, String> params = null;
        String info = null;
        try {
            params = new HashMap<>();
            params.put("userName", userName);
            params.put("oldPassword", oldPassword);
            params.put("newPassword", newPassword);
            String json = FastJsonUtil.bean2Json(params);
            info = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.CHANGE_PASSWORD, params, getHeader(), listener);
    }

    /**
     * 新增店员
     * @param userName
     * @param newPassword
     * @param listener
     * @return
     */
    public static StringRequest addEmployee(Long storeId, String userName, String newPassword,String phone,Long right, OnRequestListener listener)
    {
        Map<String, String> params = null;
        String info = null;
        try {
            params = new HashMap<>();
            params.put("storeId", storeId+"");
            params.put("userName", userName);
            params.put("newPassword", newPassword);
            params.put("phone", phone);
            params.put("right", right+"");
            String json = FastJsonUtil.bean2Json(params);
            info = Base64Util.encode(json.getBytes("UTF-8"));
            params.clear();
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.ADD_EMPLOYEE, params, getHeader(), listener);
    }

    /**
     * 订单退货
     *
     * @param billDetailsId
     * @param returnReason
     * @param listener
     * @return
     */
    public static StringRequest returnSaleBillGoods(int billDetailsId, int returnReason, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Integer> objMap = null;
        String info = null;
        try {
            params = new HashMap<>();
            objMap = new HashMap<>();
            objMap.put("billDetailsId", billDetailsId);
            objMap.put("returnReason", returnReason);
            String json = FastJsonUtil.bean2Json(objMap);
            info = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(Url.BILL_SALES_RETURN, params, getHeader(), listener);
    }


    /**
     * 报表分析
     *
     * @param begin
     * @param end
     * @param page
     * @param pageSize
     * @param listener
     * @return
     */
    public static StringRequest getSalesAnalyTotal(String url, String begin, String end, int page, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> objMap = null;
        String info = null;
        try {
            params = new HashMap<>();
            objMap = new HashMap<>();
            objMap.put("begin", begin);
            objMap.put("end", end);
            if (page != 0) {
                objMap.put("page", page);
            }
            if (pageSize != 0) {
                objMap.put("pageSize", pageSize);
            }

            String json = FastJsonUtil.bean2Json(objMap);
            info = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(url, params, getHeader(), listener);
    }


    /**
     * 报表分析
     *
     * @param begin
     * @param end
     * @param page
     * @param pageSize
     * @param listener
     * @return
     */
    public static StringRequest getSalesAnalyGrossales(String url, String begin, String end, int page, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> objMap = null;
        String info = null;
        try {
            params = new HashMap<>();
            objMap = new HashMap<>();
            objMap.put("begin", begin);
            objMap.put("end", end);
            if (page != 0) {
                objMap.put("page", page);
            }
            if (pageSize != 0) {
                objMap.put("pageSize", pageSize);
            }

            String json = FastJsonUtil.bean2Json(objMap);
            info = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(url, params, getHeader(), listener);
    }


    /**
     * 报表分析
     *
     * @param begin
     * @param end
     * @param page
     * @param pageSize
     * @param listener
     * @return
     */
    public static StringRequest getSalesAnalyCount(String url, String begin, String end, int page, int pageSize, OnRequestListener listener)
    {
        Map<String, String> params = null;
        Map<String, Object> objMap = null;
        String info = null;
        try {
            params = new HashMap<>();
            objMap = new HashMap<>();
            objMap.put("begin", begin);
            objMap.put("end", end);
            if (page != 0) {
                objMap.put("page", page);
            }
            if (pageSize != 0) {
                objMap.put("pageSize", pageSize);
            }

            String json = FastJsonUtil.bean2Json(objMap);
            info = Base64Util.encode(json.getBytes("UTF-8"));
            params.put("info", info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRequest(url, params, getHeader(), listener);
    }

}
