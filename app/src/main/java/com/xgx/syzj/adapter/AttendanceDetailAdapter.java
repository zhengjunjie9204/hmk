package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Attendance;

import java.util.ArrayList;
import java.util.List;

/**
 * 考勤详情
 *
 * @author zajo
 * @created 2015年09月25日 14:47
 */
public class AttendanceDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<Attendance> mList = new ArrayList<>();
    private HoldClass hold;

    public AttendanceDetailAdapter(Context context){
        this.mContext = context;
    }

    public void appendList(List<Attendance> list) {
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final Attendance item = mList.get(position);
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_attendance_detail, null);
            hold.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            hold.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_date.setText(item.getUserName());
        hold.tv_time.setText("考勤时间："+item.getPhone());

        return convertView;
    }

    static class HoldClass{
        private TextView tv_time,tv_date;
    }
}
