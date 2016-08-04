package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 接车开单详情
 */
public class RevenueSellActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_cancel;
    private ListView lv_data, lv_project;//data=商品,project等于项目
    private double allmoney;
    private List<Project> mProject = new ArrayList<>();
    private List<Goods> mGood = new ArrayList<>();
    private ProjectListAdapter projectAdapter;
    private RevenueGoodListAdapter mAdapter;
    private Button btn_sure;
    private boolean mIsPay;
    private ScrollView sv;
    private CheckBox cb_wash;
    private CheckBox cb_plate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_sell);
        Utils.hideSoftInput(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
        initData();
    }

    private void initView()
    {
        setTitleText(getIntent().getExtras().getString("carnumber"));
        setSubmit("会员");
        lv_project = (ListView) findViewById(R.id.lv_project);
        lv_data = (ListView) findViewById(R.id.lv_data);
        sv = (ScrollView) findViewById(R.id.sv1);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        cb_wash = (CheckBox) findViewById(R.id.cb_wash);//套餐普洗
        cb_plate = (CheckBox) findViewById(R.id.cb_plate);//套餐镀晶
        projectAdapter = new ProjectListAdapter(this, mProject, deleteItemCount, textChange);
        lv_project.setAdapter(projectAdapter);
        mAdapter = new RevenueGoodListAdapter(this, mGood, deleteItemCountTwo,textChangeTwo);
        lv_data.setAdapter(mAdapter);
        setListViewHeight(lv_data);
        setListViewHeight(lv_project);
        sv.scrollTo(10, 10);
        onPackageChang();
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

    public void onPackageChang()
    {
        cb_wash.setOnClickListener(this);
        cb_plate.setOnClickListener(this);
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
            setListViewHeight(lv_data);
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
            setListViewHeight(lv_project);
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
        if (!(cb_wash.isChecked() || cb_plate.isChecked())) {
            showShortToast("未选择项目");
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

    /**
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
     */
    public void setListViewHeight(ListView listView)
    {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

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
            setListViewHeight(lv_project);
        }else if(requestCode == 2003){
            Goods goods = data.getParcelableExtra("good");
            allmoney += goods.getQuantity() * goods.getSellingPrice();
            mGood.add(goods);
            mAdapter.notifyDataSetChanged();
            setListViewHeight(lv_data);
        }
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
    }


    @Override
    public void onClick(View view)
    {
        cb_plate.isChecked();
        cb_wash.isChecked();
    }
}
