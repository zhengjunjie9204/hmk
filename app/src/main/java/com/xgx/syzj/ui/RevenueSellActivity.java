package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.ProjectListAdapter;
import com.xgx.syzj.adapter.RevenueGoodListAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;
import com.xgx.syzj.widget.ListViewExtend;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 接车开单详情
 */
public class RevenueSellActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_cancel;
    private ListViewExtend lv_data, lv_project, mSellListView;
    private double allmoney;
    private List<Project> mProject = new ArrayList<>();
    private List<Goods> mGood = new ArrayList<>();
    private ProjectListAdapter projectAdapter;
    private RevenueGoodListAdapter mAdapter;
    private Button btn_sure;
    private boolean mIsPay;
    private ScrollView sv;
    private boolean hasGoods = false;
    private boolean hasProject = false;
    private CheckBox cb_wash;
    private CheckBox cb_plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_sell);
        Utils.hideSoftInput(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        EventCenter.bindContainerAndHandler(this, eventHandler);
        initView();
        initData();
        //        RechargeDataModel.getMenberItem();
    }

    private void initView() {
        setTitleText(getIntent().getExtras().getString("carnumber"));
        setSubmit("会员");
        mSellListView = (ListViewExtend) findViewById(R.id.sell_listview);
        lv_project = (ListViewExtend) findViewById(R.id.lv_project);
        lv_data = (ListViewExtend) findViewById(R.id.lv_data);
        sv = (ScrollView) findViewById(R.id.sv1);
        cb_wash = (CheckBox) findViewById(R.id.cb_wash);
        cb_wash.setOnClickListener(this);
        cb_plate = (CheckBox) findViewById(R.id.cb_plate);
        cb_plate.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        projectAdapter = new ProjectListAdapter(this, mProject, null);
        lv_project.setAdapter(projectAdapter);
        mAdapter = new RevenueGoodListAdapter(this, mGood, null);
        lv_data.setAdapter(mAdapter);
    }

    private void initData() {
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
        mIsPay = getIntent().getExtras().getBoolean("isPay");
        if (mIsPay) {
            btn_sure.setText("结账");
        } else {
            btn_sure.setText("结账");
        }

    }

    public void onGoodsAddSell(View view) {
        gotoActivityForResult(ProjectListActivity.class, null, 2002);
    }

    public void onGoodsClick(View view) {
        gotoActivityForResult(RevenueGoodsListActivity.class, null, 2003);
    }

    //支付按钮
    public void onAddSure(View view) {

        if (hasProject) {
            showShortToast("挂单成功");
            return;
        }
        if (!hasGoods) {
            showShortToast("未选择商品");
            return;
        }
        CustomAlertDialog.showPayModeDialog(this, true, new CustomAlertDialog.IAlertListDialogItemClickListener() {
            @Override
            public void onItemClick(int position) {

                showShortToast("支付成功");
                Bundle bundle = new Bundle();
                bundle.putString("money", allmoney + "");
                bundle.putParcelable("member", new Member());
                gotoActivity(RevenueResultActivity.class, bundle);
            }
        });
    }

    @Override
    public void onSubmit(View view) {
        super.onSubmit(view);
        gotoActivity(RevenueMemberActivity.class);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(JSONObject jsonObject) {

        }

        public void onEvent(String error) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 2002) {
            ArrayList<Project> list=data.getExtras().getParcelableArrayList("list");
            for (Project project:list){
                allmoney += project.getPrice() * project.getLaborTime();
            }
            mProject.addAll(list);
            hasProject = true;
            btn_sure.setText("挂单");
            projectAdapter.notifyDataSetChanged();
        } else if (requestCode == 2003) {
            ArrayList<Goods> list=data.getExtras().getParcelableArrayList("list");
            for (Goods goods:list) {
                allmoney += goods.getQuantity() * goods.getSellingPrice();
            }
            mGood.addAll(list);
            hasGoods = true;
            mAdapter.notifyDataSetChanged();
        }
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_wash:
                if ((btn_sure.getText().toString()).equals("挂单") && !cb_plate.isChecked()) {
                    hasProject = false;
                    btn_sure.setText("结账");
                } else {
                    hasProject = true;
                    btn_sure.setText("挂单");
                }
                break;
            case R.id.cb_plate:
                if ((btn_sure.getText().toString()).equals("挂单") && !cb_wash.isChecked()) {
                    hasProject = false;
                    btn_sure.setText("结账");
                } else {
                    hasProject = true;
                    btn_sure.setText("挂单");
                }
                break;
        }
    }
}
