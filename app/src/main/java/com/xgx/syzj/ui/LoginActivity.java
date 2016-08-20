package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.datamodel.UserDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;

/**
 * 登录
 *
 * @author zajo
 * @created 2015年08月06日 17:56
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_log,btn_forget;
    private EditText et_username,et_password;
    private String username,password;
    private TextView btn_demo;
    private boolean isToken=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();

//        et_username.setText("dianzhang1");
//        et_password.setText("dianzhang1");
        String token= SYZJApplication.getInstance().getSpUtil().get(Constants.SharedPreferencesClass.SP_TOKEN);
        if(!TextUtils.isEmpty(token)) {
            UserDataModel.loginByToken(token);
        }
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initView() {
        hideTopBar();
        btn_log = (Button) findViewById(R.id.btn_log);
//        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_forget = (Button) findViewById(R.id.btn_forget);
        btn_demo = (TextView) findViewById(R.id.btn_demo);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void initListener() {
        btn_log.setOnClickListener(this);
//        btn_reg.setOnClickListener(this);
        btn_forget.setOnClickListener(this);
        btn_demo.setOnClickListener(this);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler(){

        public void onEvent(Result result){
            hideLoadingDialog();
            JSONObject object= JSON.parseObject(result.getResult());
            Log.e("=",result.getResult());
            if(result.getStatus()==200&&"登录成功".equals(result.getMessage())) {
//                String userinfo=object.getString("userInfo").replace("[","").replace("]","");
                JSONObject userInfo = object.getJSONObject("userInfo");
                User user = new User();
                user.setStoreId(userInfo.getIntValue("storeId"));
                user.setStorePic(userInfo.getString("storePic"));
                user.setStoreName(userInfo.getString("storeName"));
                user.setStoreLogo(userInfo.getString("storeLogo"));
                user.setUserName(userInfo.getString("userName"));
                user.setToken(userInfo.getString("token"));
                user.setEmployeeId(userInfo.getIntValue("employeeId"));
//                for (int i = 0; i < userInfo.getJSONArray("roles").size(); i++) {
//                    user.setRoles(userInfo.getJSONArray("roles").getIntValue(i));
//                }
                CacheUtil.getmInstance().setUser(user);
                SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_PHONE, username);
                SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_PSW, password);
                SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_TOKEN, user.getToken());
                SYZJApplication.getInstance().getSpUtil().addInt(Constants.SharedPreferencesClass.SP_ROLES, user.getRoles());
                SYZJApplication.getInstance().getSpUtil().addInt(Constants.SharedPreferencesClass.SP_STORE_ID, user.getStoreId());
//                if(user.getRoles()==1){
//                    gotoActivity(BossActivity.class);
//                }else
                 {
                    gotoActivity(MainActivity.class);
                }
                defaultFinish();
            }else {
                if (isToken) {
                    showShortToast("登录已过期，请重新登录");
                } else {
                    showShortToast("用户名或密码不正确");
                }
            }
        }

        public void onEvent(String error){
            hideLoadingDialog();
            showShortToast(error);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        String sp_phone = SYZJApplication.getInstance().getSpUtil().get(Constants.SharedPreferencesClass.SP_PHONE);
        String sp_psw = SYZJApplication.getInstance().getSpUtil().get(Constants.SharedPreferencesClass.SP_PSW);
        if (sp_phone != "" && sp_psw != ""){
            et_username.setText(sp_phone);
            et_password.setText(sp_psw);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventHandler.tryToUnregister();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_log:
                if (checkInput()){
                    login();
                }
                break;
//            case R.id.btn_reg:
//                gotoActivity(RegisterActivity.class);
//                break;
            case R.id.btn_forget:
                gotoActivity(RegisterForgetActivity.class);
                break;
            case R.id.btn_demo:
                break;
            default:
                break;
        }
    }
    private boolean checkInput() {
        isToken=false;
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        boolean result = true;
        if (TextUtils.isEmpty(username)){
            showShortToast(getString(R.string.toast_log_phone_error));
            result = false;
        }
        if (password.length() < 6){
            showShortToast(getString(R.string.toast_reg_psw_less));
            result = false;
        }
        return result;
    }


    private void login(){
        showLoadingDialog(R.string.dialog_log_ing_text);
//        gotoActivity(MainActivity.class);
        UserDataModel.login(username, password);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().AppExit(this, false);
        }
        return super.onKeyDown(keyCode, event);
    }
}
