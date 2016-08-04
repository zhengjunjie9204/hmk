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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 会员充次
 *
 * @author zajo
 * @created 2015年10月10日 15:20
 */
public class MemberAddCountActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_user, tv_total, tv_profit;
    private EditText et_money, et_remark;
    private TextItemView tv_mode, tv_count;
    private CheckSwitchButton csb_sms;
    private Button btn_ok;
    private String money, count, remark;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add_count);
        member = getIntent().getParcelableExtra("member");
        if (member == null) {
            defaultFinish();
            return;
        }
        initView();
        initListener();
        RechargeDataModel.getMenberItem(member.getId());
        RechargeDataModel.getSumRecord(member.getId());
    }

    private void initView()
    {
        setTitleText("会员计次");
        setSubmit("记录");
        et_money = (EditText) findViewById(R.id.et_money);
        tv_count = (TextItemView) findViewById(R.id.tv_count);
        et_remark = (EditText) findViewById(R.id.et_remark);
        tv_mode = (TextItemView) findViewById(R.id.tv_mode);
        csb_sms = (CheckSwitchButton) findViewById(R.id.csb_sms);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        tv_user = (TextView) findViewById(R.id.tv_user);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_profit = (TextView) findViewById(R.id.tv_profit);
    }

    private void initListener()
    {
        btn_ok.setOnClickListener(this);
        tv_mode.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result)
        {
            if (result.geteCode() == RechargeDataModel.GET_MENBER) {
                try {
                    JSONObject json = new JSONObject(result.getResult());
                    tv_user.setText(json.optInt("length", 0) + "次");
                    JSONArray memberItems = json.optJSONArray("memberItems");
                    if (null != memberItems || memberItems.length() > 0) {
                        int count = 0;
                        for (int i = 0; i < memberItems.length(); i++) {
                            count += memberItems.getJSONObject(i).optInt("itemCount", 0);
                        }
                        tv_total.setText(count + "个");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (result.geteCode() == RechargeDataModel.GET_SUMRECORD) {
                try {
                    JSONObject json = new JSONObject(result.getResult());
                    tv_profit.setText("¥ " + json.optDouble("money", 0.00));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                hideLoadingDialog();
                showShortToast("充值成功");
                Intent data = new Intent();
                data.setAction(Constants.Broadcast.RECEIVER_ADD_RECHARGE);
                data.putExtra("member", member);
                sendBroadcast(data);
                defaultFinish();
            }
        }

        public void onEvent(String error)
        {
            hideLoadingDialog();
            showShortToast(error);
        }
    };

    @Override
    protected void submit()
    {
        super.submit();
        Bundle bundle = new Bundle();
        bundle.putParcelable("member", member);
        gotoActivity(MemberMoneyRecordActivity.class, bundle);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (checkInput()) {
                    showLoadingDialog(R.string.loading_member_add_recharge);
                    //TODO:会员冲次
                    String sendMsg = "0";
                    if (csb_sms.isChecked()) {
                        sendMsg = "1";
                    }
                    RechargeDataModel.addItemCombo(member.getId(), money, 3, null, null, remark, sendMsg);
//                    RechargeDataModel.addRecharge(member.getId(), Integer.parseInt(money),
//                            0, Integer.parseInt(count), Constants.RechargeType.RECHARGE_COUNT,
//                            Utils.getPayIndex(tv_mode.getDesc()), remark);
                }
                break;
            case R.id.tv_mode:
                CustomAlertDialog.showPayModeDialog(this, false, new CustomAlertDialog.IAlertListDialogItemClickListener() {
                    @Override
                    public void onItemClick(int position)
                    {
                        tv_mode.setDesc(Utils.getPayResName(position));
                    }
                });
                break;
            case R.id.tv_count:
                gotoActivityForResult(MemberSelectProjectActivity.class, null, 2005);
                break;
        }
    }

    private boolean checkInput()
    {
        money = et_money.getText().toString().trim();
        remark = et_remark.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            showShortToast("请输入充值金额");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 2005) {
            String name = data.getStringExtra("project");
            String money = data.getStringExtra("money");
            tv_count.setDesc(name);
            et_money.setText(money);
        }
    }
}
