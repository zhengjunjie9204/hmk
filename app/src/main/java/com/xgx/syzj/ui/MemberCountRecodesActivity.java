package com.xgx.syzj.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.CountRecordAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.CountRecords;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.RechargeDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.FastJsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 冲次记录接口
 * */
public class MemberCountRecodesActivity extends BaseActivity {
    private ListView mListView;
    private ArrayList<CountRecords> mList = new ArrayList<>();
    private CountRecordAdapter mAdapter;
    private Member member;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_count_recodes);
        member = getIntent().getParcelableExtra("member");
        initView();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        RechargeDataModel.getConsumeList(member.getId());
    }

    private void initView()
    {
        setTitleText(getString(R.string.add_count_recodes));
        mListView = (ListView) findViewById(R.id.recodes_listview);
        mAdapter = new CountRecordAdapter(this,mList);
        mListView.setAdapter(mAdapter);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            if (result.getStatus() == 200) {
                try {
                    JSONObject json = new JSONObject(result.getResult());
                    List<CountRecords> list = FastJsonUtil.json2List(json.getString("records"),CountRecords.class);
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onEvent(String error) {
            showShortToast(error);
        }
    };
}
