package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.ComboDataModel;
import com.xgx.syzj.datamodel.ProjectDataModel;
import com.xgx.syzj.datamodel.RechargeDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CheckSwitchButton;
import com.xgx.syzj.widget.CustomAlertDialog;
import com.xgx.syzj.widget.TextItemView;

import java.util.List;

/**
 * 充值计次项目
 */
public class MemberSelectProjectActivity extends BaseActivity{

    private TextView tv_money;
    private EditText et_count;
    private CheckBox cb_wash,cb_wash2;
    private String money,name;
    private ComboDataModel mDataModel;
    private ProjectDataModel mpProjectDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_select_project);
        setTitleText("充值计次项目");
        initView();
        money=3500.00+"";
        name="计次A套餐";
        initListner();
        mDataModel = new ComboDataModel(Constants.LOAD_COUNT);
        mpProjectDataModel = new ProjectDataModel(Constants.LOAD_COUNT);
        mDataModel.queryFirstPage();
        mpProjectDataModel.queryFirstPage();
    }

    private void initListner() {
        cb_wash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_wash2.setChecked(false);
                    money=tv_money.getText().toString();
                    name="全车镀晶";
                }
            }
        });
        cb_wash2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_wash.setChecked(false);
                    money=3500.00+"";
                    name="计次A套餐";
                }
            }
        });
        et_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString())){
                    cb_wash.setChecked(true);
                    int c=Integer.parseInt(s.toString());
                    tv_money.setText(35.0*c+"");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("project",name);
                intent.putExtra("money",money);
                setResult(RESULT_OK,intent);
                defaultFinish();
            }
        });
    }

    private void initView() {
        tv_money= (TextView) findViewById(R.id.tv_money);
        et_count= (EditText) findViewById(R.id.et_count);
        cb_wash= (CheckBox) findViewById(R.id.cb_wash);
        cb_wash2= (CheckBox) findViewById(R.id.cb_wash2);
    }
    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Project> list) {
//            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
//            mList.addAll(list);
//            mAdapter.notifyDataSetChanged();
        }

        public void onEvent(String error)
        {
            showShortToast(error);
        }
    };

}
