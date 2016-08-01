package com.xgx.syzj.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.AttendanceDetailAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Attendance;

import java.util.ArrayList;
import java.util.List;

/**
 * 考勤详情
 *
 * @author zajo
 * @created 2015年09月25日 15:21
 */
public class AttendanceDetailActivity extends BaseActivity {

    private AttendanceDetailAdapter mAdapter;
    private ListView lv_data;
    private List<Attendance> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_detail);
        setTitleText("考勤详情");
        lv_data = (ListView) findViewById(R.id.lv_data);
        setData();
        mAdapter = new AttendanceDetailAdapter(this);
        mAdapter.appendList(mList);
        lv_data.setAdapter(mAdapter);
    }

    private void setData() {
        Attendance entry;
        for (int i = 0; i < 20; i++) {
            entry = new Attendance();
            entry.setId(i+"");
            entry.setUserName("2015-09-25");
            entry.setPhone("15:33:33");
            mList.add(entry);
        }
    }
}
