package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 报表分析
 *
 * @author zajo
 * @created 2015年09月29日 14:33
 */
public class AnalysisActivity extends BaseActivity implements View.OnClickListener {

    public static final byte ASSOCIOR_SURVEY_CONSUME = 0x011;
    public static final byte ASSOCIATOR_SURVEY = 0x012;
    public static final byte GOODS_SURVEY = 0x013;
    public static final byte SALE_SURVEY = 0x014;
    public static final byte SALE_ANALY_COUNT = 0x015;
    public static final byte SALE_ANALY_TOTAL = 0x016;
    public static final byte SALE_ANALY_TOP_TOTAL = 0x017;
    public static final byte SALE_ANALY_TOP_PROFIT = 0x018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analysis);
        setTitleText("报表分析");
        findViewById(R.id.tv_goods).setOnClickListener(this);
        findViewById(R.id.tv_member).setOnClickListener(this);
        findViewById(R.id.tv_sell).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_goods:
                gotoActivity(AnalysisGoodsActivity.class);
                break;
            case R.id.tv_member:
                gotoActivity(AnalysisMemberActivity.class);
                break;
            case R.id.tv_sell:
                gotoActivity(AnalysisSellActivity.class);
                break;
        }
    }
}
