package com.xgx.syzj.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.FastJsonUtil;

import de.greenrobot.event.EventBus;

public class AccountModifyPasswordActivity extends BaseActivity {

    private EditText et_pwd_old;
    private EditText et_pwd_new;
    private EditText et_user_pwd;
    private User user;
    //新旧密码
    private String pwd_old;
    private String pwd_new;
    //确认密码
    private String user_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_modify_password);
        initView();
        initData();
        user = CacheUtil.getmInstance().getUser();
        if (user == null) {
            defaultFinish();
            return;
        }
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);

    }

    private void initData() {

    }

    private void initView() {
        et_pwd_old =(EditText) findViewById(R.id.et_pwd_old);
        et_pwd_new =(EditText) findViewById(R.id.et_pwd_new);
        et_user_pwd =(EditText) findViewById(R.id.et_user_pwd);
    }


    public void onSure(View v){
        pwd_old = et_pwd_old.getText().toString();
        pwd_new = et_pwd_new.getText().toString();
        user_pwd = et_user_pwd.getText().toString();
        if(pwd_new.equals(user_pwd)){
            showLoadingDialog(R.string.loading_update_password);
            new Thread(){
                @Override
                public void run() {
                super.run();
                Api.changePassword(user.getUserName(),pwd_old,pwd_new,listener);
            }
        }.start();
        }
        else{
            et_user_pwd.setText("");
            Toast.makeText(AccountModifyPasswordActivity.this,"请重新确认密码",Toast.LENGTH_SHORT).show();

        }
    }
    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            String message = result.getMessage();
            showShortToast(message);
            if(result.getStatus()==200){
                    finish();
            }
        }
        public void onEvent(String error){
            hideLoadingDialog();
            showShortToast(error);
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
