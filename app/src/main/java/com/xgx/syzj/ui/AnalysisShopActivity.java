package com.xgx.syzj.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.widget.TextItemView;


/**
 * 店铺体验
 *
 * @author zajo
 * @created 2015年09月29日 10:19
 */
public class AnalysisShopActivity extends BaseActivity implements View.OnClickListener {

//    private ProgressWheel progress;
    private TextView tv_score;
    private SpannableString msp = null;
    private TextItemView tv_kucun,tv_member,tv_pay,tv_conpon;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analysis_shop);
        setTitleText("店铺体验");
//        progress = (ProgressWheel) findViewById(R.id.progress);
//        progress.setOnClickListener(this);
        tv_kucun = (TextItemView) findViewById(R.id.tv_kucun);
        tv_member = (TextItemView) findViewById(R.id.tv_member);
        tv_pay = (TextItemView) findViewById(R.id.tv_pay);
        tv_conpon = (TextItemView) findViewById(R.id.tv_conpon);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_score.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_score:
                startCheck();
                break;
            /*case R.id.progress:
                startCheck();
                break;*/
        }
    }

    private void startCheck(){
//        tv_score.setVisibility(View.GONE);
        tv_kucun.setDesc("");
        tv_member.setDesc("");
        tv_pay.setDesc("");
        tv_conpon.setDesc("");
//        if (progress.isSpinning) {
//            progress.stopSpinning();
//        }
//        progress.resetCount();
//        progress.setText("体验中...");
//        progress.spin();
        tv_score.setText("体验中...");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                progress.setText("");
//                progress.stopSpinning();
                msp = new SpannableString("69分");
                msp.setSpan(new AbsoluteSizeSpan(30, true), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
                tv_score.setText(msp);
                tv_score.setMovementMethod(LinkMovementMethod.getInstance());
//                tv_score.setVisibility(View.VISIBLE);

                tv_kucun.setDesc("75分");
                tv_member.setDesc("50分");
                tv_pay.setDesc("95分");
                tv_conpon.setDesc("55分");
            }
        }, 3000);
    }
}
