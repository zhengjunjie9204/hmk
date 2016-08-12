package com.xgx.syzj.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.MemberConsumeAdapter;
import com.xgx.syzj.adapter.MemberItemAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.ConsumeInfo;
import com.xgx.syzj.bean.CountItemsBean;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.MemberDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.ListViewExtend;

import org.json.JSONObject;

import java.util.List;

/**
 * 会员资料
 */
public class RevenueMemberActivity extends BaseActivity {
    private int memberId;
    private TextView tv_name,tv_number,tv_type,tv_carNum,tv_money;
    private ListViewExtend item_listview;
    private ListViewExtend consumeinfo_listview;
    private MemberItemAdapter itemAdapter;
    private MemberConsumeAdapter consumeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_member);
        memberId = getIntent().getIntExtra("memberId",memberId);
        initView();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        MemberDataModel.getMemberBaseInfo(memberId);
    }

    private void initView()
    {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_carNum = (TextView) findViewById(R.id.tv_carNum);
        tv_money = (TextView) findViewById(R.id.tv_money);
        item_listview = (ListViewExtend) findViewById(R.id.item_listview);
        consumeinfo_listview = (ListViewExtend) findViewById(R.id.consumeinfo_listview);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result)
        {
            try {
                if (result.getStatus() == 200) {
                    JSONObject json = new JSONObject(result.getResult());
                    JSONObject driverInfo = json.getJSONObject("driverInfo");
                    if (null != driverInfo) {
                        tv_name.setText(driverInfo.optString("name", ""));
                        tv_number.setText(driverInfo.optString("phone", ""));
                        tv_type.setText(driverInfo.optString("carType", ""));
                        tv_carNum.setText(driverInfo.optString("carNumber", ""));
                        tv_money.setText("￥" + driverInfo.optDouble("storeMoney", 0.00));
                    }
                    List<CountItemsBean> items = FastJsonUtil.json2List(json.getString("itemInfo"), CountItemsBean.class);
                    if(null != items){
                        itemAdapter = new MemberItemAdapter(RevenueMemberActivity.this,items);
                        item_listview.setAdapter(itemAdapter);
                    }
                    List<ConsumeInfo> infos = FastJsonUtil.json2List(json.getString("consumeInfo"),ConsumeInfo.class);
                    if(null != infos){
                        consumeAdapter = new MemberConsumeAdapter(RevenueMemberActivity.this,infos);
                        consumeinfo_listview.setAdapter(consumeAdapter);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onEvent(String error)
        {
            showShortToast(error);
        }
    };

}