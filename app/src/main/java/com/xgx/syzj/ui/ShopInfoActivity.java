package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 店铺基本信息
 *
 * @author zajo
 * @created 2015年08月12日 16:07
 */
public class ShopInfoActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_info);
        setTitleText(getString(R.string.shop_info_title));
        findViewById(R.id.ll_user).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        gotoActivity(AccountStaffListActivity.class);
    }
}
