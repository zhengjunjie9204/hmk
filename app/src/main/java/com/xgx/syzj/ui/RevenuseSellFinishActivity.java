package com.xgx.syzj.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;
import com.xgx.syzj.widget.ListViewExtend;

import java.util.ArrayList;
import java.util.List;

public class RevenuseSellFinishActivity extends BaseActivity implements View.OnClickListener {

    private ListViewExtend mSellListView;
    private ListViewExtend lv_project;
    private ListViewExtend lv_data;
    private ScrollView sv;
    private CheckBox cb_wash;
    private CheckBox cb_plate;
    private Button btn_cancel;
    private Button btn_sure;
    private double allmoney;
    private List<Project> mProject = new ArrayList<>();
    private List<Goods> mGood = new ArrayList<>();
    private ProjectListAdapter projectAdapter;
    private RevenueGoodListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenuse_sell_finish);
        Utils.hideSoftInput(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
        initData();
    }

    private void initData() {
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
    }

    private void initView() {
        setTitleText(getIntent().getExtras().getString("carnumber"));
        setSubmit("会员");
        mSellListView = (ListViewExtend) findViewById(R.id.sell_listview);
        lv_project = (ListViewExtend) findViewById(R.id.lv_project);
        lv_data = (ListViewExtend) findViewById(R.id.lv_data);
        sv = (ScrollView) findViewById(R.id.sv1);
        cb_wash =(CheckBox) findViewById(R.id.cb_wash);
        cb_wash.setOnClickListener(this);
        cb_plate =(CheckBox) findViewById(R.id.cb_plate);
        cb_plate.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        projectAdapter = new ProjectListAdapter(this, mProject, deleteItemCount, textChange);
        lv_project.setAdapter(projectAdapter);
        mAdapter = new RevenueGoodListAdapter(this, mGood, deleteItemCountTwo,textChangeTwo);
        lv_data.setAdapter(mAdapter);
        btn_sure.setText("结账");
    }

    @Override
    public void onClick(View view) {

    }
    public void onGoodsAddSell(View view)
    {
    }
    public void onGoodsClick(View view)
    {
    }
    private ProjectListAdapter.IDeleteItemCount deleteItemCount = new ProjectListAdapter.IDeleteItemCount() {
        @Override
        public void onItemDelete(int position)
        {
            Project project = mProject.get(position);
            double count = project.getLaborTime();
            if (count == 1) {
                mProject.remove(position);
            } else {
                project.setLaborTime(count - 0.5);
            }
            allmoney -= project.getPrice();
            btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
            projectAdapter.notifyDataSetChanged();
        }
    };

    private ProjectListAdapter.ITextChange textChange = new ProjectListAdapter.ITextChange() {
        @Override
        public void onTextChange(int position, String s)
        {
            if (!TextUtils.isEmpty(s)) {
                Project project = mProject.get(position);
                double count = project.getLaborTime();
                allmoney -= project.getPrice() * count;
                count = Double.parseDouble(s);
                project.setLaborTime(count);
                allmoney += project.getPrice() * count;
                btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
            }
        }
    };

    private RevenueGoodListAdapter.IDeleteItemCount deleteItemCountTwo = new RevenueGoodListAdapter.IDeleteItemCount() {
        @Override
        public void onItemDelete(int position)
        {
            Goods goods = mGood.get(position);
            double count = goods.getQuantity();
            if (count == 1) {
                mGood.remove(position);
            }
            allmoney -= goods.getSellingPrice();
            btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
            mAdapter.notifyDataSetChanged();
        }
    };

    private RevenueGoodListAdapter.ITextChange textChangeTwo = new RevenueGoodListAdapter.ITextChange() {
        @Override
        public void onTextChange(int position, String s)
        {
            if (!TextUtils.isEmpty(s)) {
                Goods goods = mGood.get(position);
                int count = goods.getQuantity();
                allmoney -= goods.getSellingPrice() * count;
                count = Integer.parseInt(s);
                goods.setQuantity(count);
                allmoney += goods.getSellingPrice() * count;
                btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
            }
        }
    };
    //支付按钮
    public void onAddSure(View view) {


        CustomAlertDialog.showPayModeDialog(this, true, new CustomAlertDialog.IAlertListDialogItemClickListener() {
            @Override
            public void onItemClick(int position)
            {

                showShortToast("支付成功");
                Bundle bundle = new Bundle();
                bundle.putString("money", allmoney + "");
                bundle.putParcelable("member", new Member());
                gotoActivity(RevenueResultActivity.class, bundle);
            }
        });
    }
    @Override
    public void onSubmit(View view)
    {
        super.onSubmit(view);
        gotoActivity(RevenueMemberActivity.class);
    }
}
