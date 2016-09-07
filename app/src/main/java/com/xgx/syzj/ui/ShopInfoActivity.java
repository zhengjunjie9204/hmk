package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.Store;
import com.xgx.syzj.bean.StoreInfo;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.StorePopupWindowUtil;

import org.json.JSONException;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 店铺基本信息
 *
 * @author zajo
 * @created 2015年08月12日 16:07
 */
public class ShopInfoActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv_stroename;
    private TextView tv_nickname;
    private TextView tv_phone;
    private TextView tv_name;
    private List<Store> storeList;
    private Button btn_store;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_info);
        setTitleText(getString(R.string.shop_info_title));
         user = CacheUtil.getmInstance().getUser();
        if(user.getRoles()==1){
            setSubmit("门店");
        }
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        new Thread(){
            @Override
            public void run() {
                super.run();
                Api.storeInfo((long)user.getStoreId(),listener);
            }
        }.start();

        if (user == null){
            defaultFinish();
            return;
        }
        initView();

        findViewById(R.id.ll_user).setOnClickListener(this);
    }

    private void initView() {
        tv_stroename = (TextView)findViewById(R.id.tv_stroename_name);
        tv_nickname = (TextView)findViewById(R.id.tv_nickname);
        tv_phone = (TextView)findViewById(R.id.tv_phone);
        tv_name = (TextView)findViewById(R.id.tv_name);
        btn_store = (Button) findViewById(R.id.btn_submit);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,AccountStaffListActivity.class);
        if(user.getRoles()==1){
            intent.putExtra("storeId1",storeId);
        }else{
            intent.putExtra("storeId1",user.getStoreId());
        }

        startActivity(intent);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            JSONObject obj = JSON.parseObject(result.getResult());
            String info = obj.getString("info");
            StoreInfo storeInfo = FastJsonUtil.json2Bean(info, StoreInfo.class);
            tv_stroename.setText(storeInfo.getStoreName());
            tv_nickname.setText(storeInfo.getStorePosition());
            tv_phone.setText(storeInfo.getManagerPhone());
            tv_name.setText(storeInfo.getManagerName());
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

    @Override
    public void onSubmit(View view) {
        super.onSubmit(view);
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
                        org.json.JSONObject json = new org.json.JSONObject(result.getResult());
                        storeList = FastJsonUtil.json2List(json.getString("storeList"), Store.class);
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

    private int storeId;
    private StorePopupWindowUtil.IPopupWindowCallListener ipopCallListener = new StorePopupWindowUtil.IPopupWindowCallListener() {

        @Override
        public void onItemClick(int index,Store store)
        {
            setSubmit(store.getName());
            storeId = store.getId();
            Api.storeInfo((long)storeId,listener);
        }
    };
}
