package com.xgx.syzj.datamodel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.OrderList;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.OrderListDataEvent;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by sam on 2016/3/11 16:58.
 */
public class SaleListRecordModel extends PagedListDataModel<OrderList> {
    public static final byte DELETE_SALE_RECORD = 0x11;
    public static final byte ORDER_DETAILS = 0x12;
    private static byte code;
    private String TYPE = "";//请求类型，全部、会员、散客
    private OrderListDataEvent data = new OrderListDataEvent();


    public SaleListRecordModel(int num)
    {
        mListPageInfo = new ListPageInfo<>(num);
    }
    public SaleListRecordModel(int num, String flag)
    {
        mListPageInfo = new ListPageInfo<>(num);
    }

    //设置用户的类型： 普通消费者或：1，会员消费者 ：2，所有消费者 0;
    public void setCustomerType(String type)
    {
        this.TYPE = type;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    public void setTime(String beginTime, String endTime)
    {
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    @Override
    protected void doQueryData()
    {

    }
    public void  payOrder(String key,String minMoney,String maxMoney,String startTime,String endTime){
        Api.getOrderFilter(key,minMoney,maxMoney,startTime,endTime,mListPageInfo.getPage(), mListPageInfo.getNumPerPage(), new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                JSONObject object = JSON.parseObject(result.getResult());
                List<OrderList> list;
                if (result.getStatus() == 200) {
                    list = FastJsonUtil.json2List(object.getString("payOrders"), OrderList.class);
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
                EventBus.getDefault().post(message);
            }
        });
    }

    //作废订单
    public void setOrderCancel(String orderId)
    {
        this.code = DELETE_SALE_RECORD;
        Api.setOrderCancel(orderId, listener);
    }

    //订单详情
    public static void getOrderDetail(int payOrderId)
    {
        code = ORDER_DETAILS;
        Api.getOrderDetail(payOrderId, listener);
    }

    public static void doRequst(int billId)
    {
        Api.cancelBill(billId, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                result.seteCode(DELETE_SALE_RECORD);
                EventBus.getDefault().post(result);
            }

            @Override
            public void onError(String errorCode, String message)
            {
                EventBus.getDefault().post(message);
            }
        });
    }

    private static BaseRequest.OnRequestListener listener = new BaseRequest.OnRequestListener() {

        @Override
        public void onSuccess(Result result)
        {
            result.seteCode(code);
            EventCenter.getInstance().post(result);
        }

        @Override
        public void onError(String errorCode, String message)
        {
            EventCenter.getInstance().post(message);
        }
    };

}
