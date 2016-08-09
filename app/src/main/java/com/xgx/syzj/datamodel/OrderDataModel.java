package com.xgx.syzj.datamodel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest.OnRequestListener;
import com.xgx.syzj.bean.OrderList;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.OrderListDataEvent;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ding
 */
public class OrderDataModel extends PagedListDataModel<OrderList> {
    private static byte code;
    public static final byte ORDER_DONE = 0x13;
    public static final byte ORDER_DETAIL = 0x14;
    private String key;
    private OrderListDataEvent data = new OrderListDataEvent();

    public OrderDataModel(int num) {
        mListPageInfo = new ListPageInfo<>(num);
    }

    public void setKey(String key) {
        this.key = key;
        data.dataList = null;
        data.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    @Override
    protected void doQueryData() {
        Api.getUnpayOrder(mListPageInfo.getPage(), mListPageInfo.getNumPerPage(), new OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                JSONObject object = JSON.parseObject(result.getResult());
                List<OrderList> list;
                if (result.getStatus() == 200) {
                    list = FastJsonUtil.json2List(object.getString("orders"), OrderList.class);
                } else {
                    list = new ArrayList<>();
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
            public void onError(String errorCode, String message)
            {
                setRequestFail();
                EventCenter.getInstance().post(message);
            }
        });
    }

    public static void setOrderDone(int payOrderId)
    {
        code = ORDER_DONE;
        Api.setOrderDone(payOrderId, listener);
    }
    public static void getOrderDetail(int payOrderId)
    {
        code = ORDER_DETAIL;
        Api.setOrderDone(payOrderId,listener);
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
}