package com.xgx.syzj.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.OrderList;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.datamodel.SaleListRecordModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;

import java.util.ArrayList;
import java.util.List;

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
    private SaleListRecordModel mDataModel;
    private ArrayList<OrderList> data=new ArrayList<>();
    private String min;
    private String max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_history_filter);
        initView();
        initData();
        user = CacheUtil.getmInstance().getUser();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        mDataModel = new SaleListRecordModel(Constants.LOAD_COUNT);
    }

    private void initData() {


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
        final String mintime = btn_mintime.getText().toString();
         min = mintime.replace("年", "-").replace("月", "-").replace("日", "");
        final String maxtime = btn_maxtime.getText().toString();
         max = maxtime.replace("年", "-").replace("月", "-").replace("日", "");
        minmoney = et_minmoney.getText().toString();
        maxmoney = et_maxmoney.getText().toString();
        mDataModel.payOrder("",minmoney,maxmoney,min,max);

    }


    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        year=y;
        month=m+1;
        day=d;
      if(minTime){
          btn_mintime.setText(year+"年"+month+"月"+day+"日");
          minTime=false;
      }
        if(maxTime){
          btn_maxtime.setText(year+"年"+month+"月"+day+"日");
          maxTime=false;
      }
    }
    private SimpleEventHandler eventHandler = new SimpleEventHandler() {
        public void onEvent(List list){
            data.addAll(list);
            hideLoadingDialog();
            Intent intent = new Intent();
            intent.putExtra("maxTime",max);
            intent.putExtra("minTime",min);
            intent.putExtra("minmoney",minmoney);
            intent.putExtra("maxmoney",maxmoney);
            SaleHistoryFragment.getData(max,min,minmoney,maxmoney);
            setResult(RESULT_OK, intent);
            defaultFinish();
        }
        public void onEvent(Result result) {
            hideLoadingDialog();
            String message = result.getMessage();
            showShortToast(message);


        }


    };


}

