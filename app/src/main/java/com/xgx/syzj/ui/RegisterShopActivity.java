package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.datamodel.EmployeeDataModel;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.StrUtil;

/**
 * 填写店铺信息完成注册
 *
 * @author zajo
 * @created 2015年11月19日 15:18
 */
public class RegisterShopActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_header;
    private EditText et_shopname,et_username,et_industry;
    private Button btn_protocol,btn_reg;
    private String et_shopname_str,et_username_str,et_industry_str;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_shop);
        initView();
    }

    private void initView(){
        setTitleText("店铺设置");
        iv_header = (ImageView) findViewById(R.id.iv_header);
        et_shopname = (EditText) findViewById(R.id.et_shopname);
        et_username = (EditText) findViewById(R.id.et_username);
        et_industry = (EditText) findViewById(R.id.et_industry);
        btn_protocol = (Button) findViewById(R.id.btn_protocol);
        btn_reg = (Button) findViewById(R.id.btn_reg);

        iv_header.setOnClickListener(this);
        btn_protocol.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_protocol:
                break;
            case R.id.iv_header:
                break;
            case R.id.btn_reg:
                if(checkInput()){
                //    showLoadingDialog(R.string.dialog_add_staff_text);
//                        CacheUtil.getmInstance().getShop().getShopMessage().setShopName(et_shopname_str);
//                        CacheUtil.getmInstance().getShop().getUserInfo().setUserName(et_username_str);
//                        CacheUtil.getmInstance().getShop().getShopMessage().setShopType(et_industry_str);
                 //   hideLoadingDialog();
                    defaultFinish();
                }

                break;
        }

    }

    private boolean checkInput() {
        et_shopname_str = et_shopname.getText().toString().trim();
        if (TextUtils.isEmpty(et_shopname_str) || et_shopname_str.length() > 16) {
            showShortToast("店铺名称输入不当");
            return false;
        }
        et_username_str = et_username.getText().toString().trim();
        if (TextUtils.isEmpty(et_username_str) || et_username_str.length() > 16) {
            showShortToast("用户名称输入不当");
            return false;
        }
        et_industry_str = et_industry.getText().toString().trim();
        if (TextUtils.isEmpty(et_industry_str) || et_industry_str.length() > 16) {
            showShortToast("行业输入不当");
            return false;
        }

        return true;
    }

  //  private void register() {
       // showLoadingDialog(R.string.dialog_add_staff_text);
      //  User user = CacheUtil.getmInstance().getShop().getUserInfo();
        // UserDataModel.registerStaff(phoneStr, pswStr, nameStr, user.getAuthority(), user.getStoreId());

 //   }
}
