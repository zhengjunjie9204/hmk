package com.xgx.syzj.datamodel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest.OnRequestListener;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.ProjectListDataEvent;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zajo
 * @created 2015年10月13日 10:06
 */
/**
 * @author zajo
 * @created 2015年10月13日 10:06
 */
public class ComboDataModel extends PagedListDataModel<Project> {
    private static byte code;
    private String key;
    private ProjectListDataEvent data = new ProjectListDataEvent();

    public ComboDataModel(int num) {
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
        Api.getComboList(key, mListPageInfo.getPage(), mListPageInfo.getNumPerPage(), new OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject object = JSON.parseObject(result.getResult());
                List<Project> list;
                if (result.getStatus() == 200) {
                    list = FastJsonUtil.json2List(object.getString("items"), Project.class);
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
}