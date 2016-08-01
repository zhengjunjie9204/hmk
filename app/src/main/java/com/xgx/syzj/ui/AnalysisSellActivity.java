package com.xgx.syzj.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.JsonReader;
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
import com.xgx.syzj.bean.AnalySaleGrossProfit;
import com.xgx.syzj.bean.AnalySaleTotal;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.BusinessSaleAnalyModel;
import com.xgx.syzj.utils.DateUtil;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.AnalysisTabBar;
import com.xgx.syzj.widget.ConsumptionPopupWindowUtil;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private TextView tv_all_money;
    private Button btn_store;

    private byte mFlag;
    private HorizontalBarChart mChart;
    private String[] mTimes = new String[]{
            "0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analysis_sell);
        setTitleText("报表分析");
        setSubmit("门店");
        initTabBar();
        initChart();
        changeTabBar(atbA);

        tv_all_money= (TextView) findViewById(R.id.tv_all_money);
        btn_store= (Button) findViewById(R.id.btn_submit);

        EventBus.getDefault().register(this);
        selectBillpay();
        BusinessSaleAnalyModel.getSalesAnalyTotal(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getSaleAnalyGrosssales(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getaleAnalyCount(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getaleAnalyTotals(startTime, currentTime, 1, 10);
        BusinessSaleAnalyModel.getaleAnalyprofit(startTime, currentTime, 1, 10);
    }


    public void onEventMainThread(Result result) {

        JsonReader reader;
        if (result.geteCode() == AnalysisActivity.SALE_ANALY_TOTAL) {
            if (result.getResult() != null)
                try {
                    reader = new JsonReader(new StringReader(result.getResult()));
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String name = reader.nextName();
                        if (name.equals("totalValue")) {
                            tv_all_money.setText("￥" + reader.nextDouble());

                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } else if (result.geteCode() == AnalysisActivity.SALE_ANALY_TOP_TOTAL) {
            List<AnalySaleTotal> list = FastJsonUtil.json2List(result.getResult(), AnalySaleTotal.class);
            if (list.size() > 0){}
//                tv_sell_more_money.setDesc(list.get(0).getProductName());
        } else if (result.geteCode() == AnalysisActivity.SALE_ANALY_TOP_PROFIT) {
            List<AnalySaleGrossProfit> list = FastJsonUtil.json2List(result.getResult(), AnalySaleGrossProfit.class);
            if (list.size() > 0){}
//                tv_sell_more_profit.setDesc(list.get(0).getProductName());
        }
    }


    public void onEventMainThread(List<AnalySaleTotal> list) {
        if (list.size() > 0){}
//            tv_sell_more_count.setDesc(list.get(0).getProductName());
    }


    public void onEventMainThread(Map<String, String> map) {
//        tv_sell_count.setTitle("毛利润:￥" + map.get("totalValue"));
    }

    public void onEventMainThread(String message) {
        showShortToast(message);
    }

    private void initTabBar() {
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

    private void initChart() {
        mChart = (HorizontalBarChart) findViewById(R.id.chart);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
//        mChart.setMaxVisibleValueCount(60);
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

        setData(12, 100);
        mChart.animateY(1500);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }

    private void selectBillpay() {
        curDate = new Date(System.currentTimeMillis());
        currentTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, 0);
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, -1);
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, selectData);
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
        if (bar != atbC)
            atbC.setStateColor(R.color.title_6_color);
        if (bar != atbD)
            atbD.setStateColor(R.color.title_6_color);
        if (bar != atbE)
            atbE.setStateColor(R.color.title_6_color);
    }

    private void setData(int count, int rang) {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            xVals.add(mTimes[i % 12]);
            yVals1.add(new BarEntry((float) (Math.random() * rang), i));
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        defaultFinish();
    }
}
