package com.xgx.syzj.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;

/**
 * 注册成功信息页面
 *
 * @author zajo
 * @created 2015年08月19日 10:39
 */
public class RegisterFourActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reg_four);

        findViewById(R.id.tv_one).setBackgroundColor(getResources().getColor(R.color.light_color));
        findViewById(R.id.iv_two).setBackgroundResource(R.mipmap.registered_two_two);
        findViewById(R.id.tv_two).setBackgroundColor(getResources().getColor(R.color.light_color));
        findViewById(R.id.iv_three).setBackgroundResource(R.mipmap.registered_three_three);
        findViewById(R.id.tv_three).setBackgroundColor(getResources().getColor(R.color.light_color));
        findViewById(R.id.iv_four).setBackgroundResource(R.mipmap.register_four_four);
        String phone = SYZJApplication.getInstance().getSpUtil().get(Constants.SharedPreferencesClass.SP_PHONE);
        ((TextView) (findViewById(R.id.tv_msg))).setText(String.format(getString(R.string.reg_four_success_msg), phone));

    }
}
