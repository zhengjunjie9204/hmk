package com.xgx.syzj.datamodel;

import android.util.JsonReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.BillGoodsDetailbean;
import com.xgx.syzj.bean.BillListItemBean;
import com.xgx.syzj.bean.ConsumeHistory;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.BaseDataEvent;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2016/3/11 16:58.
 */
public class BillListRecordModel extends PagedListDataModel<ConsumeHistory> {
    private BaseDataEvent<ConsumeHistory> data = new BaseDataEvent<>();
    private int memberId;

    public BillListRecordModel(int num, int memberId) {
        mListPageInfo = new ListPageInfo<>(num);
        this.memberId = memberId;
    }

    public void setTime(String beginTime) {
        data.dataList = null;
        data.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    @Override
    protected void doQueryData() {
        Api.getConsumeHistory(memberId, mListPageInfo.getPage(), mListPageInfo.getNumPerPage(), new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject object = JSON.parseObject(result.getResult());
                List<ConsumeHistory> list;
                if (result.getStatus() == 200) {
                    EventCenter.getInstance().post(result);
                    list = FastJsonUtil.json2List(object.getString("records"), ConsumeHistory.class);
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
                EventCenter.getInstance().post(data);
            }

            @Override
            public void onError(String errorCode, String message) {
                setRequestFail();
                EventCenter.getInstance().post(message);
            }
        });
    }

    public void getMemberPayRecord(int associatorId, String begin, String end, int customerType, int flag) {
        Api.getBillExtcount(associatorId, begin, end, customerType, flag, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                Map<String, Object> map = new HashMap<String, Object>();
                JsonReader reader;
                if (result.getResult() != null)
                    try {
                        reader = new JsonReader(new StringReader(result.getResult()));
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String name = reader.nextName();
                            if (name.equals("count")) {
                                map.put("count", reader.nextInt());
                            } else {
                                reader.skipValue();
                            }
                        }
                        reader.endObject();
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                EventCenter.getInstance().post(map);
            }

            @Override
            public void onError(String errorCode, String message) {
                EventCenter.getInstance().post(message);
            }
        });
    }
}
