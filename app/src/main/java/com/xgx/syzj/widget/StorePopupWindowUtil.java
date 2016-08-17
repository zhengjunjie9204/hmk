package com.xgx.syzj.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Store;

import java.util.List;

/**
 * 消费记录顶部弹出框
 *
 * @author zajo
 * @created 2015年09月21日 15:40
 */
public class StorePopupWindowUtil{

    PopupWindow popupWindow;
    IPopupWindowCallListener listener;
    Activity activity;

    public StorePopupWindowUtil(Activity activity, IPopupWindowCallListener listener) {
        this.listener = listener;
        this.activity = activity;
    }

    public void showActionWindow(View parent, final List<Store> mStoreList) {
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_storelist_pop, null);
        ListView listview = (ListView) view.findViewById(R.id.popup_listview);
        ArrayAdapter arrayAdapter = new ArrayAdapter(activity,R.layout.adapter_textview,mStoreList);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                listener.onItemClick(position,mStoreList.get(position));
                popupWindow.dismiss();
            }
        });
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

    public interface IPopupWindowCallListener {
        void onItemClick(int index,Store store);
    }
}
