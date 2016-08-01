package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.utils.Utils;

/**
 * 系统设置
 *
 * @author zajo
 * @created 2015年08月13日 17:38
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        setTitleText(getString(R.string.setting_title));
        ((TextView) findViewById(R.id.tv_version)).setText(String.format(getString(R.string.setting_version_text), Utils.getVersion(this)));
    }

    public void goCheckVersion(View view) {
        showShortToast("当前已经是最新版本.");
    }

    public void onClickPrint(View view) {
        gotoActivity(PrintActivity.class);
    }
}
