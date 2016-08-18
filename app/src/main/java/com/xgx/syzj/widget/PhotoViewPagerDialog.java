package com.xgx.syzj.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.ui.PhotoView;
import com.squareup.picasso.Picasso;
import com.xgx.syzj.R;

import java.util.List;

/**
 * Created by 32918 on 2016/6/12.
 *
 * @author 32918
 */
public class PhotoViewPagerDialog extends Dialog {
    private PhotoViewPager mViewPager;
    private List<String> mDataList;
    private SamplePagerAdapter mAdapter;

    public PhotoViewPagerDialog(Context context, List<String> list)
    {
        super(context, R.style.BlackBackGround);
        this.mDataList = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_viewpager);
        // 这句话起全屏的作用
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager);
        mAdapter = new SamplePagerAdapter();
        mViewPager.setAdapter(mAdapter);
    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount()
        {
            return mDataList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position)
        {
            PhotoView photoView = new PhotoView(container.getContext());
            Picasso.with(getContext()).load(mDataList.get(position)).into(photoView);
//            PhotoViewAttacher mAttacher = new PhotoViewAttacher(photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }
    }

    public void show(int position){
        show();
        mViewPager.setCurrentItem(position);
    }
}
