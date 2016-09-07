package com.xgx.syzj.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.ProjectRevenueAdapter;
import com.xgx.syzj.adapter.RevenueCountItemAdapter;
import com.xgx.syzj.adapter.RevenueGoodListAdapter;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.CountItemsBean;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.OrderList;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.MemberDataModel;
import com.xgx.syzj.datamodel.SellFinishModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.ArithUtils;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevenuseSellFinishActivity extends BaseActivity implements View.OnClickListener, RevenueCountItemAdapter.CountItems {
    public static final int HANDLER_MONEY = 0x101;
    private SwipeMenuListView sellListView, lv_project, lv_data;
    private Button btn_cancel;
    private EditText et_distance;
    private Button btn_sure;
    private double allmoney;
    private List<Project> mProject = new ArrayList<>();
    private List<Goods> mGood = new ArrayList<>();
    private ProjectRevenueAdapter projectAdapter;
    private RevenueGoodListAdapter mAdapter;
    //传递值
    private String distance;
    private OrderList order;
    private int memberId;
    private String carNumber;
    private List<CountItemsBean> countItemsList = new ArrayList<>();
    private RevenueCountItemAdapter mCountAdapter;
    private Map<Integer, CountItemsBean> mdata = new HashMap<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x101) {
                setAllMoney();
            }
        }
    };
    private String finish;
    private List<Project> mitemList;
    private List<Goods> mgoodList;
    private double projectMoney;
    private SellFinishModel sellFinishModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenuse_sell_finish);
        Utils.hideSoftInput(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        EventCenter.bindContainerAndHandler(this, eventHandler);
        sellFinishModel = new SellFinishModel();
        initData();
        initView();
    }

    private void initData() {
        order = (OrderList) getIntent().getSerializableExtra("order");
        finish = getIntent().getStringExtra("finish");
        if (null == order) {
            carNumber = getIntent().getStringExtra("carNumber");
            memberId = getIntent().getIntExtra("memberId", 0);
            List<CountItemsBean> list = (List<CountItemsBean>) getIntent().getSerializableExtra("countItemsList");
            if (null != list && list.size() > 0) {
                countItemsList.addAll(list);
            }
            boolean isMember = getIntent().getBooleanExtra("isMember", false);
            distance = getIntent().getStringExtra("distance");
            setTitleText(carNumber);
            if (isMember) {
                setSubmit("会员");
            }
        } else {
            setTitleText(order.getCarNumber());
            mProject.clear();
            mGood.clear();
            sellFinishModel.getOrderDetail(order.getId());
        }
    }

    private void initView() {
        sellListView = (SwipeMenuListView) findViewById(R.id.sell_listview);
        lv_project = (SwipeMenuListView) findViewById(R.id.lv_project);
        lv_data = (SwipeMenuListView) findViewById(R.id.lv_data);
        et_distance = (EditText) findViewById(R.id.et_distance);
        et_distance.setText(distance);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        LinearLayout ll_distance = (LinearLayout) findViewById(R.id.ll_distance);
        if ("已完成".equals(finish)) {
            ll_distance.setVisibility(View.GONE);
        }
        //选择项目工时
        projectAdapter = new ProjectRevenueAdapter(this, mProject, mHandler);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(RevenuseSellFinishActivity.this);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(Utils.dp2px(RevenuseSellFinishActivity.this, 90));
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        sellListView.setMenuCreator(creator);
        sellListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                countItemsList.remove(position);
                mCountAdapter.notifyDataSetChanged();
                setListViewHeight(sellListView);
                return false;
            }
        });
        lv_project.setMenuCreator(creator);
        lv_project.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Project project = mProject.remove(position);
                if (project.isFinish()) {
                    allmoney -= project.getTotalPrice();
                } else {
                    if (project.getMylaborTime() > 0) {
                        allmoney -= ((project.getPrice() / project.getLaborTime()) * project.getMylaborTime());
                    } else {
                        allmoney -= ((project.getPrice()));

                    }
                }
                btn_cancel.setText(String.format("合计金额：￥%s", ArithUtils.showString(allmoney)));
                projectAdapter.notifyDataSetChanged();
                setListViewHeight(lv_project);
                return false;
            }
        });
        lv_data.setMenuCreator(creator);
        lv_data.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Goods goods = mGood.remove(position);
                allmoney -= goods.getCount() * goods.getSellingPrice();
                btn_cancel.setText(String.format("合计金额：￥%s", ArithUtils.showString(allmoney)));
                mAdapter.notifyDataSetChanged();
                setListViewHeight(lv_data);
                return false;
            }
        });
        lv_project.setAdapter(projectAdapter);
        //选择商品
        mAdapter = new RevenueGoodListAdapter(this, mGood, mHandler);
        lv_data.setAdapter(mAdapter);
        //选择计次项目
        mCountAdapter = new RevenueCountItemAdapter(this, countItemsList, this);
        sellListView.setAdapter(mCountAdapter);
        lv_project.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Project project = mProject.get(position);
                CustomAlertDialog.editTextDialog(RevenuseSellFinishActivity.this, "请输入单价", new CustomAlertDialog.IAlertDialogListener() {
                    @Override
                    public void onSure(Object obj) {
                        project.setChangePrice(true);
                        project.setPrice((int) obj);
                        projectAdapter.notifyDataSetChanged();
                        setAllMoney();
                    }
                });
            }
        });
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Goods goods = mGood.get(position);
                CustomAlertDialog.editTextDialog(RevenuseSellFinishActivity.this, "请输入单价", new CustomAlertDialog.IAlertDialogListener() {
                    @Override
                    public void onSure(Object obj) {
                        goods.setSellingPrice((int) obj);
                        mAdapter.notifyDataSetChanged();
                        setAllMoney();
                    }
                });
            }
        });
        setListViewHeight(lv_project);
        setListViewHeight(lv_data);
        setListViewHeight(sellListView);
        btn_sure.setText("结账");
        btn_cancel.setText(String.format("合计金额：￥%s", allmoney));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                try {
                    JSONArray productList = new JSONArray();
                    JSONArray itemList = new JSONArray();
                    for (Goods goods : mGood) {
                        if (goods.getUid() > 0&&goods.getCount()>0) {
                            JSONObject json = new JSONObject();
                            json.put("productId", goods.getProductId());
                            json.put("amount", goods.getCount());
                            json.put("price", goods.getSellingPrice());
                            if (goods.isFinish()) {
                                json.put("finish", true);
                            } else {
                                json.put("finish", false);

                            }
                            productList.put(json);
                        }
                    }
                    for (Project project : mProject) {
                        if (project.getId() > 0) {
                            JSONObject json = new JSONObject();
                            json.put("itemId", project.getId());
                            json.put("labourTime", project.getMylaborTime() > 0 ? project.getMylaborTime() : project.getLaborTime());
                            json.put("price", project.getPrice());
                            if (project.isFinish()) {
                                json.put("finish", true);
                            } else {
                                json.put("finish", false);
                            }
                            itemList.put(json);
                        }

                    }
                    if (null != mdata) {
                        for (Map.Entry entry : mdata.entrySet()) {
                            CountItemsBean value = (CountItemsBean) entry.getValue();
                            JSONObject json = new JSONObject();
                            json.put("itemId", value.getItemId());
                            json.put("labourTime", value.getLabourTime());
                            json.put("price", 0);
                            itemList.put(json);
                        }
                    }
                    if (null != order) {
                        if (itemList.length() == 0 && productList.length() == 0) {
                            orderPayItem(order.getId(), 0, 3);
                        } else if (itemList.length() != 0 && productList.length() != 0 && (mProject.size() + countItemsList.size()) == mitemList.size() && mGood.size() == mgoodList.size()) {
                            CustomAlertDialog.showPayModeDialog(this, true, new CustomAlertDialog.IAlertListDialogItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    orderPayItem(order.getId(), 0, 3);
                                }
                            });
                        } else if (itemList.length() != 0 && (mProject.size() + countItemsList.size()) == mitemList.size() && mGood.size() == mgoodList.size()) {
                            CustomAlertDialog.showPayModeDialog(this, true, new CustomAlertDialog.IAlertListDialogItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    orderPayItem(order.getId(), 0, 3);
                                }
                            });
                        } else if (itemList.length() == 0) {
                            showShortToast("请选择项目");
                        } else {
                            if (productList.length() == 0) {
                                productList = null;
                            }

                            try {
                                if (productList != null) {
                                    for (int i = 0; i < productList.length(); i++) {
                                        JSONObject json = productList.getJSONObject(i);
                                        boolean finish = json.getBoolean("finish");
                                        if (finish) {
                                            productList.remove(i);
                                        }
                                    }
                                }
                                if(itemList!=null) {
                                    for (int i = 0; i < itemList.length(); i++) {
                                        JSONObject json = itemList.getJSONObject(i);
                                        boolean finish = json.getBoolean("finish");
                                        if (finish) {
                                            itemList.remove(i);
                                        }
                                    }
                                }
                                if (productList != null) {
                                    for (int i = 0; i < productList.length(); i++) {
                                        JSONObject json = productList.getJSONObject(i);
                                        boolean finish = json.getBoolean("finish");
                                        if (finish) {
                                            productList.remove(i);
                                        }
                                    }
                                }
                                if(itemList!=null) {
                                    for (int i = 0; i < itemList.length(); i++) {
                                        JSONObject json = itemList.getJSONObject(i);
                                        boolean finish = json.getBoolean("finish");
                                        if (finish) {
                                            itemList.remove(i);
                                        }
                                    }
                                }
                            }
                            finally{
                                showLoadingDialog(R.string.loading_date);
                                //编辑已完成的订单(7.11)
                                sellFinishModel.editOrder(order.getId(), itemList, productList);
                            }
                        }
                    } else {
                        if (mGood.size() == 0 && mProject.size() == 0 && (null == mdata || mdata.size() == 0)) {
                            showShortToast("请选择商品或者项目");
                            return;
                        } else {
                            showLoadingDialog(R.string.loading_date);
                            if (mGood.size() > 0 && mProject.size() == 0 && (null == mdata || mdata.size() == 0)) {//只含商品
                                final JSONArray finalProductList = productList;
                                CustomAlertDialog.showPayModeDialog(this, true, new CustomAlertDialog.IAlertListDialogItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        //支付只含商品的订单(7.1)
                                        sellFinishModel.orderPayProduct(memberId,0,3,String.valueOf(allmoney), null, finalProductList);
                                    }
                                });
                            } else {//包含商品和项目
                                if (productList.length() == 0) {
                                    productList = null;
                                }
                                String diatance = et_distance.getText().toString();
                                //创建含项目订单(7.2)
                                sellFinishModel.orderCreate(memberId,diatance,String.valueOf(allmoney), productList, itemList);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }



    private void setAllMoney() {
        allmoney = 0;
        for (Project project : mProject) {
            if (project.isFinish()) {
                allmoney += project.getTotalPrice();
            } else {
                if (project.getMylaborTime() > 0) {
                    allmoney += ((project.getPrice() / project.getMylaborTime()) * project.getMylaborTime());
                } else {
                    allmoney += ((project.getPrice()));

                }
            }
        }

        for (Goods goods : mGood) {
            allmoney += goods.getCount() * goods.getSellingPrice();
        }
        btn_cancel.setText(String.format("合计金额：￥%s", ArithUtils.showString(allmoney)));
    }

    @Override
    public void onSubmit(View view) {
        super.onSubmit(view);
        Intent intent = new Intent(this, RevenueMemberActivity.class);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
    }

    private void toFinish() {
        showShortToast("支付成功");
        Bundle bundle = new Bundle();
        bundle.putString("money", allmoney + "");
        bundle.putParcelable("member", new Member());
        gotoActivity(RevenueResultActivity.class, bundle);
    }

    //支付含项目订单(7.5)
    private void orderPayItem(int payOrderId, int payTwiceFlag, int payType) {
       sellFinishModel.orderPayItem(payOrderId,payTwiceFlag,payType,null);
    }


    private SimpleEventHandler eventHandler = new SimpleEventHandler(){
        public void onEvent(JSONObject json)  {

        };
        public void onEvent(Result result) {
            hideLoadingDialog();
            if(result.geteCode()==sellFinishModel.getOrderDetail){
                if (result.getStatus() == 200){
                    try {
                        JSONObject json = new JSONObject(result.getResult());
                        mitemList = FastJsonUtil.json2List(json.getString("items"), Project.class);
                        if (null != mitemList && mitemList.size() > 0) {
                            for (Project item : mitemList) {
                                if (item.getPayType() == 8) {
                                    countItemsList.add(new CountItemsBean(item.getId(), item.getName(), (int) item.getLaborTime()));
                                } else {
                                    item.setFinish(true);
                                    mProject.add(item);
                                }
                                projectAdapter.notifyDataSetChanged();
                            }
                            mCountAdapter.setUnCheck();
                            mCountAdapter.notifyDataSetChanged();
                            setListViewHeight(lv_project);
                            setListViewHeight(sellListView);

                        }
                        mgoodList = FastJsonUtil.json2List(json.getString("products"), Goods.class);
                        if (null != mgoodList && mgoodList.size() > 0) {
                            for (Goods goods : mgoodList) {
                                goods.setFinish(true);
                                mGood.add(goods);
                                mAdapter.notifyDataSetChanged();
                                setListViewHeight(lv_data);
                            }
                        }
                        setAllMoney();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (result.geteCode() == sellFinishModel.orderCreate) {
                if (result.getStatus() == 200) {
                    showShortToast("挂单成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showShortToast("" + result.getMessage());
                }
                hideLoadingDialog();
            }
            if(result.geteCode()==sellFinishModel.orderPayProduct){
                if (result.getStatus() == 200) {
                    toFinish();
                } else {
                    showShortToast("" + result.getMessage());
                }
                hideLoadingDialog();
            }
            if(result.geteCode()==sellFinishModel.editOrder){
                if (result.getStatus() == 200) {
                    showShortToast("编辑成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showShortToast(result.getMessage());
                }
                hideLoadingDialog();
            }
            if(result.geteCode()==sellFinishModel.orderPayItem){
                if (result.getStatus() == 200) {
                    toFinish();
                } else {
                    showShortToast("" + result.getMessage());
                }
                hideLoadingDialog();
            }
        }
        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }
    };

    @Override
    public void getCountItems(Map<Integer, CountItemsBean> data, int position) {
        mdata = data;
        if (mdata.size() > 0) {
            btn_sure.setText("挂单");
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 2002) {
            ArrayList<Project> list = data.getExtras().getParcelableArrayList("list");
            mProject.addAll(list);
            btn_sure.setText("挂单");
            projectAdapter.notifyDataSetChanged();
            setListViewHeight(lv_project);
        } else if (requestCode == 2003) {
            ArrayList<Goods> list = data.getParcelableArrayListExtra("goodsList");
            mGood.clear();
            mGood.addAll(list);
            if (order != null) {
                btn_sure.setText("挂单");
            }
            mAdapter.notifyDataSetChanged();
            setListViewHeight(lv_data);
        }
        setAllMoney();
    }
}
