package com.xgx.syzj.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.OrderForm;
import com.xgx.syzj.widget.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * 订单
 *
 * @author zajo
 * @created 2015年11月20日 10:30
 */
public class OrderFormActivity extends BaseActivity implements OrderFormFragmemt.IOrderFormItemClick {

    private PagerSlidingTabStrip indicator;
    private String[] CONTENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
        initView();
    }

    private void initView() {
        setTitleText("微店订单");
        setSubmit("筛选");
        CONTENT = new String[]{"待完成", "已完成"};
        FragmentPagerAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        indicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        // 设置tab样式
        DisplayMetrics dm = getResources().getDisplayMetrics();
        SetTab(OrderFormActivity.this, indicator, dm, 50);
    }

    @Override
    public void onItemClick(OrderForm of) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("order", of);
        gotoActivity(OrderFormDetailActivity.class, bundle);
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> mList = new ArrayList<>();

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            mList.add(new OrderFormFragmemt(false));
            mList.add(new OrderFormFragmemt(true));
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

    private void SetTab(Context context, PagerSlidingTabStrip indicator, DisplayMetrics dm, int padsize) {

        indicator.setIndicatorleftrightpadsize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        // 设置Tab是自动填充满屏幕的
        indicator.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        indicator.setDividerColor(Color.TRANSPARENT);
//        indicator.setDividerColor(R.color.gray);
//        indicator.setDividerPadding(1);
        // 设置Tab底部线的高度
        indicator.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        // 设置Tab Indicator的高度
        indicator.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab标题文字的大小
        indicator.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 13, dm));
        indicator.setTabPaddingLeftRight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        indicator.setTabPadding((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 24, dm));
        // 设置Tab Indicator的颜色
        indicator.setIndicatorColor(context.getResources().getColor(R.color.top_bar_color));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        indicator.setSelectedTextColor(context.getResources().getColor(R.color.top_bar_color));
        // 取消点击Tab时的背景色
        indicator.setTabBackground(0);
    }
}
