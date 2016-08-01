package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.widget.CustomProgressDialog;

/**
 * 完成注册
 *
 * @author zajo
 * @created 2015年08月10日 11:38
 */
public class RegisterThreeActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_phone;
    private EditText et_psw, et_re_psw, et_shop, et_name;
    private Button btn_reg;

    private String phoneStr, pswStr, rePswStr, shopStr, nameStr;
    private CustomProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_three);

        findViewById(R.id.tv_one).setBackgroundColor(getResources().getColor(R.color.light_color));
        findViewById(R.id.iv_two).setBackgroundResource(R.mipmap.registered_two_two);
        findViewById(R.id.tv_two).setBackgroundColor(getResources().getColor(R.color.light_color));
        findViewById(R.id.iv_three).setBackgroundResource(R.mipmap.registered_three_three);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_re_psw = (EditText) findViewById(R.id.et_re_psw);
        et_shop = (EditText) findViewById(R.id.et_shop);
        et_name = (EditText) findViewById(R.id.et_name);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_reg.setEnabled(false);

        et_psw.setText("123456");
        et_re_psw.setText("123456");
        et_name.setText("新关系");

        et_psw.addTextChangedListener(textWatcher);
        et_re_psw.addTextChangedListener(textWatcher);
        et_shop.addTextChangedListener(textWatcher);

        Bundle bundle = getIntent().getExtras();
        tv_phone.setText(bundle.getString("phone"));
        btn_reg.setOnClickListener(this);

        phoneStr = SYZJApplication.getInstance().getSpUtil().get(Constants.SharedPreferencesClass.SP_PHONE);
        loadingDialog = CustomProgressDialog.createDialog(this).setMessage(getString(R.string.toast_reg_loading_text));
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            pswStr = et_psw.getText().toString().trim();
            rePswStr = et_re_psw.getText().toString().trim();
            shopStr = et_shop.getText().toString().trim();
            nameStr = et_name.getText().toString().trim();
            if (!TextUtils.isEmpty(pswStr) && !TextUtils.isEmpty(rePswStr) && !TextUtils.isEmpty(shopStr)) {
                btn_reg.setEnabled(true);
                btn_reg.setBackgroundResource(R.drawable.button_login_bg);
            } else {
                btn_reg.setEnabled(false);
                btn_reg.setBackgroundResource(R.drawable.button_un_enable_bg);
            }
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
        loadingDialog.show();
//        Api.register(this, phoneStr, pswStr, shopStr, nameStr, new BaseRequest.OnRequestListener() {
//            @Override
//            public void onSuccess(Result result) {
//                loadingDialog.dismiss();
//                DebugLog.e(result.getResult());
//                Shop shop = FastJsonUtil.json2Bean(result.getResult(), Shop.class);
//                //保存用户信息
//                SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_PHONE, tv_phone.getText().toString());
//                SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_PSW, pswStr);
//                gotoActivity(RegisterFourActivity.class);
//                RegisterThreeActivity.this.finish();
//            }
//
//            @Override
//            public void onError(String errorCode, String message) {
//                loadingDialog.dismiss();
//                DebugLog.e(errorCode + ":" + message);
//                showShortToast(message);
//            }
//        });
    }
}
