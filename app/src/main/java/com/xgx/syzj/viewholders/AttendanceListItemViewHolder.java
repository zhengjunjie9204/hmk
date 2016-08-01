package com.xgx.syzj.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Attendance;
import com.xgx.syzj.widget.list.ViewHolderBase;

/**
 * @author zajo
 * @created 2015年10月12日 15:37
 */
public class AttendanceListItemViewHolder extends ViewHolderBase<Attendance> {

    TextView tv_phone_text,tv_name,tv_late,tv_absence;

    @Override
    public View createView(LayoutInflater inflater) {
        View convertView = inflater.inflate(R.layout.item_attendance, null);
        tv_phone_text = (TextView) convertView.findViewById(R.id.tv_phone_text);
        tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        tv_late = (TextView) convertView.findViewById(R.id.tv_late);
        tv_absence = (TextView) convertView.findViewById(R.id.tv_absence);
        return convertView;
    }

    @Override
    public void showData(int position, Attendance itemData) {
        tv_phone_text.setText(itemData.getPhone());
        tv_name.setText(itemData.getUserName());
        tv_late.setText(itemData.getLate()+"");
        tv_absence.setText(itemData.getAbsence()+"");
    }
}
