package com.xgx.syzj.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.xgx.syzj.base.BaseFragment;
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
import com.xgx.syzj.widget.CustomProgressDialog;
import com.xgx.syzj.widget.StorePopupWindowUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 主页面Fragment
 *
 * @author zajo
 * @created 2015年08月11日 11:26
 */
public class MainBossContainerFragment extends BaseFragment implements View.OnClickListener,IMainBossActivityClick,DatePickerDialog.OnDateSetListener {

    private ImageView iv_menu;
    private CustomProgressDialog Dialog;
    private IBossMainMenuListItemClick IBossMainMenuListItemClick;
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
    private int storeId;
    private List<Store> storeList;
    private LinearLayout selling;
    private Button btn_submit;
    private Button btn_stime;
    private LinearLayout linear_time;
    private Button bt_onSearch;
    private Button btn_etime;
    private int year;
    private int month;
    private int day;
    private boolean maxTime=true;
    private boolean minTime=true;
    private View view;


    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof IBossMainMenuListItemClick)) {
            throw new ClassCastException();
        }
        IBossMainMenuListItemClick = (IBossMainMenuListItemClick) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//         year = bundle.getInt("year");
//         month = bundle.getInt("month");
//         day = bundle.getInt("day");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_main_boss_container, container, false);
        initView(view);
        mChart = (HorizontalBarChart) view.findViewById(R.id.chart);
        btn_submit =(Button) view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        tv_all_money = (TextView) view.findViewById(R.id.tv_all_money);
        tvAllCount = (TextView) view.findViewById(R.id.tv_all_money_count);
        tvProMoney = (TextView) view.findViewById(R.id.tv_project_money);
        tvGoodMoney = (TextView)view. findViewById(R.id.tv_goods_money);
        tvCardMoney = (TextView)view. findViewById(R.id.tv_card_money);
        tvCountMoney = (TextView) view.findViewById(R.id.tv_count_money);
        linear_time = (LinearLayout)view.findViewById(R.id.linear_time);
        selling =(LinearLayout)view.findViewById(R.id.selling);
        selling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AnalysismoneyActivity.class);
                startActivity(intent);
            }
        });
        btn_store = (Button) view.findViewById(R.id.btn_submit);
        initTabBar(view);
        initChart();
        storeId = CacheUtil.getmInstance().getUser().getStoreId();
        changeTabBar(atbA);
        selectBillpay();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        BusinessSaleAnalyModel.getSaleReport(storeId,startTime, currentTime);
        return view;
    }
    private void initTabBar(View view)
    {
        atbA = (AnalysisTabBar) view.findViewById(R.id.atb_a);
        atbB = (AnalysisTabBar) view.findViewById(R.id.atb_b);
        atbC = (AnalysisTabBar) view.findViewById(R.id.atb_c);
        atbD = (AnalysisTabBar) view.findViewById(R.id.atb_d);
        atbE = (AnalysisTabBar) view.findViewById(R.id.atb_e);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atb_a:
                changeTabBar(atbA);
                selectData = -1;
                linear_time.setVisibility(View.GONE);
                selectBillpay();
                BusinessSaleAnalyModel.getSaleReport(storeId,startTime, currentTime);
                break;
            case R.id.atb_b:

                changeTabBar(atbB);
                selectData = -7;
                linear_time.setVisibility(View.GONE);
                selectBillpay();
                BusinessSaleAnalyModel.getSaleReport(storeId,startTime, currentTime);
                break;
            case R.id.atb_c:
                changeTabBar(atbC);
                selectData = -15;
                linear_time.setVisibility(View.GONE);
                selectBillpay();
                BusinessSaleAnalyModel.getSaleReport(storeId,startTime, currentTime);
                break;
            case R.id.atb_d:
                changeTabBar(atbD);
                selectData = -30;
                linear_time.setVisibility(View.GONE);
                selectBillpay();
                BusinessSaleAnalyModel.getSaleReport(storeId,startTime, currentTime);
                break;
            case R.id.atb_e:
                changeTabBar(atbE);
                linear_time.setVisibility(View.VISIBLE);
                selectData = -265;
                selectBillpay();
                BusinessSaleAnalyModel.getSaleReport(storeId,startTime, currentTime);
                break;
            case R.id.btn_stime:
                setTime(btn_stime);
                break;
            case R.id.btn_etime:
                setTime(btn_etime);
                break;
        }
    }


    protected void submit() {
        if(null == storeList || storeList.size()==0){
            getAllStore();
        }else{
            new StorePopupWindowUtil(getActivity(), ipopCallListener).showActionWindow(btn_store, storeList);
        }
    }
    private void getAllStore(){
        Api.getAllStore(new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                if (result.getStatus() == 200) {
                    try {
                        JSONObject json = new JSONObject(result.getResult());
                        Store store = new Store();
                        store.setName("全部门店");
                        storeList = FastJsonUtil.json2List(json.getString("storeList"), Store.class);
                        storeList.add(store);
                        new StorePopupWindowUtil(getActivity(), ipopCallListener).showActionWindow(btn_store, storeList);
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


    private StorePopupWindowUtil.IPopupWindowCallListener ipopCallListener = new StorePopupWindowUtil.IPopupWindowCallListener() {

        @Override
        public void onItemClick(int index,Store store)
        {
            btn_store.setText(store.getName());
            storeId = store.getId();
            BusinessSaleAnalyModel.getMoneyReport(storeId, startTime, currentTime);
        }
    };

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

//



    private void initView(View view) {
        iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
        btn_stime =(Button)view.findViewById(R.id.btn_stime);
        btn_etime =(Button)view.findViewById(R.id.btn_etime);
        btn_stime.setOnClickListener(this);
        btn_etime.setOnClickListener(this);


        bt_onSearch =(Button) view.findViewById(R.id.bt_onSearch);
        bt_onSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusinessSaleAnalyModel.getSaleReport(storeId,startTime, currentTime);

            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (IBossMainMenuListItemClick != null)
                    IBossMainMenuListItemClick.onItemClick(100);

            }
        });
        onDataChange();
//        v1 = view.findViewById(R.id.sv1);
//        v2 = view.findViewById(R.id.sv2);
    }
    public ActionBar getActionBar() {
        throw new RuntimeException("Stub!");
    }
    public void setTitleText(String title) {
        ((TextView) (getActionBar().getCustomView().findViewById(R.id.tv_title))).setText(title);
    }

    public void setSubmit(String text) {
        Button btn = (Button) (getActionBar().getCustomView().findViewById(R.id.btn_submit));
        btn.setVisibility(View.VISIBLE);
        btn.setText(text);
    }


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
        Calendar startCalendar=Calendar.getInstance();
        CustomDatePickerDialog datePickerDialog = new CustomDatePickerDialog(getActivity(), 0, new CustomDatePickerDialog.OnDateSetListener() {
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
    public void onDataChange() {

    }

    @Override
    public void onLocation(String msg) {

    }

    @Override
    public void onChangeView() {

    }


    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        year=y;
        month=m+1;
        day=d;
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        MainBossContainerFragment mf = new MainBossContainerFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("year",y);
//        bundle.putInt("month",m);
//        bundle.putInt("day",d);
//        mf.setArguments(bundle);
//        ft.replace(R.id.ll_bossfragemnt,mf);
//        ft.addToBackStack(null);
//        ft.commit();
    }
}


