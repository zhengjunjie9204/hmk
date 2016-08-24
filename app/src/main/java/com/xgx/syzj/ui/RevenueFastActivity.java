package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.RevenueFastAdapter;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.CountItemsBean;
import com.xgx.syzj.bean.OrderList;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.OrderDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 接车开单
 */
public class RevenueFastActivity extends BaseActivity {
    private Button btn_car_title;
    private PercentRelativeLayout key_car, key_number;
    private EditText et_car_number;
    private PercentRelativeLayout english_car;
    private GridView lv_data;
    private RevenueFastAdapter mAdapter;
    private List<OrderList> mDataList;
    private OrderDataModel mDataModel;
    //未完成的位置
    private int selectPosition;
    private String carNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_fast);
        Utils.hideSoftInput(this);
        initView();
        initListener();
        mDataList = new ArrayList<>();
        mAdapter = new RevenueFastAdapter(this, mDataList);
        lv_data.setAdapter(mAdapter);
        EventCenter.bindContainerAndHandler(this, eventHandler);
        mDataModel = new OrderDataModel(Constants.LOAD_COUNT);
        mDataModel.queryFirstPage();
    }

    private void initListener()
    {
        et_car_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus) {
                    et_car_number.clearFocus();
                    Utils.hideSoftInput(RevenueFastActivity.this);
                    key_number.setVisibility(View.VISIBLE);
                    key_car.setVisibility(View.GONE);
                    english_car.setVisibility(View.VISIBLE);
                } else {
                    key_number.setVisibility(View.GONE);
                    key_car.setVisibility(View.GONE);
                    Utils.hideSoftInput(RevenueFastActivity.this);
                }
            }
        });
        et_car_number.clearFocus();
        lv_data.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i)
            {
                key_car.setVisibility(View.GONE);
                key_number.setVisibility(View.GONE);
                english_car.setVisibility(View.GONE);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2)
            {

            }
        });
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                if (mDataList.get(position).getPayStatus() == 0) {//未支付
                    CustomAlertDialog.showRemindDialog(RevenueFastActivity.this, "温馨提示", "是否完成订单", new CustomAlertDialog.IAlertDialogListener() {
                        @Override
                        public void onSure(Object obj)
                        {
                            selectPosition = position;
                            OrderDataModel.setOrderDone(mDataList.get(position).getId());
                        }
                    });
                } else {//已完成
                    final Bundle bundle = new Bundle();
                    bundle.putString("finish","已完成");
                    bundle.putSerializable("order", mDataList.get(position));
                    gotoActivity(RevenuseSellFinishActivity.class, bundle);
//                    carNumber = mDataList.get(position).getCarNumber();
//                    getCarNumber();
                }
            }
        });
    }

    private void initView()
    {
        setTitleText(getString(R.string.main_add_money));
        setSubmit(getString(R.string.app_button_sure));
        btn_car_title = (Button) findViewById(R.id.btn_car_title);
        et_car_number = (EditText) findViewById(R.id.et_car_number);
        key_car = (PercentRelativeLayout) findViewById(R.id.key_car);
        key_number = (PercentRelativeLayout) findViewById(R.id.key_number);
        english_car = (PercentRelativeLayout) findViewById(R.id.english_car);
        lv_data = (GridView) findViewById(R.id.lv_data);
    }


    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<OrderList> list)
        {
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        public void onEvent(Result result)
        {
            if (result.getStatus() == 200) {
                mDataList.get(selectPosition).setPayStatus(3);
                mAdapter.notifyDataSetChanged();
            }
        }

        public void onEvent(String error)
        {
//            showShortToast(error);
//            loadMoreListViewContainer.loadMoreError(0, error);
        }
    };

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            et_car_number.clearFocus();
        }
        return super.dispatchKeyShortcutEvent(event);
    }

    public void onCarType(View view)
    {
        Utils.hideSoftInput(this);
        key_number.setVisibility(View.GONE);
        english_car.setVisibility(View.GONE);
        et_car_number.clearFocus();
        key_car.setVisibility(View.VISIBLE);
        key_car.setFocusable(true);
//        setAnimator(Techniques.SlideInUp,key_car);
    }

    //确定按钮
    @Override
    public void onSubmit(View view)
    {
        super.onSubmit(view);
        String number = et_car_number.getText().toString();
        if (TextUtils.isEmpty(number) || number.length() < 6) {
            showShortToast("请输入正确的车牌号");
            return;
        }
        carNumber = btn_car_title.getText().toString() + number;
        getCarNumber();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            mDataList.clear();
            mAdapter.notifyDataSetChanged();
            mDataModel.queryFirstPage();
        }
    }

    /**
     * @param isMember 是否是会员
     * */
    private void toSellActivity(int memberId,String distance,List<CountItemsBean> countItemsList,boolean isMember)
    {
        Intent intent = new Intent(this,RevenuseSellFinishActivity.class);
        intent.putExtra("carNumber",carNumber);
        intent.putExtra("memberId",memberId);
        intent.putExtra("isMember",isMember);
        intent.putExtra("distance",distance);
        if(null != countItemsList){
            intent.putExtra("countItemsList", (Serializable) countItemsList);
        }
        startActivityForResult(intent,0x101);
    }

    private void getCarNumber()
    {
        Api.getCarNumber(carNumber, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                try {
                    if (result.getStatus() == 200) {
                        JSONObject json = new JSONObject(result.getResult());
                        int memberId = json.optInt("memberId", 0);
                        String distance = json.optString("distance", "0");
                        List<CountItemsBean> countItemsList = FastJsonUtil.json2List(json.getString("items"), CountItemsBean.class);
                        toSellActivity(memberId, distance,countItemsList,true);
                    } else if (result.getStatus() == 300) {
                        CustomAlertDialog.showRemindDialog(RevenueFastActivity.this, "温馨提示", "该车辆非会员，是否进行散客开单？", new CustomAlertDialog.IAlertDialogListener() {
                            @Override
                            public void onSure(Object obj)
                            {
                                addOtherMember();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String message)
            {

            }
        });
    }

    private void addOtherMember()
    {
        Api.addOtherMember(carNumber, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result)
            {
                try {
                    if (result.getStatus() == 200) {
                        JSONObject json = new JSONObject(result.getResult());
                        int memberId = json.optInt("memberId", 0);
                        toSellActivity(memberId,"0",null,false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String message)
            {

            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        return super.dispatchKeyEvent(event);
    }

    public void onCalculate(View view)
    {
        String tag = (String) view.getTag();
        String text = et_car_number.getText().toString();
        switch (tag) {
            case "1":
                btn_car_title.setText("京");
                break;
            case "2":
                btn_car_title.setText("津");
                break;
            case "3":
                btn_car_title.setText("冀");
                break;
            case "4":
                btn_car_title.setText("晋");
                break;
            case "5":
                btn_car_title.setText("蒙");
                break;
            case "6":
                btn_car_title.setText("辽");
                break;
            case "7":
                btn_car_title.setText("吉");
                break;
            case "8":
                btn_car_title.setText("黑");
                break;
            case "9":
                btn_car_title.setText("沪");
                break;
            case "10":
                btn_car_title.setText("苏");
                break;
            case "11":
                btn_car_title.setText("浙");
                break;
            case "12":
                btn_car_title.setText("皖");
                break;
            case "13":
                btn_car_title.setText("闽");
                break;
            case "14":
                btn_car_title.setText("贛");
                break;
            case "15":
                btn_car_title.setText("鲁");
                break;
            case "16":
                btn_car_title.setText("豫");
                break;
            case "17":
                btn_car_title.setText("鄂");
                break;
            case "18":
                btn_car_title.setText("湘");
                break;
            case "19":
                btn_car_title.setText("粤");
                break;
            case "20":
                btn_car_title.setText("桂");
                break;
            case "21":
                btn_car_title.setText("琼");
                break;
            case "22":
                btn_car_title.setText("渝");
                break;
            case "23":
                btn_car_title.setText("川");
                break;
            case "24":
                btn_car_title.setText("贵");
                break;
            case "25":
                btn_car_title.setText("云");
                break;
            case "26":
                btn_car_title.setText("藏");
                break;
            case "27":
                btn_car_title.setText("陕");
                break;
            case "28":
                btn_car_title.setText("甘");
                break;
            case "29":
                btn_car_title.setText("青");
                break;
            case "30":
                btn_car_title.setText("宁");
                break;
            case "31":
                btn_car_title.setText("新");
                break;
            case "32":
                key_car.setVisibility(View.GONE);
                break;
            case "one":
                if (text.length() < 6) {
                    text = text + "1";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "two":
                if (text.length() < 6) {
                    text = text + "2";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "three":
                if (text.length() < 6) {
                    text = text + "3";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "four":
                if (text.length() < 6) {
                    text = text + "4";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "five":
                if (text.length() < 6) {
                    text = text + "5";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "six":
                if (text.length() < 6) {
                    text = text + "6";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "seven":
                if (text.length() < 6) {
                    text = text + "7";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "eigth":
                if (text.length() < 6) {
                    text = text + "8";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "nine":
                if (text.length() < 6) {
                    text = text + "9";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "zero":
                if (text.length() < 6) {
                    text = text + "0";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "A":
                if (text.length() < 6) {
                    text = text + "A";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "B":
                if (text.length() < 6) {
                    text = text + "B";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "C":
                if (text.length() < 6) {
                    text = text + "C";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "D":
                if (text.length() < 6) {
                    text = text + "D";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "E":
                if (text.length() < 6) {
                    text = text + "E";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "F":
                if (text.length() < 6) {
                    text = text + "F";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "G":
                if (text.length() < 6) {
                    text = text + "G";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "H":
                if (text.length() < 6) {
                    text = text + "H";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "I":
                if (text.length() < 6) {
                    text = text + "I";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "J":
                if (text.length() < 6) {
                    text = text + "J";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "K":
                if (text.length() < 6) {
                    text = text + "K";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "L":
                if (text.length() < 6) {
                    text = text + "L";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "M":
                if (text.length() < 6) {
                    text = text + "M";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "N":
                if (text.length() < 6) {
                    text = text + "N";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "O":
                if (text.length() < 6) {
                    text = text + "O";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "P":
                if (text.length() < 6) {
                    text = text + "P";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "Q":
                if (text.length() < 6) {
                    text = text + "Q";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;

            case "R":
                if (text.length() < 6) {
                    text = text + "R";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;

            case "S":
                if (text.length() < 6) {
                    text = text + "S";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "T":
                if (text.length() < 6) {
                    text = text + "T";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "U":
                if (text.length() < 6) {
                    text = text + "U";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "V":
                if (text.length() < 6) {
                    text = text + "V";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "W":
                if (text.length() < 6) {
                    text = text + "W";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "X":
                if (text.length() < 6) {
                    text = text + "X";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "Y":
                if (text.length() < 6) {
                    text = text + "Y";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "Z":
                if (text.length() < 6) {
                    text = text + "Z";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "delete": {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            }
        }
    }


}
