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
import com.xgx.syzj.adapter.RevenueCountItemAdapter;
import com.xgx.syzj.adapter.RevenueGoodListAdapter;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.CountItemsBean;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.OrderList;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;
import com.xgx.syzj.widget.ListViewExtend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RevenuseSellFinishActivity extends BaseActivity implements View.OnClickListener {
    private ListViewExtend mSellListView;
    private ListViewExtend lv_project;
    private ListViewExtend lv_data;
    private ScrollView sv;
    private Button btn_cancel;
    private Button btn_sure;
    private double allmoney;
    private List<Project> mProject = new ArrayList<>();
    private List<Goods> mGood = new ArrayList<>();
    private ProjectListAdapter projectAdapter;
    private RevenueGoodListAdapter mAdapter;
    //传递值
    private OrderList order;
    private int memberId;
    private String carNumber;
    private List<CountItemsBean> countItemsList;
    private RevenueCountItemAdapter mCountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenuse_sell_finish);
        Utils.hideSoftInput(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initData();
        initView();
    }

    private void initData()
    {
        order = (OrderList) getIntent().getSerializableExtra("order");
        if (null == order) {
            carNumber = getIntent().getStringExtra("carNumber");
            memberId = getIntent().getIntExtra("memberId", 0);
            countItemsList = (List<CountItemsBean>) getIntent().getSerializableExtra("countItemsList");
            boolean isMember = getIntent().getBooleanExtra("isMember", false);
            setTitleText(carNumber);
            if (isMember) {
                setSubmit("会员");
            }
        } else {
            setTitleText(order.getCarNumber());
        }
    }

    private void initView()
    {
        mSellListView = (ListViewExtend) findViewById(R.id.sell_listview);
        lv_project = (ListViewExtend) findViewById(R.id.lv_project);
        lv_data = (ListViewExtend) findViewById(R.id.lv_data);
        sv = (ScrollView) findViewById(R.id.sv1);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        projectAdapter = new ProjectListAdapter(this, mProject, deleteItemCount, textChange);
        lv_project.setAdapter(projectAdapter);
        mAdapter = new RevenueGoodListAdapter(this, mGood, deleteItemCountTwo, textChangeTwo);
        lv_data.setAdapter(mAdapter);
        btn_sure.setText("结账");
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
        if (null != countItemsList) {
            mCountAdapter = new RevenueCountItemAdapter(this, countItemsList);
            mSellListView.setAdapter(mCountAdapter);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.btn_sure:
                try {
                    if (null != order) {
                        orderPayItem(order.getId(), 0, 3);
                    } else {
                        if (mGood.size() == 0 && mProject.size() == 0) {
                            showShortToast("请选择商品或者项目");
                            return;
                        } else {
                            JSONArray productList = new JSONArray();
                            JSONArray itemList = new JSONArray();
                            for (Goods goods : mGood) {
                                JSONObject json = new JSONObject();
                                json.put("productId", goods.getProductId());
                                json.put("amount", goods.getQuantity());
                                productList.put(json);
                            }
                            for (Project project : mProject) {
                                JSONObject json = new JSONObject();
                                json.put("itemId", project.getId());
                                json.put("amount", project.getLaborTime());
                                itemList.put(json);
                            }
                            showLoadingDialog(R.string.loading_date);
                            if (mGood.size() > 0 && mProject.size() == 0) {//只含商品
                                orderPayProduct(0, 3, String.valueOf(allmoney), null, productList);
                            } else {//包含商品和项目
                                if (productList.length() ==  0) {
                                    productList = null;
                                }
                                orderCreate(String.valueOf(allmoney), 3, productList, itemList);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 2002) {
            Project project = data.getParcelableExtra("project");
            allmoney += project.getPrice() * project.getLaborTime();
            mProject.add(project);
            btn_sure.setText("挂单");
            projectAdapter.notifyDataSetChanged();
        } else if (requestCode == 2003) {
            Goods goods = data.getParcelableExtra("good");
            allmoney += goods.getQuantity() * goods.getSellingPrice();
            mGood.add(goods);
            mAdapter.notifyDataSetChanged();
        }
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
    }

    @Override
    public void onSubmit(View view)
    {
        super.onSubmit(view);
        Intent intent = new Intent(this, RevenueMemberActivity.class);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
    }

    private void toFinish()
    {
        showShortToast("支付成功");
        Bundle bundle = new Bundle();
        bundle.putString("money", allmoney + "");
        bundle.putParcelable("member", new Member());
        gotoActivity(RevenueResultActivity.class, bundle);
    }

    //支付只含商品的订单(7.1)
    private void orderPayProduct(int payTwiceFlag, int payType, String fee, String authCode, JSONArray productList)
    {
        Api.orderPayProduct(memberId, payTwiceFlag, payType, fee, authCode, productList, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                if (result.getStatus() == 200) {
                    toFinish();
                } else {
                    showShortToast("" + result.getMessage());
                }
                hideLoadingDialog();
            }

            @Override
            public void onError(String errorCode, String message)
            {
                hideLoadingDialog();
            }
        });
    }

    //创建含项目订单(7.2)
    private void orderCreate(String fee, int payType, JSONArray productList, JSONArray itemList)
    {
        Api.orderCreate(memberId, fee, payType, productList, itemList, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                if (result.getStatus() == 200) {
                    toFinish();
                } else {
                    showShortToast("" + result.getMessage());
                }
                hideLoadingDialog();
            }

            @Override
            public void onError(String errorCode, String message)
            {
                hideLoadingDialog();
            }
        });
    }

    //支付含项目订单(7.5)
    private void orderPayItem(int payOrderId, int payTwiceFlag, int payType)
    {
        Api.orderPayItem(payOrderId, payTwiceFlag, payType, null, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                if (result.getStatus() == 200) {
                    toFinish();
                } else {
                    showShortToast("" + result.getMessage());
                }
                hideLoadingDialog();
            }

            @Override
            public void onError(String errorCode, String message)
            {
                hideLoadingDialog();
            }
        });
    }

}
