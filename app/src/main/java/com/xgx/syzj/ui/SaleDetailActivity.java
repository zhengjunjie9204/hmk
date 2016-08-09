package com.xgx.syzj.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.OrderDetailItemAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.OrderList;
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
import java.util.List;

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
    private TextView tv_mobile;
    private TextView tv_time;
    private TextView tv_number;
    private OrderDetailItemAdapter mItemAdapter;
    private OrderDetailItemAdapter mProAdapter;
    private List<ProductItems> mItemList;
    private List<Product> mProList;
    //数据
    private OrderList orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);
        setTitleText("销售详情");
        initView();
        setData();
        mItemAdapter = new OrderDetailItemAdapter(this,mItemList,null,0);
        mProAdapter = new OrderDetailItemAdapter(this,null,mProList,1);
        mItemListView.setAdapter(mItemAdapter);
        mProductListView.setAdapter(mProAdapter);
    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_number= (TextView) findViewById(R.id.tv_number);
        mItemListView = (ListViewExtend) findViewById(R.id.lv_data);
        mProductListView = (ListViewExtend) findViewById(R.id.lv_data2);
    }

    private void setData(){
        mItemList = new ArrayList<>();
        mProList = new ArrayList<>();
        orderList = (OrderList) getIntent().getSerializableExtra("order");
        EventCenter.getInstance().register(eventHandler);
        SaleListRecordModel.getOrderDetail(orderList.getId());
        tv_name.setText(orderList.getName());
        tv_mobile.setText(orderList.getMobile());
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
                    mItemAdapter.notifyDataSetChanged();
                    mProAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onEvent(String  errorStr){
            showShortToast(errorStr);
        }
    };
}
