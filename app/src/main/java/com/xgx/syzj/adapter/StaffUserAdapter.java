package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xgx.syzj.R;
import com.xgx.syzj.bean.userInfo;

import java.util.ArrayList;
import java.util.List;


public class StaffUserAdapter extends BaseAdapter {


    private Context mContext;
    private List<userInfo> mList = new ArrayList<userInfo>();


    public StaffUserAdapter(Context context, List<userInfo> mList) {
        this.mContext = context;
        this.mList=mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        userInfo user=mList.get(position);
        final HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_user, null);
            hold.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_phone= (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_name.setText(user.getName());
        hold.tv_phone.setText(user.getPhone());

        return convertView;
    }

     public class HoldClass {
        private TextView tv_name,tv_phone;
    }

}
