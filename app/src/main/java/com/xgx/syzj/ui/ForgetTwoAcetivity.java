package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 忘记密码步骤二
 *
 * @author zajo
 * @created 2015年08月10日 15:43
 */
public class ForgetTwoAcetivity extends BaseActivity implements View.OnClickListener {

    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_two);
        setTitleText(getString(R.string.forget_psw_title));
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                gotoActivity(ForgetThreeActivity.class);
                finish();
                break;
        }
    }
}
