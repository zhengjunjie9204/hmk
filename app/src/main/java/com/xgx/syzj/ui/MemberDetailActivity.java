package com.xgx.syzj.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.MemberDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.widget.CustomAlertDialog;

/**
 * 会员详情
 *
 * @author zajo
 * @created 2015年08月31日 11:06
 */
public class MemberDetailActivity extends BaseActivity {

    private TextView tv_name, tv_card, tv_money, tv_count, tv_phone, tv_number, tv_type, tv_all_pay;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        initView();
        member = getIntent().getParcelableExtra("member");
        if (member == null) {
            defaultFinish();
            return;
        }
        initDate();
    }

    private void initView() {
        setTitleText(getString(R.string.member_detail_title));
        setSubmit(getString(R.string.app_button_edit));
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_card = (TextView) findViewById(R.id.tv_card);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_all_pay = (TextView) findViewById(R.id.tv_all_pay);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_type= (TextView) findViewById(R.id.tv_type);
        tv_number= (TextView) findViewById(R.id.tv_number);


        EventCenter.bindContainerAndHandler(this, eventHandler);
        registerReceiver();
    }

    private void initDate() {
        tv_name.setText(member.getName());
        tv_card.setText("NO:" + member.getCardNumber());
        tv_money.setText("储值：¥ " + member.getStoredMoney());
        String status="0";
        if(!TextUtils.isEmpty(member.getStatus())){
            status=member.getStatus();
        }
        tv_count.setText("计次卡：" + status+ "张");
        tv_all_pay.setText("¥ " + member.getStoredMoney() + "\n累计消费");
        tv_phone.setText(member.getPhone());
        tv_type.setText(member.getCarType());
        tv_number.setText(member.getCarNumber());
    }
    //注册接受者
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.Broadcast.RECEIVER_ADD_RECHARGE);
//        intentFilter.addAction(Constants.Broadcast.RECEIVER_DELETE_MEMBER);
        intentFilter.addAction(Constants.Broadcast.RECEIVER_UPDATE_MEMBER);
        registerReceiver(myReceiver, intentFilter);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.Broadcast.RECEIVER_ADD_RECHARGE)) {
                //充值积分兑换响应
                member = intent.getParcelableExtra("member");
//                tv_money.setText("储值：¥ " + member.getStrCardValue());
//                tv_count.setText("计次卡：" + member.getCardCount() + "张");
            }
//            else if (action.equals(Constants.Broadcast.RECEIVER_DELETE_MEMBER)) {
//                //删除响应
//                MemberDetailActivity.this.defaultFinish();
//            }
            else if (action.equals(Constants.Broadcast.RECEIVER_UPDATE_MEMBER)) {
                //更新会员信息
                member = intent.getParcelableExtra("member");
                initDate();
            }
        }
    };

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            if (result.geteCode() == MemberDataModel.DELETE_MEMBER) {
                showShortToast("删除会员成功");
                Intent data = new Intent();
                data.setAction(Constants.Broadcast.RECEIVER_DELETE_MEMBER);
                data.putExtra("member", member);
                sendBroadcast(data);
                defaultFinish();
            }
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }
    };


    public void onMemberMoney(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("member", member);
        gotoActivity(MemberMoneyAddActivity.class, bundle);
    }

    public void onMemberHistory(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("member", member);
        gotoActivity(MemberConsumptionActivity.class,bundle);
    }

    public void onMemberAddCount(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("member", member);
        gotoActivity(MemberAddCountActivity.class, bundle);
    }

    public void onMemberDelete(View view) {
        CustomAlertDialog.showRemindDialog(MemberDetailActivity.this, "提醒", "是否删除用户？", new CustomAlertDialog.IAlertDialogListener() {
            @Override
            public void onSure(Object obj) {
                showLoadingDialog(R.string.loading_delete_member);
                MemberDataModel.deleteMember(member.getId());
            }
        });
    }

    @Override
    protected void submit() {
        super.submit();
        Bundle bundle = new Bundle();
        bundle.putParcelable("member", member);
        gotoActivity(MemberModifyActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
