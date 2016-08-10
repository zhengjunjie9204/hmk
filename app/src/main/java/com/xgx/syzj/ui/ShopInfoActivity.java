package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.StoreInfo;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.bean.UserInfo;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.FastJsonUtil;

import de.greenrobot.event.EventBus;

/**
 * 店铺基本信息
 *
 * @author zajo
 * @created 2015年08月12日 16:07
 */
public class ShopInfoActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv_stroename;
    private TextView tv_nickname;
    private TextView tv_phone;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_info);
        setTitleText(getString(R.string.shop_info_title));
        final User user = CacheUtil.getmInstance().getUser();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        new Thread(){
            @Override
            public void run() {
                super.run();
                Api.storeInfo((long)user.getStoreId(),listener);
            }
        }.start();

        if (user == null){
            defaultFinish();
            return;
        }
        initView();

        findViewById(R.id.ll_user).setOnClickListener(this);
    }

    private void initView() {
        tv_stroename = (TextView)findViewById(R.id.tv_stroename_name);
        tv_nickname = (TextView)findViewById(R.id.tv_nickname);
        tv_phone = (TextView)findViewById(R.id.tv_phone);
        tv_name = (TextView)findViewById(R.id.tv_name);

    }

    @Override
    public void onClick(View v) {
        gotoActivity(AccountStaffListActivity.class);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            JSONObject obj = JSON.parseObject(result.getResult());
            String info = obj.getString("info");
            StoreInfo storeInfo = FastJsonUtil.json2Bean(info, StoreInfo.class);
            tv_stroename.setText(storeInfo.getStoreName());
            tv_nickname.setText(storeInfo.getStorePosition());
            tv_phone.setText(storeInfo.getManagerPhone());
            tv_name.setText(storeInfo.getManagerName());
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
