package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.Store;
import com.xgx.syzj.datamodel.BusinessSaleAnalyModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.DateUtil;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.AnalysisTabBar;
import com.xgx.syzj.widget.StorePopupWindowUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 资金流水
 *
 * @author zajo
 * @created 2015年09月29日 15:26
 */
public class AnalysismoneyActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_store;
    private AnalysisTabBar atbA, atbB, atbD, atbE;
    private String currentTime, startTime;
    private int selectData = -1;
    private TextView mTvAlipay, mTvRemain, mTvWechat, mTvCash, mTvCard;
    private int storeId;
    private List<Store> storeList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_money);
        initView();
        storeId = CacheUtil.getmInstance().getUser().getStoreId();
        selectBillpay();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        BusinessSaleAnalyModel.getMoneyReport(storeId,startTime, currentTime);
    }

    private void initView()
    {
        setTitleText(getString(R.string.main_add_revenue));
        if (CacheUtil.getmInstance().getUser().getRoles() == 1) {
            setSubmit("门店");
        }
        initTabBar();
        changeTabBar(atbA);
        btn_store = (Button) findViewById(R.id.btn_submit);
        mTvCash = (TextView) findViewById(R.id.money_tv_cash);
        mTvCard = (TextView) findViewById(R.id.money_tv_card);
        mTvWechat = (TextView) findViewById(R.id.money_tv_wechat);
        mTvAlipay = (TextView) findViewById(R.id.money_tv_alipay);
        mTvRemain = (TextView) findViewById(R.id.money_tv_remain);
    }

    private void initTabBar()
    {
        atbA = (AnalysisTabBar) findViewById(R.id.atb_a);
        atbB = (AnalysisTabBar) findViewById(R.id.atb_b);
        atbD = (AnalysisTabBar) findViewById(R.id.atb_d);
        atbE = (AnalysisTabBar) findViewById(R.id.atb_e);

        atbA.setOnClickListener(this);
        atbB.setOnClickListener(this);
        atbD.setOnClickListener(this);
        atbE.setOnClickListener(this);
    }

    private void selectBillpay()
    {
        Date curDate = new Date(System.currentTimeMillis());
        currentTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMD, Calendar.DATE, 0);
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMD, Calendar.DATE, selectData);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.atb_a:
                changeTabBar(atbA);
                selectData = -1;
                break;
            case R.id.atb_b:
                changeTabBar(atbB);
                selectData = -7;
                break;
            case R.id.atb_d:
                changeTabBar(atbD);
                selectData = -30;
                break;
            case R.id.atb_e:
                changeTabBar(atbE);
                selectData = -265;
                break;
        }
        selectBillpay();
        showLoadingDialog(R.string.loading_date);
        BusinessSaleAnalyModel.getMoneyReport(storeId,startTime, currentTime);
    }

    private void changeTabBar(AnalysisTabBar bar)
    {
        bar.setStateColor(R.color.top_bar_color);
        if (bar != atbA)
            atbA.setStateColor(R.color.title_6_color);
        if (bar != atbB)
            atbB.setStateColor(R.color.title_6_color);
        if (bar != atbD)
            atbD.setStateColor(R.color.title_6_color);
        if (bar != atbE)
            atbE.setStateColor(R.color.title_6_color);
    }

    private StorePopupWindowUtil.IPopupWindowCallListener ipopCallListener = new StorePopupWindowUtil.IPopupWindowCallListener() {

        @Override
        public void onItemClick(int index,Store store)
        {
            btn_store.setText(store.getName());
            storeId = store.getId();
            BusinessSaleAnalyModel.getMoneyReport(storeId, startTime, currentTime);
        }
    };

    @Override
    protected void submit()
    {
        if(null == storeList || storeList.size()==0){
            getAllStore();
        }else{
            new StorePopupWindowUtil(AnalysismoneyActivity.this, ipopCallListener).showActionWindow(btn_store, storeList);
        }
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result)
        {
            try {
                if (result.getStatus() == 200) {
                    JSONObject json = new JSONObject(result.getResult());
                    mTvCash.setText("￥"+json.optDouble("cash",0.00));
                    mTvCard.setText("￥"+json.optDouble("card",0.00));
                    mTvWechat.setText("￥"+json.optDouble("wechat",0.00));
                    mTvAlipay.setText("￥"+json.optDouble("alipay",0.00));
                    mTvRemain.setText("￥"+json.optDouble("remain",0.00));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            hideLoadingDialog();
        }

        public void onEvent(String error)
        {
            hideLoadingDialog();
        }
    };

    private void getAllStore(){
        Api.getAllStore(new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                if (result.getStatus() == 200) {
                    try {
                        JSONObject json = new JSONObject(result.getResult());
                        List<Store> storeList = FastJsonUtil.json2List(json.getString("storeList"), Store.class);
                        new StorePopupWindowUtil(AnalysismoneyActivity.this, ipopCallListener)
                                .showActionWindow(btn_store,storeList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String errorCode, String message)
            {

            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        defaultFinish();
    }
}
