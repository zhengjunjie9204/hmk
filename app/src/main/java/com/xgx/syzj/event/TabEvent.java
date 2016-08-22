package com.xgx.syzj.event;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.xgx.syzj.R;
import com.xgx.syzj.widget.PagerSlidingTabStrip;


public class TabEvent {
    public static void SetTab(Context context, PagerSlidingTabStrip indicator, DisplayMetrics dm , int padsize){

        indicator.setIndicatorleftrightpadsize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        // 设置Tab是自动填充满屏幕的
        indicator.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        indicator.setDividerColor(Color.TRANSPARENT);
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
                TypedValue.COMPLEX_UNIT_DIP, 24, dm))  ;
        // 设置Tab Indicator的颜色
        indicator.setIndicatorColor(context.getResources().getColor(R.color.light_blue));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        indicator.setSelectedTextColor( context.getResources().getColor(R.color.light_blue));
        // 取消点击Tab时的背景色
        indicator.setTabBackground(0);

    }

}
