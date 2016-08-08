package com.xgx.syzj.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.BusinessSaleAnalyModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.DateUtil;
import com.xgx.syzj.widget.AnalysisTabBar;
import com.xgx.syzj.widget.ConsumptionPopupWindowUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * 销售分析
 *
 * @author zajo
 * @created 2015年09月29日 15:26
 */
public class AnalysisSellActivity extends BaseActivity implements View.OnClickListener {

    private AnalysisTabBar atbA, atbB, atbC, atbD, atbE;
    private String currentTime, startTime;
    private Date curDate;
    private int selectData = -1;
    private TextView tv_all_money, tvAllCount, tvProMoney, tvGoodMoney, tvCardMoney, tvCountMoney;
    private Button btn_store;
    private byte mFlag;
    private HorizontalBarChart mChart;
    private String[] mTimes = new String[]{"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00"
            , "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_sell);
        setTitleText("报表分析");
        setSubmit("门店");
        mChart = (HorizontalBarChart) findViewById(R.id.chart);
        tv_all_money = (TextView) findViewById(R.id.tv_all_money);
        tvAllCount = (TextView) findViewById(R.id.tv_all_money_count);
        tvProMoney = (TextView) findViewById(R.id.tv_project_money);
        tvGoodMoney = (TextView) findViewById(R.id.tv_goods_money);
        tvCardMoney = (TextView) findViewById(R.id.tv_card_money);
        tvCountMoney = (TextView) findViewById(R.id.tv_count_money);
        btn_store = (Button) findViewById(R.id.btn_submit);
        initTabBar();
        initChart();
        changeTabBar(atbA);
        selectBillpay();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        BusinessSaleAnalyModel.getSaleReport(startTime, currentTime);
    }

    private void initTabBar()
    {
        atbA = (AnalysisTabBar) findViewById(R.id.atb_a);
        atbB = (AnalysisTabBar) findViewById(R.id.atb_b);
        atbC = (AnalysisTabBar) findViewById(R.id.atb_c);
        atbD = (AnalysisTabBar) findViewById(R.id.atb_d);
        atbE = (AnalysisTabBar) findViewById(R.id.atb_e);

        atbA.setOnClickListener(this);
        atbB.setOnClickListener(this);
        atbC.setOnClickListener(this);
        atbD.setOnClickListener(this);
        atbE.setOnClickListener(this);
    }

    private void initChart()
    {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
//        mChart.setMaxVisibleValueCount()60;
        mChart.setPinchZoom(true);
        mChart.setDrawGridBackground(false);
        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        mChart.getAxisLeft().setEnabled(false);
//        YAxis yl = mChart.getAxisLeft();
//        yl.setDrawAxisLine(true);
//        yl.setDrawGridLines(true);
//        yl.setGridLineWidth(0.3f);

//        YAxis yr = mChart.getAxisRight();
//        yr.setDrawAxisLine(true);
//        yr.setDrawGridLines(false);
//        yr.setInverted(true);

    }

    private void selectBillpay()
    {
        curDate = new Date(System.currentTimeMillis());
        currentTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, 0);
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, -1);
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, selectData);
    }

    private ConsumptionPopupWindowUtil.IPopupWindowCallListener ipopCallListener = new ConsumptionPopupWindowUtil.IPopupWindowCallListener() {

        @Override
        public void onItemClick(int index)
        {
            if (index == 1) {
                btn_store.setText("门店1");
                selectData = -30;
                selectBillpay();
            } else if (index == 2) {
                btn_store.setText("门店2");
                selectData = -90;
                selectBillpay();
            } else if (index == 3) {
                btn_store.setText("门店3");
                selectData = -365;
                selectBillpay();
            }
        }
    };

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
            case R.id.atb_c:
                changeTabBar(atbC);
                selectData = -15;
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
        BusinessSaleAnalyModel.getSaleReport(startTime, currentTime);
    }

    @Override
    protected void submit()
    {
        new ConsumptionPopupWindowUtil<Object>(this, ipopCallListener)
                .showActionWindow(btn_store);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result)
        {
            try {
                if (result.getStatus() == 200) {
                    JSONObject json = new JSONObject(result.getResult());
                    tvAllCount.setText(json.optInt("count", 0) + "笔");
                    tv_all_money.setText("￥" + json.optDouble("totalSale", 0.00));
                    tvProMoney.setText("￥" + json.optDouble("productSale", 0.00));
                    tvGoodMoney.setText("￥" + json.optDouble("itemSale", 0.00));
                    tvCardMoney.setText("￥" + json.optDouble("storeMoneySale", 0.00));
                    tvCountMoney.setText("￥" + json.optDouble("storeItemSale", 0.00));
                    setData(json);
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

    private void changeTabBar(AnalysisTabBar bar)
    {
        bar.setStateColor(R.color.top_bar_color);
        if (bar != atbA)
            atbA.setStateColor(R.color.title_6_color);
        if (bar != atbB)
            atbB.setStateColor(R.color.title_6_color);
        if (bar != atbC)
            atbC.setStateColor(R.color.title_6_color);
        if (bar != atbD)
            atbD.setStateColor(R.color.title_6_color);
        if (bar != atbE)
            atbE.setStateColor(R.color.title_6_color);
    }

    private void setData(JSONObject json)
    {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            xVals.add(mTimes[i % 24]);
            yVals1.add(new BarEntry((float) json.optDouble(i + "hour", 0.00), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet 1");
        set1.setColor(Color.rgb(141, 209, 255));

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.rgb(230, 58, 106));
//        data.setValueTypeface(tf);

        mChart.setData(data);

        mChart.animateY(1500);
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        defaultFinish();
    }
}
