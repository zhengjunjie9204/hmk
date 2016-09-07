package com.xgx.syzj.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import com.xgx.syzj.widget.CustomDatePickerDialog;
import com.xgx.syzj.widget.StorePopupWindowUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private Button btn_store,btn_stime,btn_etime;
    private LinearLayout ll_time;
    private Calendar startCalendar;
    private byte mFlag;
    private HorizontalBarChart mChart;
    private String[] mTimes = new String[]{"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00"
            , "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
    private int storeId;
    private List<Store> storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_sell);
        setTitleText("报表分析");
        if (CacheUtil.getmInstance().getUser().getRoles() == 1) {
            setSubmit("门店");
        }
        mChart = (HorizontalBarChart) findViewById(R.id.chart);
        tv_all_money = (TextView) findViewById(R.id.tv_all_money);
        tvAllCount = (TextView) findViewById(R.id.tv_all_money_count);
        tvProMoney = (TextView) findViewById(R.id.tv_project_money);
        tvGoodMoney = (TextView) findViewById(R.id.tv_goods_money);
        tvCardMoney = (TextView) findViewById(R.id.tv_card_money);
        tvCountMoney = (TextView) findViewById(R.id.tv_count_money);
        btn_store = (Button) findViewById(R.id.btn_submit);
        btn_stime = (Button) findViewById(R.id.btn_stime);
        btn_etime = (Button) findViewById(R.id.btn_etime);
        ll_time = (LinearLayout) findViewById(R.id.linear_time);
        startCalendar = Calendar.getInstance();
        initTabBar();
        initChart();
        storeId = CacheUtil.getmInstance().getUser().getStoreId();
        changeTabBar(atbA);
        selectBillpay();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        BusinessSaleAnalyModel.getSaleReport(storeId, startTime, currentTime);
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

    private void selectBillpay() {
        curDate = new Date(System.currentTimeMillis());
        currentTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, 0);
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, -1);
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, selectData);
    }

    private StorePopupWindowUtil.IPopupWindowCallListener ipopCallListener = new StorePopupWindowUtil.IPopupWindowCallListener() {

        @Override
        public void onItemClick(int index, Store store) {
            btn_store.setText(store.getName());
            storeId = store.getId();
            BusinessSaleAnalyModel.getMoneyReport(storeId, startTime, currentTime);
        }
    };

    public void onTime(View view) {
        switch (view.getId()) {
            case R.id.btn_stime:
                setTime(btn_stime);
                break;
            case R.id.btn_etime:
                setTime(btn_etime);
                break;
        }
    }

    private void setTime(final Button btn) {
        CustomDatePickerDialog datePickerDialog = new CustomDatePickerDialog(AnalysisSellActivity.this, 0, new CustomDatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                  int startDayOfMonth) {
                String day, month;
                if (startDayOfMonth < 10) {
                    day = "0" + startDayOfMonth;
                } else {
                    day = "" + startDayOfMonth;
                }
                if (startMonthOfYear+1 < 10) {
                    month = "0" + (startMonthOfYear + 1);
                } else {
                    month = "" + (startMonthOfYear + 1);
                }
                btn.setText(startYear + "-" + month + "-" + day);
            }
        }, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH), true);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atb_a:
                changeTabBar(atbA);
                ll_time.setVisibility(View.GONE);
                selectData = -1;
                selectBillpay();
                showLoadingDialog(R.string.loading_date);
                BusinessSaleAnalyModel.getSaleReport(storeId, startTime, currentTime);
                break;
            case R.id.atb_b:
                changeTabBar(atbB);
                ll_time.setVisibility(View.GONE);
                selectData = -7;
                selectBillpay();
                showLoadingDialog(R.string.loading_date);
                BusinessSaleAnalyModel.getSaleReportByDay(storeId, startTime, currentTime);
                break;
            case R.id.atb_c:
                changeTabBar(atbC);
                ll_time.setVisibility(View.GONE);
                selectData = -15;
                selectBillpay();
                showLoadingDialog(R.string.loading_date);
                BusinessSaleAnalyModel.getSaleReportByDay(storeId, startTime, currentTime);
                break;
            case R.id.atb_d:
                changeTabBar(atbD);
                ll_time.setVisibility(View.GONE);
                selectData = -30;
                selectBillpay();
                showLoadingDialog(R.string.loading_date);
                BusinessSaleAnalyModel.getSaleReportByDay(storeId, startTime, currentTime);
                break;
            case R.id.atb_e:
                changeTabBar(atbE);
                ll_time.setVisibility(View.VISIBLE);
                selectData = -265;
                selectBillpay();
                showLoadingDialog(R.string.loading_date);
                BusinessSaleAnalyModel.getSaleReportByDay(storeId, startTime, currentTime);
                break;
        }

    }

    @Override
    protected void submit() {
        if (null == storeList || storeList.size() == 0) {
            getAllStore();
        } else {
            new StorePopupWindowUtil(AnalysisSellActivity.this, ipopCallListener).showActionWindow(btn_store, storeList);
        }
    }

    public void onSearch(View view) {
        String sTime = btn_stime.getText().toString();
        String eTime = btn_etime.getText().toString();
        if (!TextUtils.isEmpty(sTime) && !TextUtils.isEmpty(eTime)) {
            dialog.show();
            BusinessSaleAnalyModel.getSaleReport(storeId, sTime, eTime);
        }else{
            showShortToast("请选择时间");
        }
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            try {
                if (result.getStatus() == 200&&result.geteCode()==0x11) {
                    JSONObject json = new JSONObject(result.getResult());
                    tvAllCount.setText(json.optInt("count", 0) + "笔");
                    tv_all_money.setText("￥" + json.optDouble("totalSale", 0.00));
                    tvProMoney.setText("￥" + json.optDouble("productSale", 0.00));
                    tvGoodMoney.setText("￥" + json.optDouble("itemSale", 0.00));
                    tvCardMoney.setText("￥" + json.optDouble("storeMoneySale", 0.00));
                    tvCountMoney.setText("￥" + json.optDouble("storeItemSale", 0.00));
                    setData(json);
                }
                if (result.getStatus() == 200&&result.geteCode()==0x12) {
                    JSONObject json = new JSONObject(result.getResult());
                    tvAllCount.setText(json.optInt("count", 0) + "笔");
                    tv_all_money.setText("￥" + json.optDouble("totalSale", 0.00));
                    tvProMoney.setText("￥" + json.optDouble("productSale", 0.00));
                    tvGoodMoney.setText("￥" + json.optDouble("itemSale", 0.00));
                    tvCardMoney.setText("￥" + json.optDouble("storeMoneySale", 0.00));
                    tvCountMoney.setText("￥" + json.optDouble("storeItemSale", 0.00));
                    setDayData(json);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            hideLoadingDialog();
        }

        public void onEvent(String error) {
            hideLoadingDialog();
        }
    };

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

    private void setData(JSONObject json) {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        try {
            JSONArray hourSale = json.getJSONArray("hourSale");
            if(hourSale!=null) {
                for (int i = 0; i < 24; i++) {
                    xVals.add(mTimes[i % 24]);
//                    yVals1.add(new BarEntry((float) hourSale.get(i), i));
                }
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setDayData(JSONObject json) {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        try {
            JSONArray days = json.getJSONArray("daySale");
        for (int i = 0; i < days.length(); i++) {
            xVals.add("第"+i+"天");
            String day = (String) days.get(i);
            double d = Double.parseDouble(day);
            yVals1.add(new BarEntry((float)d , i));
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAllStore() {
        Api.getAllStore(new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                if (result.getStatus() == 200) {
                    try {
                        JSONObject json = new JSONObject(result.getResult());
                        storeList = FastJsonUtil.json2List(json.getString("storeList"), Store.class);
                        new StorePopupWindowUtil(AnalysisSellActivity.this, ipopCallListener).showActionWindow(btn_store, storeList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String errorCode, String message) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        defaultFinish();
    }
}
