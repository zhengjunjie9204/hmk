package com.xgx.syzj.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

import java.util.ArrayList;

/**
 * 首次启动程序导航页面
 *
 * @author zajo
 * @created 2015年08月06日 17:12
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private int[] resIds = new int[]{R.mipmap.guide_one, R.mipmap.guide_two,
                R.mipmap.guide_three};
    private ViewPager pager;
    private Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		hideTopBar();
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_guide);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new HelpAdapter(this));
		pager.setOnPageChangeListener(new MyPageChangeListener());
		pager.setOnTouchListener(new MyTouchListener());
    }

    @Override
    public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_ok:
				gotoActivity(LoginActivity.class);
				GuideActivity.this.finish();
				break;
		}
    }

    private class MyTouchListener implements View.OnTouchListener {
		float lastDownX;

		/**
		 * 手指按下、手指在控件上滑动、手指抬起来
		 */
		public boolean onTouch(View v, MotionEvent event) {
			int currentItem = pager.getCurrentItem();
			if (currentItem != HelpAdapter.COUNT - 1) {
				// 如果不是停留在最后一个页面，就直接返回，没有必要判断触摸事件
				return false;
			}

			// MotionEvent.ACTION_DOWN 手指按下 --> MotionEvent.ACTION_MOVE 手指在控件上滑动
			// --> MotionEvent.ACTION_UP 手指抬起来
			int action = event.getAction();

			if (action == MotionEvent.ACTION_DOWN) { // 手指按下
				lastDownX = event.getX();
			} else if (action == MotionEvent.ACTION_UP) {
				float upX = event.getX();
				if (lastDownX - upX >= 100) { // 手指至少往左边滑动类100个像素
					// 进入主页
					gotoActivity(LoginActivity.class);
					GuideActivity.this.finish();
				}
			}

			return false;
		}
	}

	private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		/**
		 * 当某个页面被选中
		 *
		 * @param position
		 *            新选中页面的位置
		 */
		public void onPageSelected(int position) {
			if (position == resIds.length - 1) {
				btn_ok.setVisibility(View.VISIBLE);
			} else {
				btn_ok.setVisibility(View.GONE);
			}

		}
	}

	/**
	 * 自定义适配器
	 */
	private class HelpAdapter extends PagerAdapter {
		public static final int COUNT = 3;

		ArrayList<ImageView> views = new ArrayList<ImageView>();

		public HelpAdapter(Context context) {
			// 初始化ImageView
			for (int i = 0; i < COUNT; i++) {
				ImageView imageView = new ImageView(context);
				imageView.setBackgroundResource(resIds[i]);
				views.add(imageView);
			}
		}

		/**
		 * 显示多少数据
		 */
		public int getCount() {
			return COUNT;
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		/**
		 * 销毁position位置对应的控件（其实就是移除控件）
		 *
		 * @param container
		 *            : 其实就是ViewPager
		 * @param object
		 *            : position位置对应的控件
		 */
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		/**
		 * 初始化position位置对应的控件
		 *
		 * @param container
		 *            : 其实就是ViewPager
		 */
		public Object instantiateItem(ViewGroup container, int position) {
			// 先得到position对应的控件
			ImageView imageView = views.get(position);

			// 添加控件到ViewPager中
			container.addView(imageView);

			return imageView;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
