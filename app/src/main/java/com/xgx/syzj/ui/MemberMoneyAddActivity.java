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

import org.json.JSONException;
import org.json.JSONObject;

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
        member = getIntent().getParcelableExtra("member");
        if (member == null) {
            defaultFinish();
            return;
        }
        initView();
        initListener();
        RechargeDataModel.getRecordList(member.getId());
    }

    private void initView()
    {
        setTitleText(getString(R.string.member_money_add_title));
        setSubmit("记录");
        et_recharge = (EditText) findViewById(R.id.et_recharge);
        et_gift = (EditText) findViewById(R.id.et_gift);
        et_remark = (EditText) findViewById(R.id.et_remark);
        tv_mode = (TextItemView) findViewById(R.id.tv_mode);
        csb_sms = (CheckSwitchButton) findViewById(R.id.csb_sms);
        btn_next = (Button) findViewById(R.id.btn_next);
        tv_user = (TextView) findViewById(R.id.tv_user);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_profit = (TextView) findViewById(R.id.tv_profit);
    }

    private void initListener()
    {
        btn_next.setOnClickListener(this);
        tv_mode.setOnClickListener(this);
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result)
        {
            if (result.geteCode() == RechargeDataModel.GET_RECORDS) {
                try {
                    JSONObject json = new JSONObject(result.getResult());
                    tv_total.setText("¥ " + json.optDouble("consumeByChuzhi", 0));
                    tv_user.setText("¥ " + json.optDouble("storedMoney", 0));
                    tv_profit.setText("¥ " + json.optDouble("storeAmount", 0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                hideLoadingDialog();
                if(!TextUtils.isEmpty(result.getMessage())){
                    showShortToast(result.getMessage());
                }
                if(result.getStatus() == 200){
                    Intent data = new Intent(Constants.Broadcast.RECEIVER_ADD_RECHARGE);
                    data.putExtra("member", member);
                    sendBroadcast(data);
                    defaultFinish();
                }
            }
        }

        public void onEvent(String error)
        {
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
                    String sendMsg = "0";
                    if(csb_sms.isChecked()){
                        sendMsg = "1";
                    }
                    RechargeDataModel.addStoreMoney(member.getId(), recharge, 3, gift, remark, sendMsg);
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