package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.datamodel.BusinessSaleAnalyModel;
import com.xgx.syzj.utils.DateUtil;
import com.xgx.syzj.widget.AnalysisTabBar;
import com.xgx.syzj.widget.ConsumptionPopupWindowUtil;

import java.util.Calendar;
import java.util.Date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analysis_money);
        setTitleText(getString(R.string.main_add_revenue));
        setSubmit("门店");
        initTabBar();
        changeTabBar(atbA);

        btn_store= (Button) findViewById(R.id.btn_submit);
        selectBillpay();
        BusinessSaleAnalyModel.getSalesAnalyTotal(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getSaleAnalyGrosssales(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getaleAnalyCount(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getaleAnalyTotals(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getaleAnalyprofit(startTime, currentTime, 1, 10);
    }


    private void initTabBar() {
        atbA = (AnalysisTabBar) findViewById(R.id.atb_a);
        atbB = (AnalysisTabBar) findViewById(R.id.atb_b);
        atbD = (AnalysisTabBar) findViewById(R.id.atb_d);
        atbE = (AnalysisTabBar) findViewById(R.id.atb_e);

        atbA.setOnClickListener(this);
        atbB.setOnClickListener(this);
        atbD.setOnClickListener(this);
        atbE.setOnClickListener(this);
    }

    private void selectBillpay() {
        Date curDate = new Date(System.currentTimeMillis());
        currentTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, 0);
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, -1);
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, selectData);
    }


    @Override
    public void onClick(View v) {
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
        BusinessSaleAnalyModel.getSalesAnalyTotal(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getSaleAnalyGrosssales(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getaleAnalyCount(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getaleAnalyTotals(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getaleAnalyprofit(startTime, currentTime, 1, 10);
    }

    private void changeTabBar(AnalysisTabBar bar) {
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

    private ConsumptionPopupWindowUtil.IPopupWindowCallListener ipopCallListener = new ConsumptionPopupWindowUtil.IPopupWindowCallListener() {

        @Override
        public void onItemClick(int index) {
            if (index == 1) {
                btn_store.setText("门店1");
                selectData = -30;
                selectBillpay();
            } else if(index == 2) {
                btn_store.setText("门店2");
                selectData = -90;
                selectBillpay();
            } else if(index == 3) {
                btn_store.setText("门店3");
                selectData = -365;
                selectBillpay();
            }
        }
    };

    @Override
    protected void submit() {
        new ConsumptionPopupWindowUtil<Object>(this, ipopCallListener)
                .showActionWindow(btn_store);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        defaultFinish();
    }
}
