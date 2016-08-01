package com.xgx.syzj.datamodel;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest.OnRequestListener;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.stockRecordHistory;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.GoodsListDataEvent;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zajo
 * @created 2015年10月13日 10:06
 */
public class GoodsDataModel extends PagedListDataModel<Goods> {

    public static final byte ADD_SUCCESS = 0x10;
    public static final byte DELETE_SUCCESS = 0x11;
    public static final byte MODIFY_SUCCESS = 0x12;
    public static final byte IN_OUT_SUCCESS = 0x13;
    public static final byte GET_COUNT_SUCCESS = 0x14;
    public static final byte DELETE_SUCCESS_TWO=0x15;
    public static final byte QUERY_STORAGE_HISTORY=0x16;

    private static byte code;
    private String key;
    private String brand;
    private String categoryId;
    private GoodsListDataEvent data = new GoodsListDataEvent();

    public GoodsDataModel(int num) {
        mListPageInfo = new ListPageInfo<>(num);
    }

    public void setKey(String key) {
        this.key = key;
        data.dataList = null;
        data.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }
    public void setBrand(String brand) {
        this.brand = brand;
        data.dataList = null;
        data.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        data.dataList = null;
        data.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    @Override
    protected void doQueryData() {
        Api.getProductsList(key, categoryId, mListPageInfo.getPage(), mListPageInfo.getNumPerPage(), new OnRequestListener() {

            @Override
            public void onSuccess(Result result) {
                JSONObject object= JSON.parseObject(result.getResult());
                List<Goods> list;
                if(result.getStatus()==200) {
                    list = FastJsonUtil.json2List(object.getString("products"), Goods.class);
                }else {
                    list=new ArrayList<>();
                }
                data.dataList = list;
                if (list != null && list.size() > 0) {
                    if (list.size() >= mListPageInfo.getNumPerPage()) {
                        data.hasMore = true;
                    } else {
                        data.hasMore = false;
                    }
                } else {
                    data.hasMore = false;
                    list = new ArrayList<>();
                }
                setRequestResult(data.dataList, data.hasMore);

                EventCenter.getInstance().post(list);
            }


            @Override
            public void onError(String errorCode, String message) {
                setRequestFail();
                EventCenter.getInstance().post(message);
            }
        });
        Api.getProductsListByBand(brand, categoryId, mListPageInfo.getPage(), mListPageInfo.getNumPerPage(), new OnRequestListener() {

            @Override
            public void onSuccess(Result result) {
                JSONObject object= JSON.parseObject(result.getResult());
                List<Goods> list;
                if(result.getStatus()==200) {
                    list = FastJsonUtil.json2List(object.getString("products"), Goods.class);
                }else {
                    list=new ArrayList<>();
                }
                data.dataList = list;
                if (list != null && list.size() > 0) {
                    if (list.size() >= mListPageInfo.getNumPerPage()) {
                        data.hasMore = true;
                    } else {
                        data.hasMore = false;
                    }
                } else {
                    data.hasMore = false;
                    list = new ArrayList<>();
                }
                setRequestResult(data.dataList, data.hasMore);

                EventCenter.getInstance().post(list);
            }
            @Override
            public void onError(String errorCode, String message) {
                setRequestFail();
                EventCenter.getInstance().post(message);
            }
        });

    }

    private static OnRequestListener listener = new OnRequestListener() {

        @Override
        public void onSuccess(Result result) {
            result.seteCode(code);
            EventCenter.getInstance().post(result);

        }

        @Override
        public void onError(String errorCode, String message) {
            EventCenter.getInstance().post(message);
        }
    };

    public static void addGoods(String barcode, String productName, String categoryId, String inputPrice, String sellingPrice, String quantity, String specification, String brand,String unitid,String image) {
        code = ADD_SUCCESS;
        Api.addProducts(barcode, productName, categoryId, inputPrice, sellingPrice, quantity, specification, brand,unitid,image, listener);
    }

    public static void queryhistoryData(int productId){
        code=QUERY_STORAGE_HISTORY;
        Api.stockRecordHistory(productId, new OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject object= JSON.parseObject(result.getResult());//stockRecordHistory
                List<stockRecordHistory> list;
                if(result.getStatus()==200) {
                    list = FastJsonUtil.json2List(object.getString("stockRecordHistory"), stockRecordHistory.class);
                }else {
                    list=new ArrayList<>();
                }

                EventCenter.getInstance().post(list);
            }

            @Override
            public void onError(String errorCode, String message) {
                EventCenter.getInstance().post(message);
            }
        });
    }
    public static void deleteGoods(long uId) {
        code = DELETE_SUCCESS;
        Api.deleteProducts(uId, listener);
    }


    public static void modifyGoods(int productId, String barcode, String productName, String categoryId, String inputPrice, String sellingPrice, String quantity, String specification,String brand, String unitid,String image) {
        code = MODIFY_SUCCESS;
        Api.updateProducts(productId, barcode, productName, categoryId, inputPrice, sellingPrice, quantity, specification, brand,unitid,image, listener);
    }

    public static void inAndOutGoods(int flag, int productId, int stockCount, String description) {
        code = IN_OUT_SUCCESS;
        Api.mIntoAndOutProducts(flag, productId, stockCount, description, listener);
    }

    public static void getProductsCount(String key) {
        code = GET_COUNT_SUCCESS;
        Api.getProductsCount(key, listener);
    }
}
