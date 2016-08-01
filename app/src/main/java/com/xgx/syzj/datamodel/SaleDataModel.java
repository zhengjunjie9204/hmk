package com.xgx.syzj.datamodel;

import android.content.Context;

import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.SaleHistory;
import com.xgx.syzj.event.BaseDataEvent;
import com.xgx.syzj.ui.AnalysisActivity;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;
import de.greenrobot.event.EventBus;

/**
 * 销售
 *
 * @author zajo
 * @created 2016年01月18日 15:24
 */
public class SaleDataModel extends PagedListDataModel<SaleHistory> {

    private String TYPE = "";//请求类型，全部、会员、散客
    private String begin, end;//开始，结束时间
    private BaseDataEvent<SaleHistory> data = new BaseDataEvent();

    public SaleDataModel(int num, String type) {
        mListPageInfo = new ListPageInfo<>(num);
        TYPE = type;
    }

    public void setTime(String begin, String end) {
        this.begin = begin;
        this.end = end;
        data.dataList = null;
        data.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    @Override
    protected void doQueryData() {
        Api.saleHistory(TYPE, begin, end, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
/*                List<SaleHistory> list = FastJsonUtil.json2List(result.getResult(), SaleHistory.class);
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
                setRequestResult(data.dataList, data.hasMore);*/
                result.seteCode(AnalysisActivity.ASSOCIATOR_SURVEY);
                EventBus.getDefault().post(result);
//                EventCenter.getInstance().post(list);
            }

            @Override
            public void onError(String errorCode, String message) {
                setRequestFail();
                EventBus.getDefault().post(message);
            }
        });
    }
}
