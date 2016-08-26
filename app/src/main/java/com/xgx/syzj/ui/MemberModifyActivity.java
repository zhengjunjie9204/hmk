package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.InputType;
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
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;

/**
 * 修改会员资料
 *
 * @author zajo
 * @created 2015年11月03日 15:26
 */
public class MemberModifyActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText et_num, et_name, et_phone, et_carnumber, et_carType;
    private Member member;
    private String strNum, strName, strPhone, strCarNumber, strCarType;
    private PercentRelativeLayout pr_english;
    private PercentRelativeLayout key_car;


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
        initListner();
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initListner() {
        et_carType.setOnFocusChangeListener(this);
        et_name.setOnFocusChangeListener(this);
        et_phone.setOnFocusChangeListener(this);

    }

    private void initView() {
        setTitleText("编辑会员");
        setSubmit(getString(R.string.app_button_save));
        et_num = (EditText) findViewById(R.id.et_num);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_carnumber = (EditText) findViewById(R.id.et_carNumber);
        et_carnumber.setInputType(InputType.TYPE_NULL);
        et_carnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
//                    et_carnumber.clearFocus();
                    Utils.hideSoftInput(MemberModifyActivity.this);
                    key_car.setVisibility(View.VISIBLE);
                } else {
                    key_car.setVisibility(View.GONE);
                    Utils.hideSoftInput(MemberModifyActivity.this);
                }
            }
        });
        et_carType = (EditText) findViewById(R.id.et_carType);
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        pr_english =(PercentRelativeLayout)findViewById(R.id.english_car);
        key_car =(PercentRelativeLayout)findViewById(R.id.key_car);
        btn_delete.setVisibility(View.VISIBLE);
        btn_delete.setOnClickListener(this);
        findViewById(R.id.iv_code).setVisibility(View.GONE);
        if(!StrUtil.isEmpty(member.getCardNumber())) {
            et_num.setText(member.getCardNumber());
            et_num.setFocusable(false);
        }
        et_name.setText(member.getName());
        et_phone.setText(member.getPhone());
        et_carnumber.setText(member.getCarNumber());
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
        strCarNumber = et_carnumber.getText().toString().trim();
        strCarType = et_carType.getText().toString().trim();
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

    @Override
    public void onFocusChange(View view, boolean b) {
        key_car.setVisibility(View.GONE);
        pr_english.setVisibility(View.GONE);

    }
    public void onCalculate(View view) {
        String tag = (String) view.getTag();
        String text = et_carnumber.getText().toString();
        switch (tag) {
            case "1":
                et_carnumber.setText("京");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "2":
                et_carnumber.setText("津");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "3":
                et_carnumber.setText("冀");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "4":
                et_carnumber.setText("晋");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "5":
                et_carnumber.setText("蒙");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "6":
                et_carnumber.setText("辽");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "7":
                et_carnumber.setText("吉");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "8":
                et_carnumber.setText("黑");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "9":
                et_carnumber.setText("沪");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "10":
                et_carnumber.setText("苏");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "11":
                et_carnumber.setText("浙");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "12":
                et_carnumber.setText("皖");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "13":
                et_carnumber.setText("闽");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "14":
                et_carnumber.setText("贛");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "15":
                et_carnumber.setText("鲁");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "16":
                et_carnumber.setText("豫");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "17":
                et_carnumber.setText("鄂");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "18":
                et_carnumber.setText("湘");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "19":
                et_carnumber.setText("粤");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "20":
                et_carnumber.setText("桂");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "21":
                et_carnumber.setText("琼");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "22":
                et_carnumber.setText("渝");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "23":
                et_carnumber.setText("川");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "24":
                et_carnumber.setText("贵");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "25":
                et_carnumber.setText("云");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "26":
                et_carnumber.setText("藏");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "27":
                et_carnumber.setText("陕");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "28":
                et_carnumber.setText("甘");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "29":
                et_carnumber.setText("青");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "30":
                et_carnumber.setText("宁");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "31":
                et_carnumber.setText("新");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "one":
                if (text.length() < 7) {
                    text = text + "1";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "two":
                if (text.length() < 7) {
                    text = text + "2";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "three":
                if (text.length() < 7) {
                    text = text + "3";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "four":
                if (text.length() < 7) {
                    text = text + "4";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "five":
                if (text.length() < 7) {
                    text = text + "5";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "six":
                if (text.length() < 7) {
                    text = text + "6";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }

                break;
            case "seven":
                if (text.length() < 7) {
                    text = text + "7";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "eigth":
                if (text.length() < 7) {
                    text = text + "8";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "nine":
                if (text.length() < 7) {
                    text = text + "9";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "zero":
                if (text.length() < 7) {
                    text = text + "0";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "A":
                if (text.length() < 7) {
                    text = text + "A";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "B":
                if (text.length() < 7) {
                    text = text + "B";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "C":
                if (text.length() < 7) {
                    text = text + "C";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "D":
                if (text.length() < 7) {
                    text = text + "D";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "E":
                if (text.length() < 7) {
                    text = text + "E";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "F":
                if (text.length() < 7) {
                    text = text + "F";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "G":
                if (text.length() < 7) {
                    text = text + "G";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "H":
                if (text.length() < 7) {
                    text = text + "H";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "I":
                if (text.length() < 7) {
                    text = text + "I";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "J":
                if (text.length() < 7) {
                    text = text + "J";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "K":
                if (text.length() < 7) {
                    text = text + "K";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "L":
                if (text.length() < 7) {
                    text = text + "L";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "M":
                if (text.length() < 7) {
                    text = text + "M";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "N":
                if (text.length() < 7) {
                    text = text + "N";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "O":
                if (text.length() < 7) {
                    text = text + "O";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "P":
                if (text.length() < 7) {
                    text = text + "P";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "Q":
                if (text.length() < 7) {
                    text = text + "Q";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;

            case "R":
                if (text.length() < 7) {
                    text = text + "R";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;

            case "S":
                if (text.length() < 7) {
                    text = text + "S";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "T":
                if (text.length() < 7) {
                    text = text + "T";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "U":
                if (text.length() < 7) {
                    text = text + "U";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "V":
                if (text.length() < 7) {
                    text = text + "V";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "W":
                if (text.length() < 7) {
                    text = text + "W";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "X":
                if (text.length() < 7) {
                    text = text + "X";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "Y":
                if (text.length() < 7) {
                    text = text + "Y";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "Z":
                if (text.length() < 7) {
                    text = text + "Z";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "finish":
                pr_english.setVisibility(View.GONE);
                break;
            case "delete2": {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                if(text.length()<1){
                    pr_english.setVisibility(View.GONE);
                    key_car.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }
}
