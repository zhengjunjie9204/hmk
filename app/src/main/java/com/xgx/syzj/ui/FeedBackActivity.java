package com.xgx.syzj.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 反馈
 *
 * @author zajo
 * @created 2015年08月13日 16:26
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    private CheckBox cb;
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feedback);
        setTitleText(getString(R.string.feedback_title));
        cb = (CheckBox) findViewById(R.id.cb);
        et_phone = (EditText) findViewById(R.id.et_phone);
        //绑定监听器
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                cb.setChecked(arg1);
                if (arg1)
                    et_phone.setVisibility(View.VISIBLE);
                else
                    et_phone.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.tv_feedback_phone).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedback_phone:
                String s = getString(R.string.feedback_phone_text);
                Intent myIntentDial = new Intent("android.intent.action.CALL", Uri.parse("tel:" + s));
                startActivity(myIntentDial);
                break;
        }
    }
}
