package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.Utils;

/**
 * 用户信息
 *
 * @author zajo
 * @created 2015年08月27日 14:21
 */
public class AccountUserInfoActivity extends BaseActivity{

    private EditText et_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_user_info);
        setTitleText(getString(R.string.account_manage_user_info_title));
        User user = CacheUtil.getmInstance().getUser();
        if (user == null){
            defaultFinish();
            return;
        }
    }

    public void onModifyPsw(View view){

    }

}
