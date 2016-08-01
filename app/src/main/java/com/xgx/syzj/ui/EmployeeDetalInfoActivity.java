package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Employeebean;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.datamodel.EmployeeDataModel;
import com.xgx.syzj.utils.CacheUtil;


/**
 * 用户信息
 *
 * @author zajo
 * @created 2015年08月27日 14:21
 */
public class EmployeeDetalInfoActivity extends BaseActivity{

    private EditText et_user_name,tv_user_phone;
    private Employeebean employeebean;
    private boolean updataflag = false;
    private String  name,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_employee_info);
        setTitleText(getString(R.string.account_manage_employee_info_title));
        setSubmit(getString(R.string.app_button_sure));
        getData();
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        tv_user_phone = (EditText)findViewById(R.id.tv_user_phone);
        ((TextView)findViewById(R.id.tv_user_auth)).setText(R.string.account_manage_employee);
        et_user_name.setText(employeebean.getUserName());
        tv_user_phone.setText(employeebean.getMobilePhone());
    }

    public void getData(){
        employeebean = (Employeebean) getIntent().getParcelableExtra("employee");
        if (employeebean == null){
            defaultFinish();
            return;
        }
    }

    private boolean checkInput(){
        boolean flag = false;
        String msg = "";

        name = et_user_name.getText().toString();
        phone = tv_user_phone.getText().toString();
        if (name.equals(employeebean.getUserName()) && phone.equals(employeebean.getMobilePhone())){
                 showShortToast("未做修改");
            return false;
        }

        if(TextUtils.isEmpty(phone)){
            msg = "手机号";
        }else if(TextUtils.isEmpty(name)){
            msg = "姓名";
        }else {
            flag = true;
        }

        if(!flag){
            showShortToast(String.format("员工%s不能为空", msg));
            return flag;
        }
        return flag;
    }

    public void onModifyPsw(View view){

    }

    @Override
    protected void submit() {
        super.submit();
        if(checkInput()){
            showLoadingDialog(R.string.loading_modify_employee);
            EmployeeDataModel.updateEmployee(employeebean.getAccountId(),phone,employeebean.getPassword(),employeebean.getAuthority());
            gotoActivity(AccountStaffListActivity.class);
           this.finish();
        }
    }
}
