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
 * 考勤管理
 *
 * @author zajo
 * @created 2015年09月25日 14:47
 */
public class AttendanceAdapter extends BaseAdapter {

    private Context mContext;
    private List<Attendance> mList = new ArrayList<>();
    private HoldClass hold;

    public AttendanceAdapter(Context context){
        this.mContext = context;
    }

    public void appendList(List<Attendance> list) {
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addEntry(Attendance entry){
        mList.add(0, entry);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_attendance, null);
            hold.tv_phone_text = (TextView) convertView.findViewById(R.id.tv_phone_text);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_late = (TextView) convertView.findViewById(R.id.tv_late);
            hold.tv_absence = (TextView) convertView.findViewById(R.id.tv_absence);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_phone_text.setText(item.getPhone());
        hold.tv_name.setText(item.getUserName());
        hold.tv_late.setText(item.getLate()+"");
        hold.tv_absence.setText(item.getAbsence()+"");

        return convertView;
    }

    static class HoldClass{
        private TextView tv_phone_text,tv_name,tv_late,tv_absence;
    }
}
