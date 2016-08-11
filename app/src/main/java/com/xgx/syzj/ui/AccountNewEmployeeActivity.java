package com.xgx.syzj.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import com.xgx.syzj.widget.CustomAlertDialog;

import java.security.Permission;
import java.util.List;

import de.greenrobot.event.EventBus;

public class AccountNewEmployeeActivity extends BaseActivity {

    private EditText userName;
    private EditText phone;
    private EditText password;
    private EditText comfit;
    private TextView permission;
    private long PERMISSION_ID=4;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_new_employee);
        initView();
        user = CacheUtil.getmInstance().getUser();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);


    }
    private void initView() {
        userName = (EditText)findViewById(R.id.et_username);
        phone = (EditText) findViewById(R.id.et_pwd_phone);
        password = (EditText) findViewById(R.id.et_user_pwd);
        comfit = (EditText) findViewById(R.id.et_user_pwd_comfit);
        permission =(TextView) findViewById(R.id.tv_permission);

    }
    //选择员工权限
    public void choicePermission(View v){
        CustomAlertDialog.choicePermission(this, new CustomAlertDialog.IAlertListDialogItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0 :
                        permission.setText("店长");
                        PERMISSION_ID=3;
                        break;
                    case 1:
                        permission.setText("员工");
                        PERMISSION_ID=4;
                        break;
                }
            }
        });

    }
    //确定按钮
    public void onSure(View v){
        final String name = userName.getText().toString();
        final String userPhone = phone.getText().toString();
        final String psw = password.getText().toString();
        String PswComfit = comfit.getText().toString();
        if(PswComfit.equals(psw)) {
            showLoadingDialog(R.string.loading_add_employee);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Api.addEmployee((long) user.getStoreId(), name, psw, userPhone, PERMISSION_ID, listener);
                }
            }.start();

            if (user == null) {
                defaultFinish();
                return;
            }
        }else{
            showShortToast("请重新确认密码");
            comfit.setText("");
        }
    }
    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            String message = result.getMessage();
            showShortToast(message);
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
