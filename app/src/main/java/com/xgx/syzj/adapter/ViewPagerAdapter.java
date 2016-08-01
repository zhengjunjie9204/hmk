package com.xgx.syzj.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * @author zajo
 * @created 2015年10月21日 16:28
 */
public class ViewPagerAdapter extends PagerAdapter {
    public List<View> mListViews;

    public ViewPagerAdapter(List<View> views) {
        super();
        this.mListViews = views;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        return mListViews != null ? mListViews.size() : 0;
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        if (mListViews.get(arg1).getParent() == null) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
        } else {
            ((ViewPager) mListViews.get(arg1).getParent())
                    .removeView(mListViews.get(arg1));
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
        }

        return mListViews.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }
}
