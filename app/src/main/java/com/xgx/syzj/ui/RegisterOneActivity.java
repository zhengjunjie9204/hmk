package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.utils.StrUtil;
import com.xgx.syzj.widget.CustomAlertDialog;

/**
 * 注册第一步
 *
 * @author zajo
 * @created 2015年08月07日 11:46
 */
public class RegisterOneActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_next;
    private EditText et_username;
    private ImageView iv_true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_one);

        btn_next = (Button) findViewById(R.id.btn_next);
        et_username = (EditText) findViewById(R.id.et_username);
        iv_true = (ImageView) findViewById(R.id.iv_true);

        btn_next.setOnClickListener(this);
        et_username.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (StrUtil.isMobileNo(s.toString())){
                iv_true.setVisibility(View.VISIBLE);
                btn_next.setBackgroundResource(R.drawable.button_login_bg);
            } else {
                iv_true.setVisibility(View.GONE);
                btn_next.setBackgroundResource(R.drawable.button_un_enable_bg);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                String phone = et_username.getText().toString().trim();
                if (!StrUtil.isMobileNo(phone)) {
                    CustomAlertDialog.showRegErrorDialog(RegisterOneActivity.this, getString(R.string.dialog_reg_one_phone_error));
                } else {
                    checkPhone();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 检查电话号码是否存在
     */
    private void checkPhone(){
        Bundle bundle = new Bundle();
        bundle.putString("phone", et_username.getText().toString().trim());
        gotoActivity(RegisterTwoActivity.class, bundle);
        finish();
    }

}
