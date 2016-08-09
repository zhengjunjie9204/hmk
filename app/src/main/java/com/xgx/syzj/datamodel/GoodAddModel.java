package com.xgx.syzj.datamodel;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.GoodsListDataEvent;
import com.xgx.syzj.ui.SYZJApplication;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class GoodAddModel extends PagedListDataModel<Goods> {
    private String key;
    private String brand;
    private static byte code;
    public static final byte ADDPRODUCT = 0x10;
    private GoodsListDataEvent data = new GoodsListDataEvent();
    public GoodAddModel(int num) {
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
    @Override
    protected void doQueryData() {

    }
    public static void addProductByStore(long storeId){
        Api.addProductByStore(storeId, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject object= JSON.parseObject(result.getResult());//stockRecordHistory
                List<Goods> list;
                if(result.getStatus()==200) {
                    list = FastJsonUtil.json2List(object.getString("products"), Goods.class);
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
    private static BaseRequest.OnRequestListener listener = new BaseRequest.OnRequestListener() {

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
    public static void addProduct(List<Goods> goods){
        code=ADDPRODUCT;
        int storeid = SYZJApplication.getInstance().getSpUtil().getInt("SP_STORE_ID");
        for (Goods g : goods) {
            Api.addProductToStore(storeid,g.getProductId(),listener);
        }
    }
}
