package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.CardType;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.CardDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.CheckSwitchButton;

/**
 * 编辑会员卡
 *
 * @author zajo
 * @created 2015年11月04日 11:28
 */
public class CardEditActivity extends BaseActivity {

    private EditText et_name, et_rebate, et_jifen;
    private CheckSwitchButton csb_jifen, csb_cuzhi, csb_count, csb_password;
    private LinearLayout ll_password;
    private CardType cardType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_create);
        cardType = getIntent().getParcelableExtra("cardType");
        if (cardType == null) {
            defaultFinish();
            return;
        }
        initView();
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initView() {
        setTitleText("编辑会员卡");
        findViewById(R.id.ll_button).setVisibility(View.VISIBLE);
        ll_password = (LinearLayout) findViewById(R.id.ll_password);
        et_name = (EditText) findViewById(R.id.et_name);
        et_rebate = (EditText) findViewById(R.id.et_rebate);
        et_jifen = (EditText) findViewById(R.id.et_jifen);
        csb_jifen = (CheckSwitchButton) findViewById(R.id.csb_jifen);
        csb_cuzhi = (CheckSwitchButton) findViewById(R.id.csb_cuzhi);
        csb_count = (CheckSwitchButton) findViewById(R.id.csb_count);
        csb_password = (CheckSwitchButton) findViewById(R.id.csb_password);

        csb_count.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !ll_password.isShown()) {
                    ll_password.setVisibility(View.VISIBLE);
                }
                if (!isChecked && ll_password.isShown() && !csb_cuzhi.isChecked()) {
                    ll_password.setVisibility(View.GONE);
                    csb_password.setChecked(false);
                }
            }
        });
        csb_cuzhi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !ll_password.isShown()) {
                    ll_password.setVisibility(View.VISIBLE);
                }
                if (!isChecked && ll_password.isShown() && !csb_count.isChecked()) {
                    ll_password.setVisibility(View.GONE);
                    csb_password.setChecked(false);
                }
            }
        });
        et_rebate.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 2);
                        et_rebate.setText(s);
                        et_rebate.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    et_rebate.setText(s);
                    et_rebate.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et_rebate.setText(s.subSequence(0, 1));
                        et_rebate.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        et_name.setText(cardType.getCardTypeName());
        et_jifen.setText(cardType.getIntegralRatio());
        et_rebate.setText(cardType.getDiscount());
        csb_jifen.setChecked(cardType.isSupportIntegral());
        csb_cuzhi.setChecked(cardType.isSupportValue());
        csb_count.setChecked(cardType.isSupportCount());
        csb_password.setChecked(cardType.isNeedPassord());
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            if (result.geteCode() == CardDataModel.DELETE_CARD) {
                showShortToast("删除成功");
                Intent data = new Intent();
                data.setAction(Constants.Broadcast.RECEIVER_DELETE_CARD);
                data.putExtra("cardType", cardType);
                sendBroadcast(data);
                defaultFinish();
            } else if(result.geteCode() == CardDataModel.UPDATE_CARD){
                showShortToast("更新成功");
                JSONObject obj = JSON.parseObject(result.getResult());
                cardType = FastJsonUtil.json2Bean(obj.getString("info"), CardType.class);
                Intent data = new Intent();
                data.setAction(Constants.Broadcast.RECEIVER_UPDATE_CARD);
                data.putExtra("cardType", cardType);
                sendBroadcast(data);
                defaultFinish();
            }
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }
    };

    public void onDelete(View view) {
        showLoadingDialog(R.string.loading_delete_cards);
        CardDataModel.deleteCard(cardType.getCardTypeId());
    }

    public void onSave(View view) {
        String name = et_name.getText().toString().trim();
        String rebate = et_rebate.getText().toString().trim();
        String integralRatio = et_jifen.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            showShortToast("请输入会员卡名称");
            return;
        }
        if (TextUtils.isEmpty(rebate)) {
            showShortToast("请输入折扣比例");
            return;
        }
        if (csb_jifen.isChecked() && TextUtils.isEmpty(integralRatio)) {
            showShortToast("请输入积分比例");
            return;
        } else if(integralRatio.contains(".")) {
            integralRatio = integralRatio.substring(0, integralRatio.indexOf("."));
        }
        showLoadingDialog(R.string.loading_update_cards);
        CardDataModel.updateCard(cardType.getCardTypeId(), name,
                csb_jifen.isChecked(), csb_cuzhi.isChecked(), csb_count.isChecked(),
                csb_password.isChecked(), Integer.parseInt(rebate), Integer.parseInt(integralRatio));
    }
}
