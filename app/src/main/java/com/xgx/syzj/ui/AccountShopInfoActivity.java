package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.widget.CustomProgressDialog;

/**
 * 店铺信息
 *
 * @author zajo
 * @created 2015年08月27日 11:40
 */
public class AccountShopInfoActivity extends BaseActivity {

    private EditText et_shop_name,et_shop_phone,et_shop_address;
    private CustomProgressDialog dialog;
    private String strName,strPhone,strAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_shop_info);

        setTitleText(getString(R.string.account_manage_shop_info));
        et_shop_name = (EditText) findViewById(R.id.et_shop_name);
        et_shop_phone = (EditText) findViewById(R.id.et_shop_phone);
        et_shop_address = (EditText) findViewById(R.id.et_shop_address);
        dialog = CustomProgressDialog.createDialog(this).setMessage(getString(R.string.dialog_modify_shop_info_text));

        User user = CacheUtil.getmInstance().getUser();
        if (user == null){
            defaultFinish();
            return;
        }
        if (user.getRoles() != 0){
            findViewById(R.id.tv_shop_name).setEnabled(false);
            findViewById(R.id.tv_shop_phone).setEnabled(false);
            findViewById(R.id.tv_shop_address).setEnabled(false);
            et_shop_name.setEnabled(false);
            et_shop_phone.setEnabled(false);
            et_shop_address.setEnabled(false);
        } else {
            setSubmit(getString(R.string.app_button_sure));
        }

    }

/*    @Override
    public void onSubmit(View view) {
        super.onSubmit(view);
        if (checkInput()){
            submit();
        }
    }*/

    private boolean checkInput(){
        boolean flag = false;
        String msg = "";

        strName = et_shop_name.getText().toString().trim();
        strPhone = et_shop_phone.getText().toString().trim();
        strAddress = et_shop_address.getText().toString().trim();

        if(TextUtils.isEmpty(strName)){
            msg = "店铺名称";
        }else if(TextUtils.isEmpty(strPhone)){
            msg = "店面电话";
        }else  if(TextUtils.isEmpty(strAddress)){
            msg = "店铺地址";
        }else {
            flag = true;
        }

        if(!flag){
            showShortToast(String.format("%s不能为空",msg));
            return flag;
        }
        return flag;
    }

    @Override
    protected void submit() {
        super.submit();
        if(checkInput()){
            showLoadingDialog(R.string.loading_modify_stoore);
            Api.updateStoreInfo(strName, strPhone, strAddress, new BaseRequest.OnRequestListener() {
                @Override
                public void onSuccess(Result result) {
                    hideLoadingDialog();
                }
                @Override
                public void onError(String errorCode, String message) {
                    showShortToast(message);
                }
            });
            hideLoadingDialog();
        }
//        dialog.show();
    }

}
