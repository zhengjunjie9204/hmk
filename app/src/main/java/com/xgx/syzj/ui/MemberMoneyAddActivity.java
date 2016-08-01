package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.RechargeDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CheckSwitchButton;
import com.xgx.syzj.widget.CustomAlertDialog;
import com.xgx.syzj.widget.TextItemView;

/**
 * 会员储值
 *
 * @author zajo
 * @created 2015年08月31日 17:27
 */
public class MemberMoneyAddActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_recharge, et_gift, et_remark;
    private TextView tv_user,tv_total,tv_profit;
    private TextItemView tv_mode;
    private CheckSwitchButton csb_sms;
    private Button btn_next;
    private String recharge, gift, remark;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_money_add);
        setTitleText(getString(R.string.member_money_add_title));
        setSubmit("记录");

        member = getIntent().getParcelableExtra("member");
        if (member == null) {
            defaultFinish();
            return;
        }
        et_recharge = (EditText) findViewById(R.id.et_recharge);
        et_gift = (EditText) findViewById(R.id.et_gift);
        et_remark = (EditText) findViewById(R.id.et_remark);
        tv_mode = (TextItemView) findViewById(R.id.tv_mode);
        csb_sms = (CheckSwitchButton) findViewById(R.id.csb_sms);
        btn_next = (Button) findViewById(R.id.btn_next);
        tv_user = (TextView) findViewById(R.id.tv_user);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_profit = (TextView) findViewById(R.id.tv_profit);

        btn_next.setOnClickListener(this);
        tv_mode.setOnClickListener(this);

//        tv_total.setText("¥ " + member.getStrCumulativeUsedValue());
//        tv_user.setText("¥ " + member.getStrCardValue());
//        tv_profit.setText("¥ " + member.getStrCumulativeRechargeAmount());

        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            showShortToast("充值成功");
//            member.setCardValue(Double.parseDouble(recharge) + Double.parseDouble(gift) + member.getCardValue());
//            member.setCumulativeRechargeAmount(member.getCumulativeRechargeAmount() + Double.parseDouble(recharge));
//            tv_user.setText("¥ " + member.getStrCardValue());
//            tv_profit.setText("¥ " + member.getStrCumulativeRechargeAmount());
            Intent data = new Intent();
            data.setAction(Constants.Broadcast.RECEIVER_ADD_RECHARGE);
            data.putExtra("member", member);
            sendBroadcast(data);
            et_recharge.setText("");
            et_gift.setText("");
            et_remark.setText("");
            if (csb_sms.isChecked()) {
                csb_sms.setChecked(false);
            }
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }

    };

    @Override
    protected void submit() {
        super.submit();
        Bundle bundle = new Bundle();
        bundle.putParcelable("member", member);
        gotoActivity(MemberMoneyRecordActivity.class, bundle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (checkInput()) {
                    showLoadingDialog(R.string.loading_member_add_recharge);
                    RechargeDataModel.addRecharge(member.getId(), Integer.parseInt(recharge),
                            Integer.parseInt(gift), 0, Constants.RechargeType.RECHARGE_VALUE,
                            Utils.getPayIndex(tv_mode.getDesc()), remark);
                }
                break;
            case R.id.tv_mode:
                CustomAlertDialog.showPayModeDialog(this,false ,new CustomAlertDialog.IAlertListDialogItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        tv_mode.setDesc(Utils.getPayResName(position));
                    }
                });
                break;
        }
    }

    private boolean checkInput() {
        recharge = et_recharge.getText().toString().trim();
        gift = et_gift.getText().toString().trim();
        remark = et_remark.getText().toString().trim();
        if (TextUtils.isEmpty(recharge)) {
            showShortToast("请输入充值金额");
            return false;
        }
        if (TextUtils.isEmpty(gift))
            gift = "0";
        return true;
    }

}