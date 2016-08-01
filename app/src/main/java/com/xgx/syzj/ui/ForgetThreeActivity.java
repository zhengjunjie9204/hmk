package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 忘记密码步骤三
 *
 * @author zajo
 * @created 2015年08月10日 16:17
 */
public class ForgetThreeActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_submit,btn_login;
    private LinearLayout ll_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_three);
        setTitleText(getString(R.string.forget_psw_title));
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_login = (Button) findViewById(R.id.btn_login);
        ll_login = (LinearLayout) findViewById(R.id.ll_log);

        btn_submit.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                ll_login.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_login:
                finish();
                break;
        }
    }
}
