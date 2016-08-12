package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.GoodsitemChargeAdapter;
import com.xgx.syzj.adapter.GoodsrechargeAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Combo;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.StoreItem;
import com.xgx.syzj.datamodel.ComboDataModel;
import com.xgx.syzj.datamodel.ProjectDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.ExpandableListViewExtend;
import com.xgx.syzj.widget.ListViewExtend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 充值计次项目
 */
public class MemberSelectProjectActivity extends BaseActivity implements View.OnClickListener, GoodsrechargeAdapter.SignledListener, GoodsitemChargeAdapter.SignledListener {
    private ComboDataModel mDataModel;
    private ListViewExtend lv_count;
    private ExpandableListViewExtend exp_lv_package;
    private GoodsrechargeAdapter proAdapter;
    private GoodsitemChargeAdapter comboAdapter;
    private ArrayList<StoreItem> mStoreList;
    private ArrayList<Combo> mComboList;
    private Button mBtnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_select_project);
        mStoreList = new ArrayList<>();
        mComboList = new ArrayList<>();
        initView();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        mDataModel = new ComboDataModel(Constants.LOAD_COUNT);
        ProjectDataModel.getStoreItem();
        mDataModel.queryFirstPage();
    }

    private void initView()
    {
        setTitleText("充值计次项目");
        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);
        lv_count = (ListViewExtend) findViewById(R.id.lv_count);
        exp_lv_package = (ExpandableListViewExtend) findViewById(R.id.exp_lv_package);
        proAdapter = new GoodsrechargeAdapter(this, mStoreList);
        lv_count.setAdapter(proAdapter);
        comboAdapter = new GoodsitemChargeAdapter(this, mComboList);
        exp_lv_package.setAdapter(comboAdapter);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result)
        {
            if (result.getStatus() == 200) {
                JSONObject object = JSON.parseObject(result.getResult());
                List<StoreItem> list = FastJsonUtil.json2List(object.getString("items"), StoreItem.class);
                mStoreList.addAll(list);
                proAdapter.notifyDataSetChanged();
            }
        }

        public void onEvent(List<Combo> list)
        {
            mComboList.addAll(list);
            comboAdapter.notifyDataSetChanged();
        }

        public void onEvent(String error)
        {
            showShortToast(error);
        }
    };

    @Override
    public void onClick(View v)
    {
        if (proAdapter.getSlectMap().size() == 0 && comboAdapter.getSlectMap().size() == 0) {
            showShortToast("请选择项目或者套餐");
            return;
        } else {
            Intent data = new Intent();
            if (proAdapter.getSlectMap().size() > 0) {
                for (Map.Entry<Integer, StoreItem> integerStoreItemEntry : proAdapter.getSlectMap().entrySet()) {
                    data.putExtra("store",integerStoreItemEntry.getValue());
                }
            } else if (comboAdapter.getSlectMap().size() > 0) {
                for (Map.Entry<Integer, Combo> integerComboEntry : comboAdapter.getSlectMap().entrySet()) {
                    data.putExtra("combo",integerComboEntry.getValue());
                }
            }
            setResult(RESULT_OK,data);
            defaultFinish();
        }
    }

    @Override
    public void onStoreClick(StoreItem item)
    {
        comboAdapter.cleanMap();
    }

    @Override
    public void onComboClick(Combo combo)
    {
        proAdapter.cleanMap();
    }
}
