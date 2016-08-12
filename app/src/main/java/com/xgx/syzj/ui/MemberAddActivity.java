package com.xgx.syzj.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.ViewPagerAdapter;
import com.xgx.syzj.app.Url;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.CardType;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.CardDataModel;
import com.xgx.syzj.datamodel.MemberDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.BitmapUtil;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.utils.StrUtil;

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
public class MemberAddActivity extends BaseActivity{

    private static final int LOAD_CARD_LIST = 0;

    private ViewPager viewPager;
    private ArrayList<View> views = new ArrayList<>();
    private ArrayList<CardType> cards = new ArrayList<>();
    private EditText et_num, et_name, et_phone, et_carnumber, et_cartype;
    private ImageView iv_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add);

        setTitleText(getString(R.string.member_add_title));
        setSubmit(getString(R.string.app_button_sure));
        String Pic = CacheUtil.getmInstance().getUser().getStorePic();
        String s = Pic.replaceAll("\\\\", "/");
        String  storePic=Url.HOST_URL+s;
        initView();
        initData(storePic);
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        iv_code= (ImageView)findViewById(R.id.iv_code);
        et_num = (EditText) findViewById(R.id.et_num);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_carnumber = (EditText) findViewById(R.id.et_carNumber);
        et_cartype = (EditText) findViewById(R.id.et_carType);
    }

    private void initData(String storePic) {
        Bitmap httpBitmap = getHttpBitmap(storePic);
        iv_code.setImageBitmap(httpBitmap);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            if (result.geteCode() == CardDataModel.LOAD_CARD_LIST) {
                List<CardType> list = FastJsonUtil.json2List(result.getResult(), CardType.class);
                if (list != null) {
                    cards.addAll(list);
                }
                mHandler.sendEmptyMessage(LOAD_CARD_LIST);
            } else if(result.geteCode() == MemberDataModel.ADD_MEMBER) {
                if("添加成功".equals(result.getMessage())) {
                    showShortToast("新增会员成功");
                    et_num.setText("");
                    et_name.setText("");
                    et_phone.setText("");
                    et_carnumber.setText("");
                    et_cartype.setText("");
                    gotoActivity(MemberListActivity.class);
                }else{
                    showShortToast("新增会员失败");
                }
            }
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }

    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
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


    @Override
    protected void submit() {
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
        if (TextUtils.isEmpty(strName)) {
            showShortToast("姓名不能为空");
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
        MemberDataModel.addMember(strName,strCarNumber,strPhone,strCarType,strNum);
    }
    /**
     * 获取网落图片资源
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection) myFileURL.openConnection();
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
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }

}
