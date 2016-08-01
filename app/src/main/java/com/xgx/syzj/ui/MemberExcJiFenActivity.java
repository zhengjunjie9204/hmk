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
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.RechargeDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.ui.GoodsListActivity;
import com.xgx.syzj.widget.TextItemView;

/**
 * 兑换积分
 *
 * @author zajo
 * @created 2015年08月31日 15:05
 */
public class MemberExcJiFenActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_count;
    private EditText et_count;
    private TextItemView tiv_goods;
    private EditText et_mark;
    private Button btn_submit;
    private Member member;
    private Goods goods;
    private String rechargeCount, remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_exc_jifen);

        member = getIntent().getParcelableExtra("member");
        if (member == null) {
            defaultFinish();
            return;
        }

        initView();
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initView() {
        setTitleText(getString(R.string.member_jifen_title));
        tv_count = (TextView) findViewById(R.id.tv_count);
        et_count = (EditText) findViewById(R.id.et_count);
        et_mark = (EditText) findViewById(R.id.et_mark);
        tiv_goods = (TextItemView) findViewById(R.id.tiv_goods);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(this);
        tiv_goods.setOnClickListener(this);

//        tv_count.setText(member.getCardIntegral() + "");
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            showShortToast("兑换成功");
//            member.setCardIntegral(member.getCardIntegral() - Integer.parseInt(rechargeCount));
//            tv_count.setText(member.getCardIntegral() + "");
            Intent data = new Intent();
            data.setAction(Constants.Broadcast.RECEIVER_ADD_RECHARGE);
            data.putExtra("member", member);
            sendBroadcast(data);
            et_count.setText("");
            tiv_goods.setDesc("");
            et_mark.setText("");
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tiv_goods:
                Bundle bundle = new Bundle();
                bundle.putString(GoodsListActivity.FLAG, GoodsListActivity.FLAG_EXC_JIFEN);
                gotoActivityForResult(GoodsListActivity.class, bundle, 1000);
                break;
            case R.id.btn_submit:
                if (checkInput()) {
                    showLoadingDialog(R.string.loading_member_add_recharge_count);
                    RechargeDataModel.exchangeIntegral(member.getId()+"", goods.getProductId(),
                            Integer.parseInt(rechargeCount), remark);
                }
                break;
        }
    }

    private boolean checkInput() {
        rechargeCount = et_count.getText().toString().trim();
        remark = et_mark.getText().toString().trim();
        if (TextUtils.isEmpty(rechargeCount)) {
            showShortToast("请输入兑换积分");
            return false;
        }
        if (goods == null) {
            showShortToast("请选择兑换商品");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 1000:
                goods = data.getParcelableExtra("goods");
                if (goods != null)
                    tiv_goods.setDesc(goods.getProductName());
                break;
        }
    }
}
