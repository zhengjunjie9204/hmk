package com.xgx.syzj.datamodel;

import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.CountItemsBean;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Product;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.BaseDataEvent;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class SellFinishModel extends BaseDataModel {
    private BaseDataEvent<Project> data = new BaseDataEvent<>();
    private int OrdId;
    private JSONObject json;
    private static byte code;
    public static final byte orderCreate = 0x10;
    public static final byte orderPayProduct = 0x11;
    public static final byte editOrder = 0x12;
    public static final byte orderPayItem = 0x13;
    public static final byte getOrderDetail = 0x14;

    public void getOrderDetail(int OrdId){
        code=getOrderDetail;
        Api.getOrderDetail(OrdId,listener);
    }
    public void orderCreate(int memberId, String distance, String fee, JSONArray productList, JSONArray itemList){
        code=orderCreate;
        Api.orderCreate(memberId, distance, fee, productList, itemList, listener);
    };
    public void orderPayProduct(int memberId, int payTwiceFlag, int payType, String fee, String authCode, JSONArray productList){
        code=orderPayProduct;
        Api.orderPayProduct(memberId,payTwiceFlag,payType,fee,authCode,productList,listener);
    }
    public void editOrder(int payOrderId,JSONArray itemList,JSONArray productList){
        code=editOrder;
        Api.editOrder(payOrderId, itemList, productList,listener);
    }
    public void orderPayItem(int payOrderId, int payTwiceFlag, int payType, String authCode){
        code=orderPayItem;
        Api.orderPayItem(payOrderId,payTwiceFlag,payType,authCode,listener);
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
}
