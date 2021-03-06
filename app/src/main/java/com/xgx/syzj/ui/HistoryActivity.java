package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.event.TabEvent;
import com.xgx.syzj.widget.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * 商品出入库
 */
public class HistoryActivity extends BaseActivity {

    private SaleHistoryFragment pmoFragment = new SaleHistoryFragment(1);//接车单
    private SaleHistoryFragment pmiFragment = new SaleHistoryFragment(0);//充值钱
    public static ViewPager pager = null;
    public FragmentBackListener backListener;
    private boolean isInterception = false;
    public boolean isInterception() {
        return isInterception;
    }

    public void setInterception(boolean interception) {
        isInterception = interception;
    }

    public FragmentBackListener getBackListener() {
        return backListener;
    }

    public void setBackListener(FragmentBackListener backListener) {
        this.backListener = backListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);
        initView();
    }

    private void initView() {
        CONTENT=new String[]{"接车单","充值单"};
        setTitleText("单据列表");
        setSubmit("筛选");
        FragmentPagerAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);

        PagerSlidingTabStrip indicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        // 设置tab样式
        DisplayMetrics dm = getResources().getDisplayMetrics();
        TabEvent.SetTab(HistoryActivity.this, indicator, dm, 50);
    }

    @Override
    public void onSubmit(View view) {
        super.onSubmit(view);
        Bundle bundle = new Bundle();
        gotoActivityForResult(SaleHistoryFilterActivity.class, bundle, 2003);
    }

    private String[] CONTENT;

    class MyFragmentAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> mList = new ArrayList<>();

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
                mList.add(pmoFragment);
                mList.add(pmiFragment);

        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            super.destroyItem(container, position, object);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (isInterception()) {
                if (backListener != null) {
                    backListener.onBackForward();
                    return false;
                }
            }

        }
        return false;
    }
}
interface FragmentBackListener{
    void onBackForward();
}
