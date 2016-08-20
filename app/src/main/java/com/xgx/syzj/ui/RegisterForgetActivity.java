package com.xgx.syzj.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.UserDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.StrUtil;


/**
 * 忘记密码第一步
 */
public class RegisterForgetActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_next, btn_send;
    private EditText et_username, et_number;
    private ImageView iv_true;
    private Handler mHandler = new Handler();
    private String phone, code;
    private int count = 120;
    private boolean isStart = false, isSend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_forget);
        setTitleText("获取验证码");
        Bundle bundle = getIntent().getExtras();
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_send = (Button) findViewById(R.id.btn_send);
        et_username = (EditText) findViewById(R.id.et_username);
        et_number = (EditText) findViewById(R.id.et_number);
        iv_true = (ImageView) findViewById(R.id.iv_true);
        setTitleText(getString(R.string.forget_psw_title));

        btn_next.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        et_username.addTextChangedListener(mTextWatcher);
        et_number.addTextChangedListener(mTextWatcher);
        EventCenter.bindContainerAndHandler(this, eventHandler);

        mHandler.removeCallbacks(timer);
        mHandler.postDelayed(timer, 1000);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {

            if (result.geteCode() == UserDataModel.CHECK_CODENUM) {
                if ("验证成功".equals(result.getMessage())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", phone);
                    bundle.putString("code", code);
                    gotoActivity(ForgetActivity.class, bundle);
                    defaultFinish();
                } else {
                    showShortToast(R.string.code_error);
                }
            }
            if (result.geteCode()==UserDataModel.GET_CODENUM){
                if (result.getStatus()==200){
                    showShortToast("验证码发送成功");
                }
            }
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }

    };

    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            phone = et_username.getText().toString().trim();
            code = et_number.getText().toString().trim();
            if (StrUtil.isMobileNo(phone)) {
                iv_true.setVisibility(View.VISIBLE);
                if (isSend) {
                    if (code.length() > 0) {
                        btn_next.setEnabled(true);
                        btn_next.setBackgroundResource(R.drawable.button_login_bg);
                    } else {
                        btn_next.setEnabled(false);
                        btn_next.setBackgroundResource(R.drawable.button_un_enable_bg);
                    }
                }
            } else {
                iv_true.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            if (count < 0) {
                isStart = false;
                mHandler.removeCallbacks(this);
                count = 120;
                btn_send.setEnabled(true);
                btn_send.setText(getString(R.string.reg_two_resend));
            } else {
                if (isStart) {
                    count--;
                    btn_send.setEnabled(false);
                    mHandler.postDelayed(timer, 1000);
                    btn_send.setText(String.format(getString(R.string.reg_two_time), count));
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                UserDataModel.checkCodenum(phone, code);
                break;
            case R.id.btn_send:
                if (StrUtil.isMobileNo(phone)||StrUtil.isEmail(phone)) {
                    isStart = true;
                    isSend = true;
                    btn_send.setText(String.format(getString(R.string.reg_two_time), count));
                    mHandler.postDelayed(timer, 1000);
                    btn_send.setEnabled(false);
                    UserDataModel.getCode(phone);
                } else{
                    showShortToast("请输入正确的手机号或者邮箱");
                }
                break;
            default:
                break;
        }
    }


}
