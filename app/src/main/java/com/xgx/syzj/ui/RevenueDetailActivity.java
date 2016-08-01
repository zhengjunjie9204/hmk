package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.RevenueDetailAutoAdapter;
import com.xgx.syzj.adapter.RevenueDetailGoodsAdapter;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.BillDetail;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.GoodsCart;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.datamodel.CartDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 收银记账详细页面
 *
 * @author zajo
 * @created 2015年09月22日 17:11
 */
public class RevenueDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String FLAG_AUTO = "AUTO";
    private static final String FLAG_GOODS = "GOODS";

    private LinearLayout ll_top, ll_zk, ll_type;
    private TextView tv_name, tv_jifen, tv_money, tv_all_count, tv_all_money, tv_zk, tv_all_value, tv_mode;
    private double totalMeonty;
    private ImageView iv_mode;
    private EditText et_rel_value;
    private Button btn_jz, btn_gd;
    private ListView lv_data;
    private int customerType = 0;
    //商品销售
    private ArrayList<Goods> goodsList = new ArrayList<>();
    private RevenueDetailGoodsAdapter goodsAdapter;
    //自定义销售
    private List<String> autoList = new ArrayList<>();
    private RevenueDetailAutoAdapter autoAdapter;
    private Member member;
    private String mDiscount = "";//折扣
    private String flag;
    private int cartId;//挂单id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_detail);

        initView();
        initData();
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initView() {
        setTitleText("收银记账");
        ll_top = (LinearLayout) findViewById(R.id.ll_top);
        ll_zk = (LinearLayout) findViewById(R.id.ll_zk);
        ll_type = (LinearLayout) findViewById(R.id.ll_type);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_jifen = (TextView) findViewById(R.id.tv_jifen);
        tv_all_count = (TextView) findViewById(R.id.tv_all_count);
        tv_all_money = (TextView) findViewById(R.id.tv_all_money);
        tv_zk = (TextView) findViewById(R.id.tv_zk);
        tv_all_value = (TextView) findViewById(R.id.tv_all_value);
        iv_mode = (ImageView) findViewById(R.id.iv_mode);
        tv_mode = (TextView) findViewById(R.id.tv_mode);
        tv_money = (TextView) findViewById(R.id.tv_money);
        et_rel_value = (EditText) findViewById(R.id.et_rel_value);
        lv_data = (ListView) findViewById(R.id.lv_data);
        btn_jz = (Button) findViewById(R.id.btn_jz);
        btn_gd = (Button) findViewById(R.id.btn_gd);

        ll_zk.setOnClickListener(this);
        ll_type.setOnClickListener(this);
        btn_jz.setOnClickListener(this);
        btn_gd.setOnClickListener(this);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            defaultFinish();
            return;
        }
        //获取会员
        member = bundle.getParcelable("member");
        if (member != null) {
            ll_top.setVisibility(View.VISIBLE);
            setSubmit("更改会员");
//            tv_name.setText(member.getAssociatorName());
//            tv_jifen.setText("积分：" + member.getCardIntegral());
//            tv_money.setText("储值：¥ " + member.getStrCardValue());
            customerType = 1;
        } else {
            ll_top.setVisibility(View.GONE);
            customerType = 0;
            setSubmit("绑定会员");
        }
        //获取数据
        List<Goods> mList = bundle.getParcelableArrayList("goods");
        List<String> moneyList = bundle.getStringArrayList("list");
        if (mList != null && mList.size() > 0) {
            //商品
            flag = FLAG_GOODS;
            goodsList.addAll(mList);
            goodsAdapter = new RevenueDetailGoodsAdapter(this);
            goodsAdapter.appendList(goodsList);
            lv_data.setAdapter(goodsAdapter);
        } else if (moneyList != null && moneyList.size() > 0) {
            //自定义销售
            flag = FLAG_AUTO;
            autoList.addAll(moneyList);
            autoAdapter = new RevenueDetailAutoAdapter(this, autoList, deleteListener);
            lv_data.setAdapter(autoAdapter);
            btn_gd.setText("取消");
        } else {
            defaultFinish();
            return;
        }
        cartId = bundle.getInt("cartId", 0);
        checkDiscount(mDiscount);
        checkCount();
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            if (result.geteCode() == CartDataModel.CART_PUT_UP) {
                showShortToast("挂单成功");
                AppManager.getAppManager().returnToActivity(RevenueActivity.class);
            } else if( result.geteCode() == CartDataModel.CART_BILL_CHECK) {
                showShortToast("结帐成功");
                Bundle bundle = new Bundle();
                bundle.putParcelable("member", member);
                bundle.putString("money", et_rel_value.getText().toString().trim());
                gotoActivity(RevenueResultActivity.class, bundle);
            }
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }
    };

    @Override
    protected void submit() {
        super.submit();
        gotoActivityForResult(RevenueMemberActivity.class, null, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jz://结帐
                showLoadingDialog(R.string.loading_cart_bill_check);
                int associatorId = 0;
                if (member != null)
                    associatorId = member.getId();
                String temp = et_rel_value.getText().toString().trim();
                double paidValue = 0;
                if (!TextUtils.isEmpty(temp)){
                    paidValue = Double.parseDouble(temp);
                }
                List<BillDetail> bdList = new ArrayList<>();
                if (FLAG_GOODS.equals(flag)){
                    for (int i = 0; i < goodsList.size(); i++){
                        BillDetail bd = new BillDetail();
                        Goods g = goodsList.get(i);
                        bd.setProductId(g.getProductId());
                        bd.setQuantity(g.getRevenueCount());
                        bd.setSellingPrice(g.getSellingPrice());
                        bd.setTotalValue(g.getRevenueCount() * g.getSellingPrice());
                        bdList.add(bd);
                    }
                }
                CartDataModel.cartBillCheck(cartId, associatorId, totalMeonty, paidValue, Utils.getPayIndex(tv_mode.getText().toString().trim()),customerType, bdList);
                break;
            case R.id.ll_zk://折扣
                CustomAlertDialog.showDiscountDialog(this, null, new CustomAlertDialog.IAlertDialogListener() {
                    @Override
                    public void onSure(Object obj) {
                        Message msg = new Message();
                        msg.what = 1000;
                        msg.obj = obj;
                        mHandler.sendMessage(msg);
                    }
                });
                break;
            case R.id.ll_type://支付类型
                CustomAlertDialog.showPayModeDialog(this,false, new CustomAlertDialog.IAlertListDialogItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        iv_mode.setBackgroundResource(Utils.getPayResIconId(position));
                        tv_mode.setText(Utils.getPayResName(position));
                    }
                });
                break;
            case R.id.btn_gd://挂单
                if (FLAG_AUTO.equals(flag))
                    defaultFinish();
                else {
                    CustomAlertDialog.showDiscountDialog(this, "请填写挂单名称（可不填写）", new CustomAlertDialog.IAlertDialogListener() {
                        @Override
                        public void onSure(Object obj) {
                            Message msg = new Message();
                            msg.what = 1001;
                            msg.obj = obj;
                            mHandler.sendMessage(msg);
                        }
                    });
                }
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    EditText etNum = (EditText) msg.obj;
                    mDiscount = etNum.getText().toString().trim();
                    if (TextUtils.isEmpty(mDiscount) || mDiscount.equals("10") || Double.parseDouble(mDiscount) == 0) {
                        mDiscount = "";
                        tv_zk.setText("全额");
                    } else {
                        tv_zk.setText(mDiscount + "折");
                    }
                    checkDiscount(mDiscount);
                    break;
                case 1001:
                    EditText etText = (EditText) msg.obj;
                    String gdTitle = etText.getText().toString().trim();
                    User user = CacheUtil.getmInstance().getUser();
                    double allMoney = 0;
                    List<GoodsCart> gcList = new ArrayList<>();
                    for (int i = 0; i < goodsList.size(); i++) {
                        GoodsCart gc = new GoodsCart();
                        Goods g = goodsList.get(i);
                        gc.setProductId(g.getProductId());
                        gc.setQuantity(g.getRevenueCount());
                        gc.setTotalValue(g.getRevenueCount() * g.getSellingPrice());
                        allMoney += g.getRevenueCount() * g.getSellingPrice();
                        gc.setDescription("");
                        gcList.add(gc);
                    }
                    int associatorId = 0;
                    if (member != null)
                        associatorId = member.getId();
                    showLoadingDialog(R.string.loading_cart_put_up);
                  //  CartDataModel.cartPutUp(gdTitle, associatorId, allMoney, user.getAccountId(), "", gcList);
                    break;
            }
        }
    };

    /**
     * 计算总和
     */
    private void checkCount() {
//        double am = 0;
        if (FLAG_AUTO.equals(flag)) {
            tv_all_count.setText(String.format("共计:%s件", autoList.size()));
            for (String money : autoList) {
                totalMeonty += Double.parseDouble(money);
            }
        } else if (FLAG_GOODS.equals(flag)) {
            int count = 0;
            for (Goods g : goodsList) {
                totalMeonty += g.getSellingPrice()*g.getRevenueCount();
                count += g.getRevenueCount();
            }
            tv_all_count.setText(String.format("共计:%s件", count));
        }
        DecimalFormat df = new DecimalFormat("#.00");
        tv_all_money.setText("总计:¥ " + df.format(totalMeonty));
    }

    /**
     * 计算折扣
     */
    private void checkDiscount(String dis) {
        double allMoney = 0;
        if (FLAG_AUTO.equals(flag)) {
            for (String money : autoList) {
                allMoney += Double.parseDouble(money);
            }
        } else if (FLAG_GOODS.equals(flag)) {
            for (Goods g : goodsList) {
                allMoney += g.getSellingPrice()*g.getRevenueCount();
            }
        }
        if (!TextUtils.isEmpty(dis))
            allMoney = allMoney * (Double.parseDouble(dis) / 10);
        DecimalFormat df = new DecimalFormat("#.00");
        tv_all_value.setText(df.format(allMoney));
        et_rel_value.setText(df.format(allMoney));
    }

    private RevenueDetailAutoAdapter.RevenueAutoItemDeleteListener deleteListener = new RevenueDetailAutoAdapter.RevenueAutoItemDeleteListener() {
        @Override
        public void onDelete(int position) {
            autoList.remove(position);
            autoAdapter.notifyDataSetChanged();
            checkDiscount(mDiscount);
            checkCount();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 1000:
                Member m = data.getParcelableExtra("member");
                if (m != null) {
                    member = m;
                    customerType = 1;
                    ll_top.setVisibility(View.VISIBLE);
//                    setSubmit("更改会员");
//                    tv_name.setText(member.getAssociatorName());
//                    tv_jifen.setText("积分：" + member.getCardIntegral());
//                    tv_money.setText("储值：¥ " + member.getStrCardValue());
                }
                break;
        }
    }
}
