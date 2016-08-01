package com.xgx.syzj.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 修改信息
 *
 * @author zajo
 * @created 2015年08月12日 17:30
 */
public class ModifyInfoActivity extends BaseActivity {

    public static final String MODIFY_FLAG = "FLAG";

    public static final String MODIFY_NAME = "NAME";
    public static final String MODIFY_NICKNAME = "NICKNAME";
    public static final String MODIFY_PERSON = "PERSON";
    public static final String MODIFY_CELLPHONE = "CELLPHONE";
    public static final String MODIFY_TYPE = "TYPE";

    private TextView tv_title;
    private String flag;
    private String contant;

    private EditText et_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        setSubmit(getString(R.string.app_button_sure));
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_info = (EditText) findViewById(R.id.et_info);

        Bundle b = getIntent().getExtras();
        String[] flags = b.getStringArray(MODIFY_FLAG);
        flag = flags[0];
        contant = flags[1];

        et_info.setText(contant);

        if (MODIFY_NAME.equals(flag)){
            tv_title.setText(String.format(getString(R.string.shop_info_modify),getString(R.string.shop_info_name)));
        } else if(MODIFY_NICKNAME.equals(flag)){
            tv_title.setText(String.format(getString(R.string.shop_info_modify),getString(R.string.shop_info_address)));
        } else if(MODIFY_CELLPHONE.equals(flag)){
            tv_title.setText(String.format(getString(R.string.shop_info_modify),getString(R.string.shop_info_cellphone)));
        } else if(MODIFY_TYPE.equals(flag)){
            tv_title.setText(String.format(getString(R.string.shop_info_modify),getString(R.string.shop_info_type)));
        }
    }

}
