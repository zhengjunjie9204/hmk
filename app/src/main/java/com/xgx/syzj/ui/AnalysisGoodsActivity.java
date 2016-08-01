package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.GoodsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.widget.TextItemView;

import java.util.List;

/**
 * 商品分析
 *
 * @author zajo
 * @created 2015年09月29日 14:38
 */
public class AnalysisGoodsActivity extends BaseActivity {

    private TextView tv_big,tv_small;
    private TextItemView tv_kucun,tv_money;
    private SpannableString mspBig = null, mspSmall = null;
    private String tv_big_str,tv_small_str;
    private int tv_kucun_num,tv_money_num;
    private GoodsDataModel mDataModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_goods);

        setTitleText("商品分析");
        tv_big = (TextView) findViewById(R.id.tv_goods_type_big);
        tv_small = (TextView) findViewById(R.id.tv_goods_type_small);
        tv_kucun = (TextItemView)findViewById(R.id.tv_kucun);
        tv_money = (TextItemView)findViewById(R.id.tv_money);
        setData();
    }

    private void initView(){
        mspBig = new SpannableString("商品大类"+tv_big_str+"种");
        mspBig.setSpan(new AbsoluteSizeSpan(22, true), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mspBig.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_big.setText(mspBig);
        tv_big.setMovementMethod(LinkMovementMethod.getInstance());

        mspSmall = new SpannableString("商品小类"+tv_small_str+"种");
        mspSmall.setSpan(new AbsoluteSizeSpan(22, true), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mspSmall.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_small.setText(mspSmall);
        tv_small.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private  void setData(){
        EventCenter.bindContainerAndHandler(this, eventHandler);
        mDataModel = new GoodsDataModel(Constants.LOAD_COUNT);
        mDataModel.queryNextPage();
        mDataModel.getProductsCount("");
    }


    private SimpleEventHandler eventHandler = new SimpleEventHandler(){
        public void onEvent(Result result){
            if(result.geteCode() == GoodsDataModel.GET_COUNT_SUCCESS) {
                JSONObject obj = JSON.parseObject(result.getResult());
                tv_big_str = tv_small_str= obj.getIntValue("count")+"";
                initView();
            }

        }

        public void onEvent(List<Goods> list) {
            for( Goods g:list){
                tv_kucun_num += g.getQuantity();
                tv_money_num +=g.getInputPrice()*g.getQuantity();
            }

            tv_kucun.setDesc(tv_kucun_num+"件");
            tv_money.setDesc("￥"+tv_money_num);
        }
        public void onEvent(String  errerStr){
            showShortToast(errerStr);
        }
    };




/*    public void setTime(){

    }*/


}
