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
import com.xgx.syzj.bean.User;
import com.xgx.syzj.bean.userInfo;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.FastJsonUtil;

import de.greenrobot.event.EventBus;

/**
 * 用户信息
 *
 * @author zajo
 * @created 2015年08月27日 14:21
 */
public class AccountUserInfoActivity extends BaseActivity{

    private TextView tv_user_phone;
    private TextView et_user_name;
    private TextView tv_user_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_user_info);
        setTitleText(getString(R.string.account_manage_user_info_title));
        initView();
        final User user = CacheUtil.getmInstance().getUser();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        new Thread(){
            @Override
            public void run() {
                super.run();
                Api.userInfo(user.getUserName(),listener);
            }
        }.start();

        if (user == null){
            defaultFinish();
            return;
        }
    }

    private void initView() {
        tv_user_phone =(TextView)findViewById(R.id.tv_user_phone);
        et_user_name =(TextView)findViewById(R.id.et_user_name);
        tv_user_auth =(TextView)findViewById(R.id.tv_user_auth);

    }

    public void onModifyPsw(View view){
        gotoActivity(AccountModifyPasswordActivity.class);

    }
    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            JSONObject obj = JSON.parseObject(result.getResult());
            String info = obj.getString("info");
            userInfo userInfo = FastJsonUtil.json2Bean(info, com.xgx.syzj.bean.userInfo.class);
            tv_user_phone.setText(userInfo.getPhone());
            et_user_name.setText(userInfo.getName());
            tv_user_auth.setText(userInfo.getRight());

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
