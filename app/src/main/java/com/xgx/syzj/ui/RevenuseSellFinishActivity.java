package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;
import com.xgx.syzj.widget.ListViewExtend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RevenuseSellFinishActivity extends BaseActivity implements View.OnClickListener, RevenueCountItemAdapter.CountItems {
    public static final int HANDLER_MONEY = 0x101;
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
    private Map<Integer, CountItemsBean> mdata;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 0x101) {
                setAllMoney();
            }
        }
    };

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
            //显示
            getOrderDetails();
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
        projectAdapter = new ProjectListAdapter(this, mProject, mHandler);
        lv_project.setAdapter(projectAdapter);
        mAdapter = new RevenueGoodListAdapter(this, mGood, mHandler);
        lv_data.setAdapter(mAdapter);
        lv_project.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final Project project = mProject.get(position);
                CustomAlertDialog.editTextDialog(RevenuseSellFinishActivity.this, String.valueOf(project.getPrice()), "请输入单价", new CustomAlertDialog.IAlertDialogListener() {
                    @Override
                    public void onSure(Object obj)
                    {
                        project.setPrice((int) obj);
                        projectAdapter.notifyDataSetChanged();
                        setAllMoney();
                    }
                });
            }
        });
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final Goods goods = mGood.get(position);
                CustomAlertDialog.editTextDialog(RevenuseSellFinishActivity.this, String.valueOf(goods.getSellingPrice()), "请输入单价", new CustomAlertDialog.IAlertDialogListener() {
                    @Override
                    public void onSure(Object obj)
                    {
                        goods.setSellingPrice((int) obj);
                        mAdapter.notifyDataSetChanged();
                        setAllMoney();
                    }
                });
            }
        });
        btn_sure.setText("结账");
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
        if (null != countItemsList) {
            mCountAdapter = new RevenueCountItemAdapter(this, countItemsList, this);
            mSellListView.setAdapter(mCountAdapter);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.btn_sure:
                try {
                    JSONArray productList = new JSONArray();
                    JSONArray itemList = new JSONArray();
                    for (Goods goods : mGood) {
                        if(goods.getUid() > 0){
                            JSONObject json = new JSONObject();
                            json.put("productId", goods.getProductId());
                            json.put("amount", goods.getCount());
                            productList.put(json);
                        }
                    }
                    for (Project project : mProject) {
                        if(project.getId() >0){
                            JSONObject json = new JSONObject();
                            json.put("itemId", project.getId());
                            json.put("amount", project.getLaborTime());
                            itemList.put(json);
                        }
                    }
                    if (null != mdata) {
                        for (Map.Entry entry : mdata.entrySet()) {
                            CountItemsBean value = (CountItemsBean) entry.getValue();
                            JSONObject json = new JSONObject();
                            json.put("itemId", value.getItemId());
                            json.put("amount", value.getCount());
                            itemList.put(json);
                        }
                    }
                    if (null != order) {
                        if (mGood.size() > 0 && mProject.size() > 0 && itemList.length() == 0 && productList.length() == 0) {
                            orderPayItem(order.getId(), 0, 3);
                        } else if (itemList.length() == 0) {
                            showShortToast("请选择项目");
                        } else {
                            if (productList.length() == 0) {
                                productList = null;
                            }
                            showLoadingDialog(R.string.loading_date);
                            editOrder(order.getId(), itemList, productList);
                        }
                    } else {
                        if (mGood.size() == 0 && mProject.size() == 0 && (null == mdata || mdata.size() == 0)) {
                            showShortToast("请选择商品或者项目");
                            return;
                        } else {
                            showLoadingDialog(R.string.loading_date);
                            if (mGood.size() > 0 && mProject.size() == 0 && (null == mdata || mdata.size() == 0)) {//只含商品
                                orderPayProduct(0, 3, String.valueOf(allmoney), null, productList);
                            } else {//包含商品和项目
                                if (productList.length() == 0) {
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
            Iterator<Project> proIterator = mProject.iterator();
            while (proIterator.hasNext()) {
                Project project1 = proIterator.next();
                if (project1.getId() == project.getId()) {
                    proIterator.remove();
                    break;
                }
            }
            mProject.add(project);
            btn_sure.setText("挂单");
            projectAdapter.notifyDataSetChanged();
        } else if (requestCode == 2003) {
            Goods goods = data.getParcelableExtra("good");
            Iterator<Goods> goodIterator = mGood.iterator();
            while (goodIterator.hasNext()) {
                Goods goods1 = goodIterator.next();
                if (goods1.getUid() == goods.getUid()) {
                    goodIterator.remove();
                    break;
                }
            }
            mGood.add(goods);
            mAdapter.notifyDataSetChanged();
        }
        setAllMoney();
    }

    private void setAllMoney()
    {
        allmoney = 0;
        for (Project project : mProject) {
            allmoney += project.getPrice() * project.getLaborTime();
        }
        for (Goods goods : mGood) {
            allmoney += goods.getCount() * goods.getSellingPrice();
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
                    showShortToast("挂单成功");
                    setResult(RESULT_OK);
                    finish();
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

    //编辑已完成的订单(7.11)
    private void editOrder(int payOrderId, JSONArray itemList, JSONArray productList)
    {
        Api.editOrder(payOrderId, itemList, productList, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                if (result.getStatus() == 200) {
                    showShortToast("编辑成功");
                    finish();
                }else{
                    showShortToast(result.getMessage());
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

    private void getOrderDetails()
    {
        Api.getOrderDetail(order.getId(), new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                try {
                    if (result.getStatus() == 200) {
                        JSONObject json = new JSONObject(result.getResult());
                        List<Project> proList = FastJsonUtil.json2List(json.getString("items"), Project.class);
                        if(null != proList && proList.size() >0){
                            mProject.addAll(proList);
                            projectAdapter.notifyDataSetChanged();

                        }
                        List<Goods> goodList = FastJsonUtil.json2List(json.getString("products"), Goods.class);
                        if(null != goodList && goodList.size() >0){
                            mGood.addAll(goodList);
                            mAdapter.notifyDataSetChanged();
                        }
                        setAllMoney();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String message)
            {

            }
        });
    }


    @Override
    public void getCountItems(Map<Integer, CountItemsBean> data, int position)
    {
        mdata = data;
        if (mdata.size() > 0) {
            btn_sure.setText("挂单");
        } else {
            btn_sure.setText("结账");
        }
    }
}
