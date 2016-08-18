package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xgx.syzj.R;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.MemberDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.StrUtil;
import com.xgx.syzj.widget.CustomAlertDialog;

/**
 * 修改会员资料
 *
 * @author zajo
 * @created 2015年11月03日 15:26
 */
public class MemberModifyActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_num, et_name, et_phone, et_carNumber, et_carType;
    private Member member;
    private String strNum, strName, strPhone, strCarNumber, strCarType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add);
        member = getIntent().getParcelableExtra("member");
        if (member == null) {
            defaultFinish();
            return;
        }

        initView();

        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initView() {
        setTitleText("编辑会员");
        setSubmit(getString(R.string.app_button_save));

        et_num = (EditText) findViewById(R.id.et_num);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_carNumber = (EditText) findViewById(R.id.et_carNumber);
        et_carType = (EditText) findViewById(R.id.et_carType);
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setVisibility(View.VISIBLE);
        btn_delete.setOnClickListener(this);
        findViewById(R.id.iv_code).setVisibility(View.GONE);
        if(!StrUtil.isEmpty(member.getCardNumber())) {
            et_num.setText(member.getCardNumber());
            et_num.setFocusable(false);
        }
        et_name.setText(member.getName());
        et_phone.setText(member.getPhone());
        et_carNumber.setText(member.getCarNumber());
        et_carType.setText(member.getCarType());
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            if (result.geteCode() == MemberDataModel.DELETE_MEMBER) {
                showShortToast("删除会员成功");
                Intent data = new Intent();
                data.setAction(Constants.Broadcast.RECEIVER_DELETE_MEMBER);
                data.putExtra("member", member);
                sendBroadcast(data);
                AppManager.getAppManager().returnToActivity(MemberListActivity.class);
                finish();
            } else if (result.geteCode() == MemberDataModel.UPDATE_MEMBER) {
                if(result.getStatus()==200) {
                    showShortToast("会员信息更新成功");
                    member.setCarNumber(strCarNumber);
                    member.setCardNumber(strNum);
                    member.setName(strName);
                    member.setPhone(strPhone);
                    member.setCarType(strCarType);
                    Intent data = new Intent();
                    data.setAction(Constants.Broadcast.RECEIVER_UPDATE_MEMBER);
                    data.putExtra("member", member);
                    sendBroadcast(data);
                    setResult(RESULT_OK);
                    AppManager.getAppManager().returnToActivity(MemberListActivity.class);
                    finish();

                }
            }
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_delete:
                CustomAlertDialog.showRemindDialog(MemberModifyActivity.this, "提醒", "是否删除用户？", new CustomAlertDialog.IAlertDialogListener() {
                    @Override
                    public void onSure(Object obj) {
                        showLoadingDialog(R.string.loading_delete_member);
                        MemberDataModel.deleteMember(member.getId());
                    }
                });
                break;
        }
    }

    @Override
    protected void submit() {
        super.submit();
        strNum=et_num.getText().toString().trim();
        strName = et_name.getText().toString().trim();
        strPhone = et_phone.getText().toString().trim();
        strCarNumber = et_carNumber.getText().toString().trim();
        strCarType = et_carType.getText().toString().trim();
        if (TextUtils.isEmpty(strName)) {
            showShortToast("姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(strPhone)) {
            showShortToast("号码不能为空");
            return;
        }
        if (!StrUtil.isMobileNo(strPhone)) {
            showShortToast("号码格式不正确");
            return;
        }
        showLoadingDialog(R.string.loading_update_member);
        MemberDataModel.updateMember(member.getId(), strName, strCarNumber,strPhone,strCarType,strNum);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
