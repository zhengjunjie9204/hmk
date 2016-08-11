package com.xgx.syzj.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.bean.UserInfo;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.FastJsonUtil;

import de.greenrobot.event.EventBus;

public class SaleHistoryFilterActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {


    private EditText et_minmoney;
    private EditText et_maxmoney;
    private Button btn_mintime;
    private Button btn_maxtime;
    private int year;
    private int month;
    private int day;
    private boolean maxTime,minTime;
    private String businessname;
    private String product;
    private String back;
    private String minmoney;
    private String maxmoney;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_history_filter);
        initView();
        initData();
        user = CacheUtil.getmInstance().getUser();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
    }

    private void initData() {

        minmoney = et_minmoney.getText().toString();
        maxmoney = et_maxmoney.getText().toString();
    }

    private void initView() {
        et_minmoney = (EditText)findViewById(R.id.et_minmoney);
        et_maxmoney = (EditText)findViewById(R.id.et_maxmoney);
        btn_mintime =(Button) findViewById(R.id.btn_mintime);
        btn_maxtime =(Button) findViewById(R.id.btn_maxtime);
    }
    //选择时间
    public void onClick(View v){
        TimePackerFragment timePackerFragment = new TimePackerFragment();
        timePackerFragment.show(getFragmentManager(),"datePicker");

        minTime=true;

    }
    //选择时间
    public void onClick2(View v){
        TimePackerFragment timePackerFragment = new TimePackerFragment();
        timePackerFragment.show(getFragmentManager(),"datePicker");

        maxTime=true;
    }
    //确定按钮
    public void onAddSure(View v){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Api.filterOrder((long)user.getStoreId(),businessname,product,back,minmoney,maxmoney,listener);
            }
        }.start();

        if (user == null){
            defaultFinish();
            return;
        }
        gotoActivity(SaleHistoryActivity.class);
        finish();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        year=y;
        month=m+1;
        day=d;
      if(minTime){
          btn_mintime.setText(year+"年"+month+"月"+day+"日");
          minTime=false;
      }else if(maxTime){
          btn_maxtime.setText(year+"年"+month+"月"+day+"日");
          maxTime=false;
      }
    }
    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            String message = result.getMessage();
            showShortToast(message);


        }


    };
    private static BaseRequest.OnRequestListener listener = new BaseRequest.OnRequestListener() {

        @Override
        public void onSuccess(Result result) {
            EventCenter.getInstance().post(result);
        }

        @Override
        public void onError(String errorCode, String message) {
            EventCenter.getInstance().post(message);
        }
    };

}

