package com.xgx.syzj.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xgx.syzj.R;

/**
 * 消费记录顶部弹出框
 *
 * @author zajo
 * @created 2015年09月21日 15:40
 */
public class ConsumptionPopupWindowUtil<T> implements View.OnClickListener {

    PopupWindow popupWindow;
    IPopupWindowCallListener listener;
    Activity activity;

    public ConsumptionPopupWindowUtil(Activity activity, IPopupWindowCallListener listener) {
        this.listener = listener;
        this.activity = activity;
    }

    public void showActionWindow(View parent) {
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_consumption_pop, null);
        TextView tv_one = (TextView) view.findViewById(R.id.tv_one);
        TextView tv_two = (TextView) view.findViewById(R.id.tv_two);
        TextView tv_three = (TextView) view.findViewById(R.id.tv_three);
        View bg = view.findViewById(R.id.bg);
        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);
        bg.setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true){
            @Override
            public void dismiss() {
                super.dismiss();
            }
        };
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent);
        popupWindow.update();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                if (listener != null) {
                    listener.onItemClick(1);
                }
                popupWindow.dismiss();
                break;
            case R.id.tv_two:
                if (listener != null) {
                    listener.onItemClick(2);
                }
                popupWindow.dismiss();
                break;
            case R.id.tv_three:
                if (listener != null) {
                    listener.onItemClick(3);
                }
                popupWindow.dismiss();
                break;
            case R.id.bg:
                popupWindow.dismiss();
                break;
            default:
                break;
        }
    }

    public interface IPopupWindowCallListener {
        void onItemClick(int index);
    }
}
