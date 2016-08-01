package com.xgx.syzj.ui;

import android.drm.DrmManagerClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.SaleDetailAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.BillItemDetailBean;
import com.xgx.syzj.bean.BillListItemBean;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.BillGoodsReturnModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 销售详情
 * @author zajo
 * @created 2015年09月24日 17:37
 */
public class SaleDetailActivity extends BaseActivity {

    private SaleDetailAdapter mAdapter;
    private ListView lv_data;
    private ArrayList<BillItemDetailBean> mList = new ArrayList<>();
    private TextView tv_time,tv_name;
    private TextView tv_card;
    private TextView tv_money;
    private TextView tv_all_pay;
    private TextView tv_count;
    private TextView tv_phone;
    private TextView tv_type;
    private TextView tv_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);
        setTitleText("销售详情");
        initView();
        setData();
        mAdapter = new SaleDetailAdapter(this);
        mAdapter.appendList(mList);
        lv_data.setAdapter(mAdapter);

    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_card = (TextView) findViewById(R.id.tv_card);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_all_pay = (TextView) findViewById(R.id.tv_all_pay);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_type= (TextView) findViewById(R.id.tv_type);
        tv_number= (TextView) findViewById(R.id.tv_number);
        lv_data = (ListView) findViewById(R.id.lv_data);
    }

    private void setData(){
        mList = getIntent().getParcelableArrayListExtra(SaleHistoryActivity.SALE_BILL_ITEM);
        EventCenter.getInstance().register(eventHandler);
    }




    private SimpleEventHandler eventHandler = new SimpleEventHandler(){
        public void onEvent(Result result){
            if(result.geteCode() == BillGoodsReturnModel.NOTIFY_BILL_ITEMLIST){
                showShortToast("退货成功");
                mAdapter.notifyDataSetChanged();

            }
        }

        public void onEvent(String  errorStr){
            showShortToast(errorStr);
        }
    };

}
