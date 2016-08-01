package com.xgx.syzj.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.AnalyAllConsumpt;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.BusinessSaleAnalyModel;
import com.xgx.syzj.datamodel.SaleDataModel;
import com.xgx.syzj.utils.DateUtil;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.AnalysisTabBar;
import com.xgx.syzj.widget.TextItemView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * 会员分析
 *
 * @author zajo
 * @created 2015年09月30日 09:47
 */
public class AnalysisMemberActivity extends BaseActivity implements View.OnClickListener {

    private AnalysisTabBar atbA, atbB, atbC, atbD, atbE;
    private PieChart mChart;

    private String currentTime,startTime;
    private Date curDate;
    private int selectData =-1;

    private TextView tv_percent,tv_m_percent;
    private TextItemView tv_all_member,tv_all_other,tv_new_member,tv_add_money,tv_add_count;
    private double d_tv_percent,d_tv_m_percent,all_tv_m_percent;

    private SaleDataModel saleDataModel;


//    private BarChart barChartAge;
    private String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };




//    private String[] mBarXAge = new String[]{"19岁及以下","20-29岁","30-39岁","40-49岁","50岁及以上"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analysis_member);
        setTitleText("会员分析");
        initView();
        EventBus.getDefault().register(this);
        selectBillpay();
        BusinessSaleAnalyModel.getMemberAnalyTotal(startTime,currentTime,1,10);

        saleDataModel = new SaleDataModel(Constants.LOAD_COUNT,SaleHistoryActivity.SALE_MEMBER);
        saleDataModel.setTime(startTime,currentTime);
        saleDataModel.queryNextPage();

        initTabBar();
        initPieChart();
//        initBarChartAge();
        changeTabBar(atbA);



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

    private void initPieChart() {
        mChart = (PieChart) findViewById(R.id.pieChart);
        mChart.setUsePercentValues(true);
        mChart.setDescription("");

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        mChart.setCenterText("");

        setPieData(1, 100,1,1);

        mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    /*private void initBarChartAge(){
        barChartAge = (BarChart) findViewById(R.id.barChartAge);
//        barChartAge.setOnChartValueSelectedListener(this);
        barChartAge.setDescription("");

//        barChartAge.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        barChartAge.setPinchZoom(false);

        barChartAge.setDrawBarShadow(false);

        barChartAge.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        CustomMarkerView mv = new CustomMarkerView(this, R.layout.layout_marker_view);

        // define an offset to change the original position of the marker
        // (optional)
        // mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        // set the marker to the chart
        barChartAge.setMarkerView(mv);

        setBarAgeData(90);
        barChartAge.animateY(1500);

        Legend l = barChartAge.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setYOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xl = barChartAge.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);

        YAxis leftAxis = barChartAge.getAxisLeft();
        leftAxis.setValueFormatter(new PercentFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);

        barChartAge.getAxisRight().setEnabled(false);
    }*/

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
                selectData = -365;
                break;
        }

        selectBillpay();
        BusinessSaleAnalyModel.getMemberAnalyTotal(startTime,currentTime,1,10);

        saleDataModel.setTime(startTime,currentTime);
        saleDataModel.queryFirstPage();
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

    private void setPieData(int count, float range,int d_tv_percent ,int d_tv_m_percent) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
      /*  for (int i = 0; i < count + 1; i++) {
          // yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));

        }*/


        yVals1.add(new Entry((float) d_tv_percent, 0));
        yVals1.add(new Entry((float) d_tv_m_percent, 1));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
        colors.add(Color.rgb(231, 58, 106));
        colors.add(Color.rgb(56, 161, 233));

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    /*private void setBarAgeData(float range){
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < mBarXAge.length; i++) {
            xVals.add(mBarXAge[i]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        float mult = range;

        for (int i = 0; i < mBarXAge.length; i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals1.add(new BarEntry(val, i));
        }

        for (int i = 0; i < mBarXAge.length; i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals2.add(new BarEntry(val, i));
        }

        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, "男");
        // set1.setColors(ColorTemplate.createColors(getApplicationContext(),
        // ColorTemplate.FRESH_COLORS));
//        set1.setColor(Color.rgb(104, 241, 175));
        set1.setColor(Color.rgb(165, 189, 239));
        BarDataSet set2 = new BarDataSet(yVals2, "女");
//        set2.setColor(Color.rgb(164, 228, 251));
        set2.setColor(Color.rgb(142, 217, 203));

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
//        data.setValueFormatter(new LargeValueFormatter());

        // add space between the dataset groups in percent of bar-width
        data.setGroupSpace(80f);

        barChartAge.setResult(data);
        barChartAge.invalidate();
    }*/

    private void initView(){
        tv_m_percent = (TextView)findViewById(R.id.tv_m_percent);
        tv_percent = (TextView)findViewById(R.id.tv_percent);
        tv_all_member = (TextItemView)findViewById(R.id.tv_all_member);
        tv_all_other = (TextItemView)findViewById(R.id.tv_all_other);
        tv_new_member = (TextItemView)findViewById(R.id.tv_new_member);
        tv_add_money = (TextItemView)findViewById(R.id.tv_add_money);
        tv_add_count = (TextItemView)findViewById(R.id.tv_add_count);
    }

    private void selectBillpay(){
        curDate = new Date(System.currentTimeMillis());
        currentTime = DateUtil.getStringByOffset(curDate,DateUtil.dateFormatYMDHMS, Calendar.DATE,0);
        startTime = DateUtil.getStringByOffset(curDate,DateUtil.dateFormatYMDHMS, Calendar.DATE,-1);
        startTime = DateUtil.getStringByOffset(curDate,DateUtil.dateFormatYMDHMS, Calendar.DATE,selectData);
    }

    public void onEventMainThread(Result result){

        if(result.getResult() == null) return;
        if(result.geteCode() == AnalysisActivity.ASSOCIATOR_SURVEY){
            JSONObject obj = JSON.parseObject(result.getResult());
            if(obj.get("totalValue") == null){
                tv_add_money.setDesc("￥"+0+"");
            }else {
                tv_add_money.setDesc("￥"+obj.get("totalValue"));
            }
            tv_new_member.setDesc(obj.get("totalAssociator")+"人");
            tv_add_count.setDesc(obj.get("totalCount")+"次");

        }else if(result.geteCode() == AnalysisActivity.ASSOCIOR_SURVEY_CONSUME){
            AnalyAllConsumpt analyAllConsumpt = FastJsonUtil.json2Bean(result.getResult(),AnalyAllConsumpt.class);
            d_tv_percent = analyAllConsumpt.getOutsiderConsumption();
            d_tv_m_percent = analyAllConsumpt.getAssociatorConsumption();
            all_tv_m_percent =  analyAllConsumpt.getTotalConsumption();
            tv_all_member.setTitle("会员:￥"+ analyAllConsumpt.getAssociatorConsumption());
            tv_all_other.setTitle("零售:￥"+analyAllConsumpt.getOutsiderConsumption());
            if(all_tv_m_percent != 0){
                int sga0 = (int)(d_tv_percent*100/all_tv_m_percent);
                int sga1 = (int)(d_tv_m_percent*100/all_tv_m_percent);
                tv_percent.setText(sga0+""+"%");
                tv_m_percent.setText(sga1+""+"%");
            }else {
                tv_percent.setText(0+""+"%");
                tv_m_percent.setText(0+""+"%");
            }

            setPieData(1, 100,(int)d_tv_percent,(int)d_tv_m_percent);
        }else {
            showShortToast("数据在加载中...");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        defaultFinish();
    }
}