package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
public class RevenueSellActivity extends BaseActivity{
    private Button btn_cancel;
    private ListViewExtend lv_data, lv_project,mSellListView;
    private double allmoney;
    private List<Project> mProject = new ArrayList<>();
    private List<Goods> mGood = new ArrayList<>();
    private ProjectListAdapter projectAdapter;
    private RevenueGoodListAdapter mAdapter;
    private Button btn_sure;
    private boolean mIsPay;
    private ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_sell);
        Utils.hideSoftInput(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        EventCenter.bindContainerAndHandler(this, eventHandler);
        initView();
        initData();
        //        RechargeDataModel.getMenberItem();
    }

    private void initView()
    {
        setTitleText(getIntent().getExtras().getString("carnumber"));
        setSubmit("会员");
        mSellListView = (ListViewExtend) findViewById(R.id.sell_listview);
        lv_project = (ListViewExtend) findViewById(R.id.lv_project);
        lv_data = (ListViewExtend) findViewById(R.id.lv_data);
        sv = (ScrollView) findViewById(R.id.sv1);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        projectAdapter = new ProjectListAdapter(this, mProject, deleteItemCount, textChange);
        lv_project.setAdapter(projectAdapter);
        mAdapter = new RevenueGoodListAdapter(this, mGood, deleteItemCountTwo,textChangeTwo);
        lv_data.setAdapter(mAdapter);
    }

    private void initData()
    {
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
        mIsPay = getIntent().getExtras().getBoolean("isPay");
        if (mIsPay) {
            btn_sure.setText("提交");
        } else {
            btn_sure.setText("挂单");
        }
    }

    public void onGoodsAddSell(View view)
    {
        gotoActivityForResult(ProjectListActivity.class, null, 2002);
    }
    public void onGoodsClick(View view)
    {
        gotoActivityForResult(RevenueGoodsListActivity.class, null, 2003);
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
    public void onAddSure(View view)
    {
        if (!mIsPay) {
            showShortToast("挂单成功");
            return;
        }
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

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(JSONObject jsonObject) {

        }

        public void onEvent(String error) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 2002) {
            Project project = data.getParcelableExtra("project");
            allmoney += project.getPrice() * project.getLaborTime();
            mProject.add(project);
            projectAdapter.notifyDataSetChanged();
        }else if(requestCode == 2003){
            Goods goods = data.getParcelableExtra("good");
            allmoney += goods.getQuantity() * goods.getSellingPrice();
            mGood.add(goods);
            mAdapter.notifyDataSetChanged();
        }
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
    }
}
