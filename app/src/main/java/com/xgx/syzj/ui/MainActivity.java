package com.xgx.syzj.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.xgx.syzj.R;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.app.lifecycle.IComponentContainer;
import com.xgx.syzj.app.lifecycle.LifeCycleComponent;
import com.xgx.syzj.app.lifecycle.LifeCycleComponentManager;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.UserDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.service.DownloadService;
import com.xgx.syzj.utils.AppUtil;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.widget.CustomAlertDialog;
import com.xgx.syzj.widget.CustomProgressDialog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity implements IMainMenuListItemClick, IComponentContainer {

    public static final int RESULT_SHORTCUT_MENU = 1000;
    private UserDataModel mDataModel;
    private SlidingPaneLayout mSlidingPanel;
    private CustomProgressDialog loadingDiaLog;
    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();
    private Handler hanler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CustomAlertDialog.showRemindDialog2(MainActivity.this, "温馨提示", "该版本已经最新", new CustomAlertDialog.IAlertDialogListener() {
                @Override
                public void onSure(Object obj) {
                    return;
                }
            });

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        AppManager.getAppManager().addActivity(this); //将activity推入管理栈

        setContentView(R.layout.activity_main);
        mDataModel = new UserDataModel();
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setParallaxDistance(200);
        mSlidingPanel.setSliderFadeColor(getResources().getColor(R.color.transparent));
        EventCenter.bindContainerAndHandler(this, eventHandler);
        //location();
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {
        public void onEvent(Result result) {
            loadingDiaLog.dismiss();
            if (UserDataModel.CHECK_VERSION_SUCCESS == result.geteCode()) {
                JSONObject object = JSON.parseObject(result.getData());
                String model = object.getString("model");
                String code = object.getString("versioncode");
                String information = object.getString("information");
                final String url = object.getString("apkurl");
                if ("hmk".equals(model) && Integer.parseInt(code) > AppUtil.getVerCode(MainActivity.this)) {
                    CustomAlertDialog.showUpdataDialog(MainActivity.this, information, new CustomAlertDialog.IAlertDialogListener() {
                        @Override
                        public void onSure(Object obj) {
                            Intent intent = new Intent(getApplicationContext(), DownloadService.class);
                            intent.putExtra("url", url);
                            startService(intent);
                        }
                    });
                } else {
                    hanler.sendMessage(new Message());
                }
            }
        }

        public void onEvent(String error) {
            loadingDiaLog.dismiss();
            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    public void gotoActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    protected void gotoActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void gotoActivityForResult(Class<? extends Activity> activity, int requestCode) {
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void gotoActivity(Class<? extends Activity> activity, String key, String value) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(key, value);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    //新增商品
    public void onAddGoods(View view) {
        if (CacheUtil.getmInstance().getUser().getRoles() == 1) {
            gotoActivity(GoodsAddActivity.class);
        } else {
            gotoActivity(GoodsSelectActivity.class);
        }
    }

    //新增会员
    public void onAddMember(View view) {
        gotoActivity(MemberAddActivity.class);
    }


    //资金流水
    public void onAddPay(View view) {
        int roles=CacheUtil.getmInstance().getUser().getRoles();
        if(roles==3){
            Toast.makeText(this, "权限不足", Toast.LENGTH_SHORT).show();
            return;
        }
        gotoActivity(AnalysismoneyActivity.class);
    }

    //商品管理
    public void onManGoods(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("type", "goods");
        gotoActivity(GoodsListActivity.class, bundle);
    }

    //会员管理
    public void onManMember(View view) {
        gotoActivity(MemberListActivity.class);
    }

    //单据列表
    public void onCheckSell(View view) {
        gotoActivity(HistoryActivity.class);
    }

    //报表分析
    public void onGoodsAnalysis(View view) {
        int roles=CacheUtil.getmInstance().getUser().getRoles();
        if(roles==3){
            Toast.makeText(this, "权限不足", Toast.LENGTH_SHORT).show();
            return;
        }
        gotoActivity(AnalysisSellActivity.class);
    }

    //我的微店
    public void onMyStore(View view) {

        CustomAlertDialog.showRemindDialog2(MainActivity.this, "温馨提示", "请联系服务商开通微信预约功能\n4008-328-728", new CustomAlertDialog.IAlertDialogListener() {
            @Override
            public void onSure(Object obj) {
                return;
            }
        });
//                gotoActivity(OrderFormActivity.class);


//        gotoActivity();
    }

    //会员体系
    public void onManSell(View view) {
        gotoActivity(CardListActivity.class);
    }


    //考勤管理
    public void onManAttendance(View view) {
        gotoActivity(AttendanceActivity.class);
    }

    //派发卡券
    public void onSendCard(View view) {
        gotoActivity(CardSendTypeActivity.class);
    }

    //接车开单
    public void onAddMoney(View view) {
        gotoActivity(RevenueFastActivity.class);
    }

    //微订单
    public void onSmallOrder(View view) {
        gotoActivity(OrderFormActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mSlidingPanel.isOpen()) {
                    mSlidingPanel.closePane();
                } else {
                    mSlidingPanel.openPane();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param position 0-4为功能列表占有 100顶部menu 101店铺信息
     */
    @Override
    public void onItemClick(int position) {
        if (position == 100)
            mSlidingPanel.openPane();
        else
            mSlidingPanel.closePane();
        switch (position) {
            case 0:
                gotoActivity(AccountUserInfoActivity.class);
                break;
            case 1:
                gotoActivity(ShopInfoActivity.class);
                break;
            case 2:
                gotoActivity(FeedBackActivity.class);
                break;
            case 3:
                loadingDiaLog = CustomProgressDialog.createDialog(this).setMessage("加载中...");
                loadingDiaLog.show();
                UserDataModel.checkVersion();
                break;
            case 4:
                onLogout();
                break;
            case 100:
                mSlidingPanel.openPane();
                break;
            default:
                mSlidingPanel.closePane();
                break;
        }
    }

    private void onLogout() {
        try {
            CacheUtil.getmInstance().setUser(null);
            SYZJApplication.getInstance().getSpUtil().delete(Constants.SharedPreferencesClass.SP_PSW);
            SYZJApplication.getInstance().getSpUtil().delete(Constants.SharedPreferencesClass.SP_ROLES);
            SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_TOKEN, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        CustomAlertDialog.showRemindDialog(MainActivity.this, "温馨提示", "是否退出登录", new CustomAlertDialog.IAlertDialogListener() {
            @Override
            public void onSure(Object obj) {
                gotoActivity(LoginActivity.class);
                finish();
            }
        });
    }

    private IMainActivityClick mainContainerFragmentListener;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof MainContainerFragment) {
            mainContainerFragmentListener = (IMainActivityClick) fragment;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == RESULT_SHORTCUT_MENU) {
            mainContainerFragmentListener.onDataChange();
        }
    }

    private int keyBackClickCount = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    Toast.makeText(this, getString(R.string.toast_press_again_exit), Toast.LENGTH_SHORT).show();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                    finish();
                    break;
                default:
                    break;
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {

        }
        return super.onKeyDown(keyCode, event);
    }

    private LocationClient mLocationClient;
    private double longtitude, latitude;
    private double mLng = 23.121559, mLat = 113.369254;
    private String address;

    private void location() {
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void addComponent(LifeCycleComponent component) {
        mComponentContainer.addComponent(component);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            mLocationClient.stop();
            longtitude = location.getLongitude();
            latitude = location.getLatitude();
            address = location.getAddrStr();
            List<Poi> list = location.getPoiList();// POI信息
            if (list != null && list.size() > 0) {
                Poi p = list.get(0);
                address = address + " " + p.getName();
            }
            mainContainerFragmentListener.onLocation(address);
        }
    }
}

//主Activity传递事件到Fragment
interface IMainActivityClick {
    void onDataChange();

    void onLocation(String msg);
//    void onChangeView();
}
