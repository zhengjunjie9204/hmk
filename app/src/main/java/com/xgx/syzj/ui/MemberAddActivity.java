package com.xgx.syzj.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.ViewPagerAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.CardType;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.CardDataModel;
import com.xgx.syzj.datamodel.MemberDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.utils.StrUtil;
import com.xgx.syzj.utils.Utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 新增会员
 *
 * @author zajo
 * @created 2015年08月28日 11:25
 */
public class MemberAddActivity extends BaseActivity implements View.OnFocusChangeListener {

    private static final int LOAD_CARD_LIST = 0;
    private boolean FINASH=true;
    private ViewPager viewPager;
    private ArrayList<View> views = new ArrayList<>();
    private ArrayList<CardType> cards = new ArrayList<>();
    private EditText et_num,et_name, et_phone, et_carnumber, et_cartype;
    private ImageView iv_code,img_mipca;
    private PercentRelativeLayout pr_english;
    private PercentRelativeLayout key_car;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add2);

        setTitleText(getString(R.string.member_add_title));
        setSubmit(getString(R.string.app_button_sure));

        initView();
        initPic();
        initLister();
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initLister() {
        et_num.setOnFocusChangeListener(this);
        et_name.setOnFocusChangeListener(this);
        et_phone.setOnFocusChangeListener(this);
        et_cartype.setOnFocusChangeListener(this);

    }

    private void initPic()
    {
        String Pic = CacheUtil.getmInstance().getUser().getStorePic();
//        String s = Pic.replaceAll("\\\\", "/");
//        String storePic = Url.HOST_URL + "qcmr/upload/wechatImg/" + s;
        Picasso.with(this).load(Pic).into(iv_code);
    }

    private void initView()
    {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        iv_code = (ImageView) findViewById(R.id.iv_code);
        img_mipca = (ImageView) findViewById(R.id.img_mipca);
        et_num = (EditText) findViewById(R.id.et_num);
        pr_english =(PercentRelativeLayout)findViewById(R.id.english_car);
        key_car =(PercentRelativeLayout)findViewById(R.id.key_car);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_carnumber = (EditText) findViewById(R.id.et_carNumber);
        check();
        et_cartype = (EditText) findViewById(R.id.et_carType);
        img_mipca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MemberAddActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 0x101);
            }
        });
    }

    private void check() {
        et_carnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    et_carnumber.clearFocus();
                    Utils.hideSoftInput(MemberAddActivity.this);
                    key_car.setVisibility(View.VISIBLE);
                } else {

                    Utils.hideSoftInput(MemberAddActivity.this);
                }
            }
        });
    }


    private void initData(String storePic)
    {
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result)
        {
            hideLoadingDialog();
            if (result.geteCode() == CardDataModel.LOAD_CARD_LIST) {
                List<CardType> list = FastJsonUtil.json2List(result.getResult(), CardType.class);
                if (list != null) {
                    cards.addAll(list);
                }
                mHandler.sendEmptyMessage(LOAD_CARD_LIST);
            } else if (result.geteCode() == MemberDataModel.ADD_MEMBER) {
                if ("添加成功".equals(result.getMessage())) {
                    showShortToast("新增会员成功");
                    et_num.setText("");
                    et_name.setText("");
                    et_phone.setText("");
                    et_carnumber.setText("");
                    et_cartype.setText("");
                    Intent intent = new Intent(MemberAddActivity.this, MemberListActivity.class);
                    intent.putExtra("huiyuan",1);
                    startActivity(intent);
                    finish();
                } else {
                    String message = result.getMessage();
                    showShortToast(message);
                }
            }
        }

        public void onEvent(String error)
        {
            hideLoadingDialog();
            showShortToast(error);
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_CARD_LIST:
                    if (cards.size() == 0) {
                        viewPager.setVisibility(View.GONE);
                    } else {
                        for (int i = 0; i < cards.size(); i++) {
                            CardType card = cards.get(i);
                            View page = getLayoutInflater().inflate(R.layout.item_member_add_card, null);
                            ((TextView) page.findViewById(R.id.tv_name)).setText(card.getCardTypeName());
                            ((TextView) page.findViewById(R.id.tv_content)).setText(StrUtil.getCardContent(card));
                            views.add(page);
                        }
                        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(0);
                    }

                    break;
            }
        }
    };
    public void onCalculate(View view) {
        String tag = (String) view.getTag();
        String text = et_carnumber.getText().toString();
        switch (tag) {
            case "1":
                et_carnumber.setText("京");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "2":
                et_carnumber.setText("津");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "3":
                et_carnumber.setText("冀");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "4":
                et_carnumber.setText("晋");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "5":
                et_carnumber.setText("蒙");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "6":
                et_carnumber.setText("辽");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "7":
                et_carnumber.setText("吉");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "8":
                et_carnumber.setText("黑");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "9":
                et_carnumber.setText("沪");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "10":
                et_carnumber.setText("苏");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "11":
                et_carnumber.setText("浙");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "12":
                et_carnumber.setText("皖");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "13":
                et_carnumber.setText("闽");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "14":
                et_carnumber.setText("贛");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "15":
                et_carnumber.setText("鲁");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "16":
                et_carnumber.setText("豫");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "17":
                et_carnumber.setText("鄂");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "18":
                et_carnumber.setText("湘");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "19":
                et_carnumber.setText("粤");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "20":
                et_carnumber.setText("桂");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "21":
                et_carnumber.setText("琼");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "22":
                et_carnumber.setText("渝");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "23":
                et_carnumber.setText("川");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "24":
                et_carnumber.setText("贵");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "25":
                et_carnumber.setText("云");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "26":
                et_carnumber.setText("藏");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "27":
                et_carnumber.setText("陕");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "28":
                et_carnumber.setText("甘");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "29":
                et_carnumber.setText("青");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "30":
                et_carnumber.setText("宁");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "31":
                et_carnumber.setText("新");
                key_car.setVisibility(View.GONE);
                pr_english.setVisibility(View.VISIBLE);
                break;
            case "one":
                if (text.length() < 7) {
                    text = text + "1";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "two":
                if (text.length() < 7) {
                    text = text + "2";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "three":
                if (text.length() < 7) {
                    text = text + "3";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "four":
                if (text.length() < 7) {
                    text = text + "4";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "five":
                if (text.length() < 7) {
                    text = text + "5";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "six":
                if (text.length() < 7) {
                    text = text + "6";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }

                break;
            case "seven":
                if (text.length() < 7) {
                    text = text + "7";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "eigth":
                if (text.length() < 7) {
                    text = text + "8";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "nine":
                if (text.length() < 7) {
                    text = text + "9";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "zero":
                if (text.length() < 7) {
                    text = text + "0";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "A":
                if (text.length() < 7) {
                    text = text + "A";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "B":
                if (text.length() < 7) {
                    text = text + "B";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "C":
                if (text.length() < 7) {
                    text = text + "C";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "D":
                if (text.length() < 7) {
                    text = text + "D";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "E":
                if (text.length() < 7) {
                    text = text + "E";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "F":
                if (text.length() < 7) {
                    text = text + "F";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "G":
                if (text.length() < 7) {
                    text = text + "G";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "H":
                if (text.length() < 7) {
                    text = text + "H";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "I":
                if (text.length() < 7) {
                    text = text + "I";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "J":
                if (text.length() < 7) {
                    text = text + "J";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "K":
                if (text.length() < 7) {
                    text = text + "K";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "L":
                if (text.length() < 7) {
                    text = text + "L";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "M":
                if (text.length() < 7) {
                    text = text + "M";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "N":
                if (text.length() < 7) {
                    text = text + "N";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "O":
                if (text.length() < 7) {
                    text = text + "O";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "P":
                if (text.length() < 7) {
                    text = text + "P";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "Q":
                if (text.length() < 7) {
                    text = text + "Q";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;

            case "R":
                if (text.length() < 7) {
                    text = text + "R";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;

            case "S":
                if (text.length() < 7) {
                    text = text + "S";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "T":
                if (text.length() < 7) {
                    text = text + "T";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "U":
                if (text.length() < 7) {
                    text = text + "U";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "V":
                if (text.length() < 7) {
                    text = text + "V";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "W":
                if (text.length() < 7) {
                    text = text + "W";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "X":
                if (text.length() < 7) {
                    text = text + "X";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "Y":
                if (text.length() < 7) {
                    text = text + "Y";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "Z":
                if (text.length() < 7) {
                    text = text + "Z";
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                break;
            case "finish":
                FINASH=false;
                check();
                pr_english.setVisibility(View.GONE);

                break;
            case "delete2": {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                    et_carnumber.setText(text);
                    et_carnumber.setSelection(text.length());
                }
                if(text.length()<1){
                    pr_english.setVisibility(View.GONE);
                     key_car.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }
    @Override
    protected void submit()
    {
        super.submit();
        String strNum = et_num.getText().toString().trim();
        String strName = et_name.getText().toString().trim();
        String strPhone = et_phone.getText().toString().trim();
        String strCarNumber = et_carnumber.getText().toString().trim();
        String strCarType = et_cartype.getText().toString().trim();
        if (TextUtils.isEmpty(strNum)) {
            showShortToast("卡号不能为空");
            return;
        }

        if (TextUtils.isEmpty(strPhone)) {
            showShortToast("号码不能为空");
            return;
        }
        if (!StrUtil.isMobileNo(strPhone)) {
            showShortToast("号码格式不正确");
            return;
        }
        showLoadingDialog(R.string.loading_add_member);
        MemberDataModel.addMember(strName, strCarNumber, strPhone, strCarType, strNum);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            et_num.setText("" + bundle.getString("result"));
        }
    }

    /**
     * 获取网落图片资源
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url)
    {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        key_car.setVisibility(View.GONE);
        pr_english.setVisibility(View.GONE);
    }
}
