package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.UserDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.StrUtil;

/**
 * @author zajo
 * @created 2015年10月08日 11:46
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_header;
    private EditText et_phone,et_code,et_psw,et_re_psw;
    private Button btn_code,btn_reg;
    private String phoneStr, pswStr, rePswStr, codeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initView(){
        setTitleText(getString(R.string.log_reg_button));
        iv_header = (ImageView) findViewById(R.id.iv_header);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_re_psw = (EditText) findViewById(R.id.et_re_psw);
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_reg = (Button) findViewById(R.id.btn_reg);

        iv_header.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        btn_reg.setOnClickListener(this);

        if (Constants.DEBUG){
            et_code.setText("1234");
        }
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler(){

        public void onEvent(Result result){
            hideLoadingDialog();
            //保存用户信息
            SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_PHONE, phoneStr);
            SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_PSW, pswStr);
            gotoActivity(RegisterShopActivity.class);
            defaultFinish();
        }

        public void onEvent(String error){
            hideLoadingDialog();
            showShortToast(error);
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg:
                if (checkInput()) {
                    register();
                }
                break;
        }
    }

    private boolean checkInput() {
        pswStr = et_psw.getText().toString().trim();
        rePswStr = et_re_psw.getText().toString().trim();
        codeStr = et_code.getText().toString().trim();
        phoneStr = et_phone.getText().toString().trim();
        if (!StrUtil.isMobileNo(phoneStr)){
            showShortToast(R.string.toast_reg_phone_error);
            return false;
        }
        if (TextUtils.isEmpty(codeStr) || codeStr.length() > 4){
            showShortToast(R.string.toast_reg_code_error);
            return false;
        }
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

    private void register() {
        showLoadingDialog(R.string.toast_reg_loading_text);
        UserDataModel.register(phoneStr, pswStr, codeStr);
    }
}
