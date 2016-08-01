package com.xgx.syzj.ui;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.RevenueFastAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 接车开单
 */
public class RevenueFastActivity extends BaseActivity {

    private Button btn_car_title;
    private PercentRelativeLayout key_car,key_number;
    private EditText et_car_number;
    private PercentRelativeLayout english_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_fast);
        Utils.hideSoftInput(this);
        setTitleText(getString(R.string.main_add_money));
        setSubmit(getString(R.string.app_button_sure));
        btn_car_title= (Button) findViewById(R.id.btn_car_title);
        et_car_number= (EditText) findViewById(R.id.et_car_number);
        key_car= (PercentRelativeLayout) findViewById(R.id.key_car);
        key_number= (PercentRelativeLayout) findViewById(R.id.key_number);
        english_car = (PercentRelativeLayout)findViewById(R.id.english_car);
        ListView lv_data = (ListView) findViewById(R.id.lv_data);
        List<String> nameList=new ArrayList<>();
        final List<String> mList=new ArrayList<>();
        for(int i=0;i<10;i++){
            nameList.add("Mr.廖"+i);
            mList.add("粤W1021"+i);
        }
        RevenueFastAdapter mAdapter=new RevenueFastAdapter(this,mList,nameList);
        lv_data.setAdapter(mAdapter);
        et_car_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    et_car_number.clearFocus();
                    Utils.hideSoftInput(RevenueFastActivity.this);
                    key_number.setVisibility(View.VISIBLE);
                    key_car.setVisibility(View.GONE);
                    english_car.setVisibility(View.VISIBLE);
                }else{
                    key_number.setVisibility(View.GONE);
                    key_car.setVisibility(View.GONE);
                    Utils.hideSoftInput(RevenueFastActivity.this);
                }
            }
        });
        et_car_number.clearFocus();
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=mList.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("carnumber",name);
                bundle.putBoolean("isPay",true);
                gotoActivity(RevenueSellActivity.class,bundle);
            }
        });
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
            et_car_number.clearFocus();
        }
        return super.dispatchKeyShortcutEvent(event);
    }

    public void onCarType(View view){
        Utils.hideSoftInput(this);
        key_number.setVisibility(View.GONE);
        et_car_number.clearFocus();
        key_car.setVisibility(View.VISIBLE);
        key_car.setFocusable(true);
        setAnimator(Techniques.SlideInUp,key_car);
    }
    //确定按钮
    @Override
    public void onSubmit(View view) {
        super.onSubmit(view);
        String name=btn_car_title.getText().toString()+et_car_number.getText().toString();
        Bundle bundle=new Bundle();
        bundle.putString("carnumber",name);
        bundle.putBoolean("isPay",false);
        gotoActivity(RevenueSellActivity.class,bundle);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    public void onCalculate(View view) {
        String tag = (String) view.getTag();
        String text=et_car_number.getText().toString();
        switch (tag){
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
                if(text.length()<6) {
                    text = text + "1";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "two":
                if(text.length()<6) {
                    text = text + "2";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "three":
                if(text.length()<6) {
                    text = text + "3";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "four":
                if(text.length()<6) {
                    text = text + "4";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "five":
                if(text.length()<6) {
                    text = text + "5";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "six":
                if(text.length()<6) {
                    text = text + "6";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "seven":
                if(text.length()<6) {
                    text = text + "7";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "eigth":
                if(text.length()<6) {
                    text = text + "8";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "nine":
                if(text.length()<6) {
                    text = text + "9";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "zero":
                if(text.length()<6) {
                    text = text + "0";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "A":
                if(text.length()<6) {
                    text = text + "A";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "B":
                if(text.length()<6) {
                    text = text + "B";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "C":
                if(text.length()<6) {
                    text = text + "C";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "D":
                if(text.length()<6) {
                    text = text + "D";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "E":
                if(text.length()<6) {
                    text = text + "E";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "F":
                if(text.length()<6) {
                    text = text + "F";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "G":
                if(text.length()<6) {
                    text = text + "G";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "H":
                if(text.length()<6) {
                    text = text + "H";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "I":
                if(text.length()<6) {
                    text = text + "I";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "J":
                if(text.length()<6) {
                    text = text + "J";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "K":
                if(text.length()<6) {
                    text = text + "k";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "L":
                if(text.length()<6) {
                    text = text + "L";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "M":
                if(text.length()<6) {
                    text = text + "M";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "N":
                if(text.length()<6) {
                    text = text + "N";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "O":
                if(text.length()<6) {
                    text = text + "O";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "P":
                if(text.length()<6) {
                    text = text + "P";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "Q":
                if(text.length()<6) {
                    text = text + "Q";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;

            case "R":
                if(text.length()<6) {
                    text = text + "R";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;

            case "S":
                if(text.length()<6) {
                    text = text + "S";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "T":
                if(text.length()<6) {
                    text = text + "T";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "U":
                if(text.length()<6) {
                    text = text + "U";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "V":
                if(text.length()<6) {
                    text = text + "V";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "W":
                if(text.length()<6) {
                    text = text + "W";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "X":
                if(text.length()<6) {
                    text = text + "X";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "Y":
                if(text.length()<6) {
                    text = text + "Y";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "Z":
                if(text.length()<6) {
                    text = text + "Z";
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            case "delete":{
                if(text.length()>0) {
                    text = text.substring(0, text.length() - 1);
                    et_car_number.setText(text);
                    et_car_number.setSelection(text.length());
                }
                break;
            }
        }
    }
}
