package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.OrderDetailItemAdapter;
import com.xgx.syzj.adapter.OrderDetailItemAdapter2;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Combos;
import com.xgx.syzj.bean.OrderDetail;
import com.xgx.syzj.bean.OrderList;
import com.xgx.syzj.bean.PayTimes;
import com.xgx.syzj.bean.PayType;
import com.xgx.syzj.bean.Product;
import com.xgx.syzj.bean.ProductItems;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.SaleListRecordModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.ListViewExtend;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 销售详情
 * @author zajo
 * @created 2015年09月24日 17:37
 */
public class SaleDetailActivity extends BaseActivity {
    private ListViewExtend mProductListView;
    private ListViewExtend mItemListView;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_number;
    private TextView tv_title1;
    private TextView tv_title2;
    private OrderDetailItemAdapter mItemAdapter;
    private OrderDetailItemAdapter mProAdapter;
    private List<ProductItems> mItemList;
    private List<Product> mProList;
    private ArrayList<OrderDetail> mOrderDet;
    //数据
    private OrderList orderList;
    private SaleListRecordModel mDataModel;
    private TextView tv_type;
    private TextView tv_pay;
    private Map map;
    private OrderDetailItemAdapter2 mItemAdapter2;
    private OrderDetailItemAdapter2 mProAdapter2;
    private ArrayList<Combos> mCombos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);
        setTitleText("销售详情");
        setSubmit("作废");

        initView();
        setData();
//        payorder_member_money_record.xml
        mItemAdapter = new OrderDetailItemAdapter(this, mItemList, null, 0);
        mProAdapter = new OrderDetailItemAdapter(this, null, mProList, 1);
        mItemAdapter2 = new OrderDetailItemAdapter2(this, mItemList,mCombos,0,mOrderDet);
        if("1".equals(orderList.getOrderType())) {
            mItemListView.setAdapter(mItemAdapter);
            mProductListView.setAdapter(mProAdapter);
        }else if("0".equals(orderList.getOrderType())){
            mItemListView.setAdapter(mItemAdapter2);

        }
        mItemAdapter.notifyDataSetChanged();
        mItemAdapter2.notifyDataSetChanged();
        mProAdapter.notifyDataSetChanged();
//        mProAdapter2.notifyDataSetChanged();
    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_number= (TextView) findViewById(R.id.tv_number);
        tv_pay= (TextView) findViewById(R.id.tv_pay);
        tv_type =(TextView) findViewById(R.id.tv_type);
        tv_title1= (TextView) findViewById(R.id.title1);
        tv_title2= (TextView) findViewById(R.id.title2);
        mItemListView = (ListViewExtend) findViewById(R.id.lv_data);
        mProductListView = (ListViewExtend) findViewById(R.id.lv_data2);

    }

    private void setData(){
        mCombos=new ArrayList<>();
        mItemList = new ArrayList<>();
        mProList = new ArrayList<>();
        mOrderDet = new ArrayList<>();
        orderList = (OrderList) getIntent().getSerializableExtra("order");
        EventCenter.getInstance().register(eventHandler);
        mDataModel = new SaleListRecordModel(Constants.LOAD_COUNT);
        SaleListRecordModel.getOrderDetail(orderList.getId());
        tv_name.setText(orderList.getName());
        tv_time.setText(orderList.getCreateTime());
        tv_number.setText(orderList.getCarNumber());
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler(){
        public void onEvent(Result result){
            if (result.getStatus() == 200) {
                try {
                    JSONObject json = new JSONObject(result.getResult());
                    List<ProductItems> list = FastJsonUtil.json2List(json.getString("items"), ProductItems.class);
                    mItemList.addAll(list);
                    List<Product> list1 = FastJsonUtil.json2List(json.getString("products"), Product.class);
                    mProList.addAll(list1);
                    List<PayTimes> list2 = FastJsonUtil.json2List(json.getString("payTimes"), PayTimes.class);
                    String payOrderType = json.optString("payOrderType", "0");
                    String payType = json.optString("payType", "0");
                    long orderAmount = json.optLong("orderAmount", 0);
                    long fee = json.optLong("fee", 0);
                    List<Combos> combos = FastJsonUtil.json2List(json.getString("combos"), Combos.class);
                    mCombos.addAll(combos);
                    long orderAmout = json.optLong("orderAmout", 0);
                    int status = json.optInt("status", 0);
                    String employee = json.optString("employee", "0");
                    String carType = json.optString("carType", "0");
                    OrderDetail orderDetail = new OrderDetail(payOrderType, payType, orderAmount, fee, employee, carType, status,orderAmout);
                    mOrderDet.add(orderDetail);
                    tv_type.setText(carType);
                    tv_name.setText(employee);
                    HashMap<String, String> map = new HashMap<>();
                    initMap(map);
                    String PayTye = map.get(payType);
                    tv_pay.setText(PayTye);
                    mItemAdapter.notifyDataSetChanged();
                    mItemAdapter2.notifyDataSetChanged();
                    mProAdapter.notifyDataSetChanged();
//                    mProAdapter2.notifyDataSetChanged();
                    if (list.size()<1){
                        tv_title1.setVisibility(View.GONE);
                    }
                    if (list1.size()<1){
                        tv_title2.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onEvent(String  errorStr){
            showShortToast(errorStr);
        }
    };

    private void initMap(Map payType) {
        payType.put("1","微信支付");
        payType.put("2","余额支付");
        payType.put("3","现金支付");
        payType.put("4","银行卡支付");
        payType.put("5","支付宝支付");
        payType.put("6","储值，现金");
        payType.put("7","储值，微信");
        payType.put("8","项目从计次里面扣除");
        payType.put("9","项目不是从计次里面扣");

    }

    @Override
    public void onSubmit(View view)
    {
        super.onSubmit(view);
        mDataModel.setOrderCancel(String.valueOf(orderList.getId()));
    }
}
