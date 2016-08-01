package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.widget.CircleImageView;
import com.xgx.syzj.widget.TextItemView;

/**
 * 账户管理
 *
 * @author zajo
 * @created 2015年08月25日 16:29
 */
public class AccountManageActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView civ_logo;
    private TextItemView tiv_shop, tiv_user, tiv_manage, tiv_new, tiv_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        initView();
        User user = CacheUtil.getmInstance().getUser();
        if (user == null) {
            defaultFinish();
            return;
        }
        if (user.getRoles() != 0) {
            tiv_shop.setEnabled(false);
            tiv_user.setEnabled(false);
            tiv_manage.setEnabled(false);
            tiv_new.setEnabled(false);
            tiv_type.setEnabled(false);
        }
    }

    private void initView() {
        setTitleText(getString(R.string.account_manage_title));
        civ_logo = (CircleImageView) findViewById(R.id.civ_logo);
        Api.getImageLoader().get("http://image.photophoto.cn/nm-6/018/030/0180300244.jpg", civ_logo);
        tiv_shop = (TextItemView) findViewById(R.id.tiv_shop);
        tiv_user = (TextItemView) findViewById(R.id.tiv_user);
        tiv_manage = (TextItemView) findViewById(R.id.tiv_manage);
        tiv_new = (TextItemView) findViewById(R.id.tiv_new);
        tiv_type = (TextItemView) findViewById(R.id.tiv_type);

        tiv_shop.setOnClickListener(this);
        tiv_user.setOnClickListener(this);
        tiv_manage.setOnClickListener(this);
        tiv_new.setOnClickListener(this);
        tiv_type.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tiv_shop:
                gotoActivity(AccountShopInfoActivity.class);
                break;
            case R.id.tiv_user:
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", CacheUtil.getmInstance().getUser());
                gotoActivity(AccountUserInfoActivity.class, bundle);
                break;
            case R.id.tiv_manage:
                gotoActivity(AccountStaffListActivity.class);
                break;
            case R.id.tiv_new:
                gotoActivity(AccountAddStaffActivity.class);
                break;
            case R.id.tiv_type:
                break;
        }
    }
}
