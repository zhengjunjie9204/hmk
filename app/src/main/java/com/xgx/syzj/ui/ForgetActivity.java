package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.UserDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;

/**
 * 忘记密码
 */
public class ForgetActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_login,ll_forget;
    private EditText et_re_psw,et_psw;
    private String phone;
    private String code;
    private String pswStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_three);
        setTitleText(getString(R.string.forget_psw_title));
        Button btn_sure = (Button) findViewById(R.id.btn_sure);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        et_psw= (EditText) findViewById(R.id.et_psw);
        et_re_psw= (EditText) findViewById(R.id.et_re_psw);
        ll_login = (LinearLayout) findViewById(R.id.ll_log);
        ll_forget= (LinearLayout) findViewById(R.id.ll_forget);
        Bundle bundle=getIntent().getExtras();
        phone=bundle.getString("phone");
        code=bundle.getString("code");

        btn_sure.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler(){

        public void onEvent(Result result){
            if(result.geteCode()== UserDataModel.FORGET_PASSWORD){

                if("修改成功，请重新登陆".equals(result.getMessage())){
                    showShortToast(R.string.forget_psw_reset_success);
                    ll_login.setVisibility(View.VISIBLE);
                    ll_forget.setVisibility(View.GONE);
                }else{
                    showShortToast("密码重置失败");
                }
            }
        }

        public void onEvent(String error){
            hideLoadingDialog();
            showShortToast(error);
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sure:
                if(checkInput()){
                    UserDataModel.forgetPsw(phone,code,pswStr);
                }
                break;
            case R.id.btn_login:
                finish();
                break;
        }
    }

    private boolean checkInput() {
        pswStr = et_psw.getText().toString().trim();
        String rePswStr = et_re_psw.getText().toString().trim();

        if (pswStr.length() < 6) {
            showShortToast(R.string.toast_reg_psw_less);
            return false;
        }
        if (!pswStr.equals(rePswStr)) {
            showShortToast(R.string.toast_reg_psw_same);
            return false;
        }
        return true;
    }
}
