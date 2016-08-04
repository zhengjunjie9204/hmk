package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.GoodsitemChargeAdapter;
import com.xgx.syzj.adapter.GoodsrechargeAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.bean.ReItem;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 充值计次项目
 */
public class MemberSelectProjectActivity extends BaseActivity implements GoodsrechargeAdapter.GoodCallBack, AdapterView.OnItemClickListener {

    private ComboDataModel mDataModel;
    private ProjectDataModel mpProjectDataModel;
    private ListView lv_count;
    private ExpandableListView exp_lv_package;
    private GoodsrechargeAdapter goodsrechargeAdapter;
    private ArrayList<ReItem> mlist,slist;
    private GoodsitemChargeAdapter goodsitemChargeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_select_project);
        setTitleText("充值计次项目");
        initView();
        mlist=new ArrayList<>();
        mlist.add(new ReItem("1",1));
        mlist.add(new ReItem("2",1));
        mlist.add(new ReItem("3",1));
        slist=new ArrayList<>();
        slist.add(new ReItem("1",1000));
        slist.add(new ReItem("2",2000));
        slist.add(new ReItem("3",3000));
        Log.e("zjj",mlist.size()+"");
        mDataModel = new ComboDataModel(Constants.LOAD_COUNT);
        mpProjectDataModel = new ProjectDataModel(Constants.LOAD_COUNT);
        goodsrechargeAdapter = new GoodsrechargeAdapter(this,mlist,this);
        lv_count.setAdapter(goodsrechargeAdapter);
        lv_count.setOnItemClickListener(this);
        goodsitemChargeAdapter = new GoodsitemChargeAdapter(this,slist);
        exp_lv_package.setAdapter(goodsitemChargeAdapter);
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }



    private void initView() {
        lv_count = (ListView)findViewById(R.id.lv_count);
        exp_lv_package =(ExpandableListView)findViewById(R.id.exp_lv_package);

    }
    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Project> list) {
        }

        public void onEvent(String error)
        {
            showShortToast(error);
        }
    };

    @Override
    public void click(View v) {

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
