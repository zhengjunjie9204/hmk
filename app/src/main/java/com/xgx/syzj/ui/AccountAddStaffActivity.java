package com.xgx.syzj.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.datamodel.EmployeeDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.StrUtil;

/**
 * 添加员工
 *
 * @author zajo
 * @created 2015年08月25日 17:11
 */
public class AccountAddStaffActivity extends BaseActivity{

    private EditText et_phone, et_name, et_psw, et_re_psw;
    private String pswStr, rePswStr, phoneStr, nameStr;
    private EmployeeDataModel employeeDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_add_staff);
        setTitleText(getString(R.string.account_manage_add_staff));
        setSubmit(getString(R.string.app_button_submit));

        et_phone = (EditText) findViewById(R.id.et_phone);
        et_name = (EditText) findViewById(R.id.et_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_re_psw = (EditText) findViewById(R.id.et_re_psw);

        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler(){

        public void onEvent(Result result){
            hideLoadingDialog();
            showShortToast(R.string.account_manage_add_staff_success);
            employeeDataModel = new EmployeeDataModel(Constants.LOAD_COUNT);
            employeeDataModel.queryFirstPage();
        }

        public void onEvent(String error){
            hideLoadingDialog();
            showShortToast(error);
        }
    };

    @Override
    protected void submit() {
        super.submit();
        if (checkInput()) {
            register();
        }
    }

    private boolean checkInput() {
        phoneStr = et_phone.getText().toString().trim();
        if (!StrUtil.isMobileNo(phoneStr)) {
            showShortToast(R.string.toast_phone_format_error);
            return false;
        }
        nameStr = et_name.getText().toString().trim();
        if (nameStr.length() > 16) {
            showShortToast(R.string.toast_username_long_error);
            return false;
        }
        pswStr = et_psw.getText().toString().trim();
        if (pswStr.length() < 6) {
            showShortToast(R.string.toast_reg_psw_less);
            return false;
        }
        rePswStr = et_re_psw.getText().toString().trim();
        if (!pswStr.equals(rePswStr)) {
            showShortToast(R.string.toast_reg_psw_same);
            return false;
        }
        return true;
    }

    private void register() {
        showLoadingDialog(R.string.dialog_add_staff_text);
        User user = CacheUtil.getmInstance().getUser();
       //UserDataModel.registerStaff(phoneStr, pswStr, nameStr, user.getAuthority(), user.getStoreId());
       // employeeDataModel.addEmployee(phoneStr, pswStr, nameStr, user.getAuthority(), user.getStoreId());

//        gotoActivity(AccountStaffListActivity.class);
//        defaultFinish();
    }
}
