package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.xgx.syzj.R;
import com.xgx.syzj.adapter.StaffUserAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.event.EventCenter;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户信息
 */
public class AccountStaffListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private List<User> mList = new ArrayList<>();
    private StaffUserAdapter mAdapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_staff_list);
        setTitleText("查看店员");
        setSubmit(getString(R.string.app_button_new));

        User user=new User();
        user.setUserName("liaotaoooo");
        user.setUserPhone("1234567652");
        mList.add(user);
        ListView lv_data = (ListView) findViewById(R.id.lv_data);
        lv_data.setOnItemClickListener(this);
        mAdapter = new StaffUserAdapter(this, mList);
        lv_data.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        index = position;
        User user = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
    }


}
