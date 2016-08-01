package com.xgx.syzj.ui;

import android.os.Bundle;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 消息中心
 *
 * @author zajo
 * @created 2015年08月13日 14:19
 */
public class MessageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitleText(getString(R.string.message_center_title));
    }

}
