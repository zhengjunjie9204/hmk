package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.StaffUserAdapter;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.bean.UserInfo;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.FastJsonUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * 用户信息
 */
public class AccountStaffListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private List<UserInfo> mList = new ArrayList<>();
    private StaffUserAdapter mAdapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_staff_list);
        setTitleText("查看店员");
        setSubmit(getString(R.string.app_button_new));
        final User user = CacheUtil.getmInstance().getUser();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        new Thread(){
            @Override
            public void run() {
                super.run();
                Api.findStoreEmployee((long)user.getStoreId(),listener);
            }
        }.start();

        if (user == null){
            defaultFinish();
            return;
        }
        ListView lv_data = (ListView) findViewById(R.id.lv_data);
        lv_data.setOnItemClickListener(this);
        mAdapter = new StaffUserAdapter(this, mList);
        lv_data.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        index = position;
        UserInfo user = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            JSONObject obj = JSON.parseObject(result.getResult());
            String info = obj.getString("employees");
            List<UserInfo> UserInfo = FastJsonUtil.json2List(info, UserInfo.class);
            mList.addAll(UserInfo);
            mAdapter.notifyDataSetChanged();
        }


    };
    private static BaseRequest.OnRequestListener listener = new BaseRequest.OnRequestListener() {

        @Override
        public void onSuccess(Result result) {
            EventCenter.getInstance().post(result);
        }

        @Override
        public void onError(String errorCode, String message) {
            EventCenter.getInstance().post(message);
        }
    };
}
