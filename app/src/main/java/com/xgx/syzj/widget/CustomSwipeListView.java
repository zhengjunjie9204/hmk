package com.xgx.syzj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class CustomSwipeListView extends ListView {

	private static final String TAG = "ListViewCompat";

	public static SwipeItemView mFocusedItemView;
	private int mPosition;

	public CustomSwipeListView(Context context) {
		super(context);
	}

	public CustomSwipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomSwipeListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void shrinkListItem(int position) {
		View item = getChildAt(position);

		if (item != null) {
			try {
				((SwipeItemView) item).shrink();
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				int x = (int) event.getX();
				int y = (int) event.getY();
				// 我们想知道当前点击了哪一行
				int position = pointToPosition(x, y);
				if (position != INVALID_POSITION) {

					// 由于pointToPosition返回的是ListView所有item中被点击的item的position，
					// 而listview只会缓存可见的item，因此getChildAt()的时候，需要通过减去getFirstVisiblePosition()
					// 来计算被点击的item在可见items中的位置。
					int firstPos = getFirstVisiblePosition();
					mFocusedItemView = (SwipeItemView) getChildAt(position
							- firstPos);
				}
			}
			default:
				break;
		}

		if (mFocusedItemView != null) {
			mFocusedItemView.onRequireTouchEvent(event);

		}

		return super.onTouchEvent(event);
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		final int actionMasked = ev.getActionMasked() & MotionEvent.ACTION_MASK;
//
//		if (actionMasked == MotionEvent.ACTION_DOWN) {
//			// 记录手指按下时的位置
//			mPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
//			return super.dispatchTouchEvent(ev);
//		}
//		if (mFocusedItemView != null) {
//			if (mFocusedItemView.getisHorizontalMove()) {
//
//				if (actionMasked == MotionEvent.ACTION_MOVE) {
//					// 最关键的地方，忽略MOVE 事件
//					// ListView onTouch获取不到MOVE事件所以不会发生滚动处理
//					return true;
//				}
//			}
//		}
//		// 手指抬起时
//		if (actionMasked == MotionEvent.ACTION_UP
//				|| actionMasked == MotionEvent.ACTION_CANCEL) {
//			// 手指按下与抬起都在同一个视图内，交给父控件处理，这是一个点击事件
//			if (pointToPosition((int) ev.getX(), (int) ev.getY()) == mPosition) {
//				super.dispatchTouchEvent(ev);
//			} else {
//				// 如果手指已经移出按下时的Item，说明是滚动行为，清理Item pressed状态
//				setPressed(false);
//				invalidate();
//				return true;
//			}
//		}
//
//		return super.dispatchTouchEvent(ev);
//	}

}
