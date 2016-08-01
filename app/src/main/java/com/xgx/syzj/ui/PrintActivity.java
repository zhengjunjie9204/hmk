package com.xgx.syzj.ui;

import android.os.Bundle;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 打印管理界面
 *
 * @author zajo
 * @created 2015年09月28日 10:08
 */
public class PrintActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        setTitleText("打印管理");
        setSubmit(getString(R.string.app_button_sure));
    }
}
